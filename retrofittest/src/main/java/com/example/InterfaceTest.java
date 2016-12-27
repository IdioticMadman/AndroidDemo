package com.example;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;

public class InterfaceTest {

    private static String TAG = MyClass.class.getSimpleName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    interface TestService {
        @Headers("Content-Type: application/json")
        @POST("/api/v1/forms_preview")
        Observable<ResponseBody> postFromData(@Body RequestBody body);
    }

    private static OkHttpClient getOkHttpClient(final String sessionId) {
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader("Cookie", sessionId).build());
            }
        };
        newBuilder.addInterceptor(requestInterceptor);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        newBuilder.addInterceptor(loggingInterceptor);
        //设置缓存路径跟大小
        return newBuilder.build();
    }


    public static void main(String[] args) {

        ArrayList<FormPreviewBean.DataBean> data = new ArrayList<>();
        FormPreviewBean.DataBean dataBean = new FormPreviewBean.DataBean("projectName", "工程名称");
        FormPreviewBean.DataBean dataBean1 = new FormPreviewBean.DataBean("projectId", "工程名称");
        FormPreviewBean.DataBean dataBean2 = new FormPreviewBean.DataBean("projectPerson", "工程名称");
        FormPreviewBean.DataBean dataBean3 = new FormPreviewBean.DataBean("projectuuu", "工程名称");
        data.add(dataBean);
        data.add(dataBean1);
        data.add(dataBean2);
        data.add(dataBean3);
        FormPreviewBean formPreviewBean = new FormPreviewBean(data, "585390b24db7851bf7b583b2");
        String str = getJsonString(formPreviewBean);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.64:3000")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//返回增加对RxJava的支持
                .client(getOkHttpClient("connect.sid=s%3AXALf1mLVoXzSP9xjnVJLDkzrdKhARxPC.Ip2MKF%2FQ22MobwmuVcJ%2FKzZYpUf0ebpbMqQc9b8mzbs; Path=/; Expires=Wed, 28 Dec 2016 09:50:09 GMT; HttpOnly"))//设置依赖的HttpClient
                .addConverterFactory(GsonConverterFactory.create())//返回的对象序列化使用Gson
                .build();

        System.out.println("转换过后的" + str);
        RequestBody request = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        retrofit.create(TestService.class).postFromData(request).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Throwable:" + e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                InputStream inputStream = responseBody.byteStream();
                File file = new File("D:\\test.pdf");
                byte[] size = new byte[1024];
                int len;
                OutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    while ((len = inputStream.read(size)) != -1) {
                        outputStream.write(size, 0, len);
                    }
                    outputStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("ResponseBody:" + responseBody);
            }
        });

        /*OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, str);
        Request request = new Request.Builder()
                .url("http://192.168.1.64:3000/api/v1/forms_preview")
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                InputStream inputStream = response.body().byteStream();
                File file = new File("D:\\test.pdf");
                byte[] size = new byte[1024];
                int len;
                OutputStream outputStream = new FileOutputStream(file);
                while ((len = inputStream.read(size)) != -1) {
                    outputStream.write(size, 0, len);
                }
                outputStream.close();
                inputStream.close();
//                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    public static String getJsonString(FormPreviewBean formPreviewBean) {
        String templateId = formPreviewBean.getTemplateId();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("templateId", templateId);

        List<FormPreviewBean.DataBean> data = formPreviewBean.getData();
        JsonObject jsonObject1 = new JsonObject();
        for (FormPreviewBean.DataBean dataBean : data) {
            jsonObject1.addProperty(dataBean.getKey(), dataBean.getValue());
        }
        jsonObject.add("data", jsonObject1);
        return jsonObject.toString();
    }
}
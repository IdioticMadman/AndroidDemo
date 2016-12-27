package com.example;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class MyClass {

    public interface FileService {

        @POST("view/queryListView")
        @FormUrlEncoded
        Call<ResponseBody> getViewPort(@FieldMap(encoded = true) Map<String, String> groupName);


        @GET("projects")
        Call<List<Project>> getProjects(@Query("userId") String userId);

        @GET("videoControl/camera")
        Call<List<CameraUrl>> getId();

        @POST("/YiZhuOA/projects/workOrder/WBPicUpload")
        Call<ResponseBody> postPictures(@Body MultipartBody multipartBody);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {


        File file = new File("F:\\desktop");
        List<File> files = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files1 = file.listFiles();
            Collections.addAll(files, files1);
        }
        /*RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/otcet-stream"), file1);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("application/otcet-stream"), file2);
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("file", file1.getName(), requestBody1);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("file", file2.getName(), requestBody2);
        List<MultipartBody.Part> bodys = new ArrayList<>();
        bodys.add(body1);
        bodys.add(body2);*/
        Map<String, String> options = new HashMap<>();
        options.put("workProjectID", "052b6ed94adb40afadd8daa09067f46");
        options.put("workId", "c2f7bc61c2b848fb873e135eb1edaea1");
        MultipartBody multipartBody = getMultipartBody(files, "fileUpload", options);
        try {
            Response<ResponseBody> execute = RetrofitBuilder.buildRetrofit("http://192.168.1.64:3000").create(FileService.class)
                    .postPictures(multipartBody).execute();
            System.out.println(execute.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static MultipartBody getMultipartBody(List<File> files, String fileParams, Map<String, String> keySet) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i));
            builder.addFormDataPart(fileParams, files.get(i).getName(), requestBody);
        }
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
}

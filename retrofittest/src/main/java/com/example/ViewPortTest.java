package com.example;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by robert on 2016/12/23.
 */

public class ViewPortTest {

    interface ViewPortService {
        @POST("YiZhuOA/view/queryListView")
        @FormUrlEncoded
        Observable<ResponseBody> getViewPort(@Field("viewProjectId") String projectId, @Field("groupName") String groupName);

    }

    public static void main(String[] args) {
//        http://192.168.1.71:8080/YiZhuOA/view/queryViewName/f027858d7128485da3703d27406273fe/%E6%B0%B4%E4%BD%8D%E6%A0%87%E5%B0%BA
        RetrofitBuilder.buildRetrofit("http://192.168.1.71:8080").create(ViewPortService.class)
                .getViewPort("f027858d7128485da3703d27406273fe","水位标尺")
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            System.out.println(response.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}

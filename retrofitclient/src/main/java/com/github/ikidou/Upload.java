package com.github.ikidou;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.github.ikidou.Upload
 * @author: robert
 * @date: 2016-09-27 19:31
 */

public class Upload {

    public interface FileService {
        @POST("Android/upload")
        @Multipart
        Call<ResponseBody> testFileUpload1(@Part("workUrl") RequestBody folderName, @Part("filename") RequestBody fileName, @Part("uploadFile\";filename =\"Eops.png") RequestBody file);

        @Multipart
        @POST(API_VERSION + "pictures")
        void postPictures(@Part("fileSize") int fileSize, @PartMap Map<String, TypedFile> pics, Callback<List<ImageFile>> callback);


        @GET("Android/check/{folderName}")
        Call<ResponseBody> getFileList(@Path("folderName") String folderName);

        @GET("Android/download/{workUrl}/{fileName}")
        Call<ResponseBody> getFile(@Path("workUrl") String workUrl, @Path("fileName") String fileName);

        @POST("a/login")
        Call<ResponseBody> login(@Field("username") String userName, @Field("password") String userPassword);
    }

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.109:8080/YiZhuOA/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        File uploadFile = new File("C:\\Users\\robert\\Desktop\\Eops.png");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);
        RequestBody folderName =
                RequestBody.create(MediaType.parse("multipart/form-data"), "picture");
        RequestBody fileName =
                RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile.getName());

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", uploadFile.getName(), requestFile);

        FileService fileService = retrofit.create(FileService.class);
        /*Call<ResponseBody> call = fileService.testFileUpload1(folderName, fileName, requestFile);
        Call<ResponseBody> fileList = fileService.getFileList("picture");
        ResponseBodyPrinter.printResponseBody(fileList);
        try {
//            Response<ResponseBody> execute1 = fileList.execute();
            Response<ResponseBody> execute = call.execute();
            ResponseBody responseBody = execute.body();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        Call<ResponseBody> call = fileService.getFile("picture", "Eops.png");
        Call<ResponseBody> call = fileService.login("admin", "123");
        try {
            Response<ResponseBody> execute = call.execute();
            ResponseBody body1 = execute.body();
            String string = body1.string();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(uploadFile.getAbsoluteFile());
    }
}

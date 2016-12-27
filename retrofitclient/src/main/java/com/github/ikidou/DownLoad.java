package com.github.ikidou;

import com.sun.deploy.net.URLEncoder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.github.ikidou.DownLoad
 * @author: robert
 * @date: 2016-09-27 20:30
 */

public class DownLoad {

    public interface FileService {

        @POST("view/queryListView")
        @FormUrlEncoded
        Call<ResponseBody> getViewPort(@FieldMap(encoded = true) Map<String, String> groupName);


        @GET("api/projects")
        Call<ResponseBody> getProjects(@Query("userId") String userId);

//        @POST("")
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        /*Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.109:8080/YiZhuOA/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        FileService fileService = retrofit.create(FileService.class);
        Map<String, String> map = new HashMap<>();
        map.put("groupName", "%e8%a7%86%e5%8f%a3");
        Call<ResponseBody> login = fileService.getViewPort(map);

        try {
            Response<ResponseBody> execute = login.execute();
            ResponseBody body1 = execute.body();
            String string = body1.string();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*try {
            URL url = new URL("http://192.168.1.109:8080/YiZhuOA/view/queryListView");
            Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            PrintWriter out = new PrintWriter(conn.getOutputtream());
            // 发送请求参数

            String s = "groupName=" + "%e8%a7%86%e5%8f%a3";
            URLDecoder.decode("%e8%a7%86%e5%8f%a3", "utf-8");
            out.write(s);
            // flush输出流的缓冲
            out.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line).append("\r\n");
                line = reader.readLine();
            }
            reader.close();
            conn.disconnect();
            System.out.println(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        System.out.println(URLEncoder.encode("视口","utf-8"));
    }
}

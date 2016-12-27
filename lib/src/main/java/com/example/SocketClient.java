package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.SocketClient
 * @author: robert
 * @date: 2016-09-26 18:13
 */

public class SocketClient {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String urlStr = "http://192.168.1.109:8080/YiZhuOA/Pars/value";
                HttpURLConnection conn = null;
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                InputStreamReader in = null;
                try {
                    URL url = new URL(urlStr);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(1000);
                    conn.setConnectTimeout(1000);
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        inputStream = conn.getInputStream();
                        in = new InputStreamReader(inputStream);
                        bufferedReader = new BufferedReader(in);
                        String str = bufferedReader.readLine();
                        System.out.println(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                            bufferedReader = null;
                        }
                        if (in != null) {
                            in.close();
                            in = null;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        if (conn != null) {
                            conn.disconnect();
                            conn = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000, 5000);
    }
}

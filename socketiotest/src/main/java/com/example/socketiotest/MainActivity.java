package com.example.socketiotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private WebSocketClient client;
    private EditText etReceive;
    private EditText etSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etReceive = (EditText) findViewById(R.id.et_receive);
        etSend = (EditText) findViewById(R.id.et_send);
        JSONObject jo = new JSONObject();
        try {
            jo.put("type", "register");

            jo.put("from", "");
            jo.put("projectId", "22");
            JSONObject jo2 = new JSONObject();
            jo2.put("userId", "44");
            jo2.put("userName","testAndroid");
            jo2.put("deviceName", "Android");
            jo.put("data", jo2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String str = jo.toString();
        new Thread() {
            @Override
            public void run() {
                List<BasicNameValuePair> extraHeaders = Arrays.asList(
                        new BasicNameValuePair("Cookie", str)
                );
                WebSocketClient.Listener listener = new WebSocketClient.Listener() {
                    @Override
                    public void onConnect() {
                        Log.e(TAG, "Connected!");
                        client.send("Hello, I am from Android");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etReceive.append("Connected!" + "\n");
                            }
                        });
                    }

                    @Override
                    public void onMessage(final String message) {
                        final String format = String.format("Got string message! %s", message);
                        Log.e(TAG, format);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etReceive.append(format + "\n");
                            }
                        });
                    }

                    @Override
                    public void onMessage(byte[] data) {
                        final String format = String.format("Got binary message! %s", new String(data));
                        Log.e(TAG, format);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etReceive.append(format + "\n");
                            }
                        });
                    }

                    @Override
                    public void onDisconnect(int code, String reason) {
                        final String format = String.format("Disconnected! Code: %d Reason: %s", code, reason);
                        Log.e(TAG, format);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etReceive.append(format + "\n");
                            }
                        });
                    }

                    @Override
                    public void onError(Exception error) {
                        final String message = error.getMessage();
                        Log.e(TAG, "Error!", error);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etReceive.append(message + "\n");
                            }
                        });
                    }
                };
                client = new WebSocketClient(URI.create("ws://192.168.1.202:8080/YiZhuOA/socketServer"), listener, extraHeaders);
                client.connect();
            }
        }.start();

    }

    public void send(View view) {
        if (client != null) {
            String text = etSend.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(this, "发送的消息不能为空", Toast.LENGTH_SHORT).show();
            } else {
                client.send(text);
                etSend.setText("");
            }
        }
    }
}

package com.example.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private Button btn_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "post: " + "获取到点击了");
                //Toast.makeText(SecondActivity.this, "Hello World,你好", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MessageEvent("Hello World,你好"));
                SecondActivity.this.startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
    }

}

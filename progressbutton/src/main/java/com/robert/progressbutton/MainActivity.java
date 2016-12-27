package com.robert.progressbutton;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dd.CircularProgressButton;

public class MainActivity extends AppCompatActivity {


    // 显示进度文字
    private TextView tv;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = msg.getData().getInt("progress");
            btn.setProgress(progress);
            if (progress != 100) {
                btn.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cpb_blue));
                btn.setText(progress + "%");
            } else {
                btn.setTextColor(Color.WHITE);
                btn.setText("");
            }

        }
    };
    private CircularProgressButton btn;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (CircularProgressButton) findViewById(R.id.btnWithText);
//        tvProgress = (TextView) findViewById(R.id.tv_progress);
        btn.setProgress(100);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.getProgress() == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 1; i <= 100; i++) {
                                Message message = Message.obtain();
                                message.getData().putInt("progress", i);

                                SystemClock.sleep(100);
                                handler.sendMessage(message);

                            }
                        }
                    }).start();
                } else {
                    btn.setProgress(0);
//                    tvProgress.setText("");
                }
            }
        });

    }
}

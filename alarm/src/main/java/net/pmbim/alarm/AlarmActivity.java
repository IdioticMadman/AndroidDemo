package net.pmbim.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.pmbim.alarm.AlarmActivity
 * @author: robert
 * @date: 2016-09-26 15:35
 */

public class AlarmActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setFinishOnTouchOutside(false);
        String fileName = "alarm.gif";
        Glide.with(this).load(new File(getCacheDir(), fileName)).asGif().into((ImageView) findViewById(R.id.iv_alarm));
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音乐音量
        final int maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    int current = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (current < maxVolume) {
                        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume/5,
                                AudioManager.FLAG_PLAY_SOUND);
                    }
                    SystemClock.sleep(50);
                }
            }
        }.start();
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume/5,
                AudioManager.FLAG_PLAY_SOUND);
        long[] pattern = {500, 1000};
        vibrator.vibrate(pattern, 0);
        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();
        mp.setLooping(true);
    }

    public void confirm(View view) {
        mp.stop();
        vibrator.cancel();
        finish();
    }

    protected void onDestroy() {
        if (mp.isPlaying()) {
            mp.stop();
        }
        vibrator.cancel();
        mp.release();//释放资源
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

package net.pmbim.alarm;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";
    private MediaPlayer mp;
    private Vibrator vibrator;
    private AudioManager audioMgr;
    private int maxVolume;
    private AlarmDialog alarmDialog;
    private SharedPreferences defaultSharedPreferences;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (defaultSharedPreferences.getBoolean("alarm", true)) {
                    Log.e(TAG, "handleMessage: 处理消息了");
                    startAlarm();
                    defaultSharedPreferences.edit().putBoolean("alarm", false).commit();
                }
            }
        }
    };
    private Timer timer;

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音乐音量
        maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mp = MediaPlayer.create(this, R.raw.alarm);
        alarmDialog = new AlarmDialog(getBaseContext());
        alarmDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alarmDialog.setCanceledOnTouchOutside(false);//点击外面区域不会让dialog消失
        alarmDialog.setCloseListener(new AlarmDialog.CloseListener() {
            @Override
            public void close() {
                stopAlarm();
            }
        });
        alarmDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.e(TAG, "onStartCommand: ");
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String urlStr = "http://192.168.1.56:8081/YiZhuOA/Pars/value";
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
                        if (str.equals("YES")) {
                            mHandler.sendEmptyMessage(1);
                            Log.e(TAG, "run: 接收到消息");
                        }
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
        };
        timer.schedule(timerTask, 1000, 5000);
        return super.onStartCommand(intent, flags, startId);
    }

    public void startAlarm() {
        alarmDialog.show();
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume / 5,
                AudioManager.FLAG_PLAY_SOUND);
        long[] pattern = {500, 1000};
        vibrator.vibrate(pattern, 0);
        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.start();
        mp.setLooping(true);
    }

    public void stopAlarm() {
        alarmDialog.dismiss();
        vibrator.cancel();
        mp.stop();
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        if (mp.isPlaying()) {
            mp.stop();
        }
        timer.cancel();
        vibrator.cancel();
        mp.release();//释放资源
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}

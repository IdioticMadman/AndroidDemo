package com.example.pdfviewdemo;

import android.app.Application;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.pdfviewdemo.App.java
 * @author: robert
 * @date: 2016-07-04 11:22
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
    }
}

package com.example.modelviewdemo;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by robert on 2016/12/22.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.init(getApplicationContext());
    }
}

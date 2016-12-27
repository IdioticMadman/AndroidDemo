package com.example.greendao3demo;

import android.app.Application;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.greendao3demo.App.java
 * @author: robert
 * @date: 2016-12-05 13:51
 */
public class App extends Application {

    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    public static App getApp() {
        return sApp;
    }

}

package net.pmbim.model.base;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.pmbim.model.base.App
 * @author: robert
 * @date: 2016-10-12 17:45
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.init(getApplicationContext());
    }
}

package com.example.greendao3demo.bean;

import android.content.Context;
import android.os.Environment;

import com.example.greendao3demo.App;
import com.socks.library.KLog;

import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.greendao3demo.bean.GreenDaoManager.java
 * @author: robert
 * @date: 2016-12-05 13:49
 */

public class GreenDaoManager {
    private static final String TAG = "GreenDaoManager";
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager(Context context) {
        String dbName = Environment.getExternalStorageDirectory().getPath() + File.separator + "user-db";
        KLog.e(TAG, "GreenDaoManager: " + dbName);
        Database db = new DaoMaster.DevOpenHelper(context, dbName).getWritableDb();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager(App.getApp());
                }
            }
        }
        return mInstance;
    }
}

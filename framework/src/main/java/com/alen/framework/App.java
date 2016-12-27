package com.alen.framework;

import android.app.Application;

import com.alen.framework.global.Constants;

/**
 * Created by Jeff on 2016/5/4.
 */
public class App extends Application {

    private static App app;

    /**
     * 全局角色
     **/
    public static Constants.Role role;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    /**
     * 获取App
     *
     * @return
     */
    public static App getInstance() {
        return app;
    }

    /**
     * 获取角色
     *
     * @return
     */
    public static Constants.Role getRole() {
        return role;
    }

    /**
     * 设置全局角色（登录之后进行设置）
     *
     * @param role
     */
    public static void setRole(Constants.Role role) {
        App.role = role;
    }
}

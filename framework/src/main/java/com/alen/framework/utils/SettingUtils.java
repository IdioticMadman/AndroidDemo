package com.alen.framework.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * 跳转到相关的设置页面
 * Created by Jeff on 2016/5/6.
 */
public class SettingUtils {
    /**
     * 根据GPS状态，跳转到GPS设置页面
     */
    public static void jumpGpsSetting(Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
        boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);//判断GPS是否打开
        if (enabled) {
            ToastUtils.showShort(context, "已经开启");
        } else {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setAction(Settings.ACTION_SETTINGS);
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                }
            }
        }
    }
}

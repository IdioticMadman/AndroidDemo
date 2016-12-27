package com.example.screendemo;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = isPad() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: 被执行了");
        //getDisplayInfomation();
    }

    /**
     * 判断是否是pad设备
     *
     * @return
     */
    private boolean isPad() {
        Point point = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else {
            display.getSize(point);
        }
        Log.e(TAG, "the screen size is " + point.toString());
        DisplayMetrics dm = new DisplayMetrics();
        //获取DisplayMetrics对象
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        /**
         * 获取物理设备的x的平方，单位为英寸
         * dm.xdpi dm.ydpi 屏幕 X/Y 轴上，每英寸含有的像素点 PPI(Pixels per inch)
         */
        double x = Math.pow(point.x / dm.xdpi, 2);
        //获取物理设备的y的平方，单位为英寸
        double y = Math.pow(point.x / dm.ydpi, 2);
        // 勾股算出斜对角的屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        Log.e(TAG, " dm.xdpi = " + dm.xdpi +"dm.ydpi = " + dm.ydpi + "screenInches = " + screenInches);

        // 大于6尺寸则为Pad
        if (screenInches >= 6.5) {
            return true;
        }
        return false;
    }

    /**
     * 获取设备的信息
     */
    private void getDisplayInfomation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        //这种方式获取的值和上面的一样
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取物理设备的x的平方，单位为英寸
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        //获取物理设备的y的平方，单位为英寸
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);

        Log.e(TAG, "through DisplayMetrics, widthPixels = " + dm.widthPixels + ",heightPixels = " + dm.heightPixels);


        //有可能因为toolbar之类的暂用一部分屏幕，getRealSize获取真实屏幕的值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindowManager().getDefaultDisplay().getRealSize(point);
        }
        Log.e(TAG, "the screen real size is " + point.toString());

    }

}

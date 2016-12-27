package com.jauker.badgeview.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ImageView;

import com.jauker.widget.BadgeView;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.jauker.badgeview.example.MainActivity
 * @author: robert
 * @date: 2016-09-30 10:25
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.iv_back);

        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(imageView);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setBadgeCount(22);
    }
}

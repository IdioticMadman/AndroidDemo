package com.robert.slidingmenudemo;

import android.os.Bundle;

import com.robert.slidingmenulib.SlidingMenu;
import com.robert.slidingmenulib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu mSm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 设置旁边的菜单页面
        setBehindContentView(R.layout.activity_menu);
        // 设置内容页面
        setContentView(R.layout.activity_main);

        // Fragment1 fragment1 = new Fragment1();
        //
        // getSupportFragmentManager().beginTransaction()
        // .replace(R.id.content, fragment1).commit();
        //
        // Button button = (Button) findViewById(R.id.button);
        // button.setOnClickListener(this);
        // 获取到一个滑动菜单
        mSm = getSlidingMenu();
        // customize the SlidingMenu

        // 设置滑动菜单的模式
        // SlidingMenu.LEFT设置滑动菜单左边滑动
        // SlidingMenu.RIGHT设置滑动菜单在右边
        // SlidingMenu.LEFT_RIGHT 设置侧滑菜单左边和右边都有
        mSm.setMode(SlidingMenu.LEFT);
        // 设置侧滑菜单触摸的模式
        // SlidingMenu.TOUCHMODE_FULLSCREEN 侧滑菜单的全屏模式
        // SlidingMenu.TOUCHMODE_MARGIN 侧滑菜单的边沿模式
        // SlidingMenu.TOUCHMODE_NONE侧滑菜单不能滑动
        mSm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置菜单页面出来之后，内容页面的剩余宽度
        mSm.setBehindOffsetRes(R.dimen.sm_width);
        // 设置阴影图片
        mSm.setShadowDrawable(R.drawable.shadow);
        // 设置阴影图片的宽度
        mSm.setShadowWidthRes(R.dimen.shadow_width);
    }
}

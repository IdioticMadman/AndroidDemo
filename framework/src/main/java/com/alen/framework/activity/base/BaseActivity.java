package com.alen.framework.activity.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Jeff on 2016/5/4.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidget();
        setListener();
        initData();
    }

    public <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 初始化组件
     **/
    public abstract void initWidget();

    /**
     * 设置监听事件
     **/
    public void setListener() {

    }

    /**
     * 初始化数据
     **/
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * 控件的点击事件处理
     *
     * @param v
     */
    public void widgetClick(View v) {
    }
}

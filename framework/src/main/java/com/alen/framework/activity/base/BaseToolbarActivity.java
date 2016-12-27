package com.alen.framework.activity.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alen.framework.R;

/**
 * 具有Toolbar的Activity
 * Created by Jeff on 2016/5/5.
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    protected FrameLayout content;//内容页
    protected Toolbar toolbar;//toolbar
    protected TextView tvTitle;//标题

    @Override
    public void initWidget() {
        setContentView(R.layout.activity_base_toolbar);
        content = findView(R.id.content);
        toolbar = findView(R.id.toolbar);
        toolbar.setTitle("");//去除toolbar原来的标题
        tvTitle = findView(R.id.tv_title);
        setSupportActionBar(toolbar);
        content.addView(initView());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (listener != null) {
                    listener.onClickBackListener();
                } else {
                    finish();//默认关闭当前Activity
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private OnClickBackListener listener;

    interface OnClickBackListener {
        void onClickBackListener();
    }

    /**
     * 设置返回按钮的事件监听
     *
     * @param listener
     */
    public void setOnClickBackListener(OnClickBackListener listener) {
        this.listener = listener;
    }

    public abstract View initView();

    /**
     * 设置标题
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置是否显示Toolbar上的返回按钮
     *
     * @param isShow
     */
    public void showToolbarBack(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }

}

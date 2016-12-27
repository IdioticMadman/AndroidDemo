package com.alen.framework.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by Jeff on 2016/5/4.
 */
public abstract class BaseHolder<T> {
    private View mRootView;

    private T mData;

    public Context context;

    public BaseHolder(Context context) {
        this.context = context;
        mRootView = initView();
        mRootView.setTag(this);
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 实现view的布局
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 让子类根据数据来刷新自己的视图
     *
     * @param data
     */
    protected abstract void refreshUI(T data);

    /**
     * 给View设置数据
     *
     * @param mData
     */
    public void setData(T mData) {
        this.mData = mData;
        refreshUI(mData);
    }
}

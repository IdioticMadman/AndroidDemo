package com.alen.framework.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jeff on 2016/5/5.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView = initView();
        setListener();
        initData();

        return mRootView;
    }

    /**
     * 设置事件监听
     */
    protected void setListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化View
     *
     * @return
     */
    protected abstract View initView();

    @Override
    public void onClick(View v) {

    }
}

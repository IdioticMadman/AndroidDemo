package com.alen.framework.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alen.framework.holder.BaseHolder;

import java.util.List;

/**
 * Created by Jeff on 2016/5/4.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private List<T> data;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (data == null) {
            new Throwable("请先设置数据");
        }

        BaseHolder<T> holder;

        if (convertView == null) {
            holder = getViewHolder();
            convertView = holder.getRootView();
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        holder.setData(getItem(position));

        return convertView;
    }

    public abstract BaseHolder<T> getViewHolder();

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }
}

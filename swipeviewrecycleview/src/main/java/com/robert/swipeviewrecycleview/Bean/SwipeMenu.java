package com.robert.swipeviewrecycleview.Bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.robert.swipeviewrecycleview.Bean.SwipeMenu
 * @author: robert
 * @date: 2016-07-25 14:28
 */
public class SwipeMenu {

    private Context mContext;
    private List<SwipeMenuItem> mItems;

    public SwipeMenu(Context context) {
        this.mContext = context;
        mItems = new ArrayList<>();
    }
    public Context getContext() {
        return mContext;
    }

    public void addItem(SwipeMenuItem item) {
        mItems.add(item);
    }

    public void removeItem(SwipeMenuItem item) {
        mItems.remove(item);
    }


    public List<SwipeMenuItem> getItems() {
        return mItems;
    }

    public SwipeMenuItem getItem(int index) {
        return mItems.get(index);
    }


}

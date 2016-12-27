package com.robert.swipeviewrecycleview.Bean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.robert.swipeviewrecycleview.Bean.SwipeMenuItem
 * @author: robert
 * @date: 2016-07-25 14:11
 */
public class SwipeMenuItem {

    public final static int TITLE_SIZE = 20;   //sp
    public final static int WIDTH = 80;        //dp

    private int id;
    private String title;
    private int titleSize;
    private int titleColor;
    private int width;
    private Drawable icon;
    private Drawable background;
    private Context mContext;


    public SwipeMenuItem(Context Context) {
        this.mContext = Context;

        //设置初始值
        this.titleColor = Color.WHITE;
        this.titleSize = TITLE_SIZE;
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        this.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH, dm);
    }

    public int getId() {
        return id;
    }

    public SwipeMenuItem setId(int id) {
        this.id = id;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public SwipeMenuItem setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SwipeMenuItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public Drawable getIcon() {
        return icon;
    }

    public SwipeMenuItem setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public SwipeMenuItem setIcon(int resId) {
        this.icon = mContext.getResources().getDrawable(resId);
        return this;
    }

    public Drawable getBackground() {
        return background;
    }

    public SwipeMenuItem setBackground(Drawable background) {
        this.background = background;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public SwipeMenuItem setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public SwipeMenuItem setTitleSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }
}

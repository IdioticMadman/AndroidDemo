package com.robert.swipeviewrecycleview.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by robert on 2016/8/4.
 */

public class SwapWrapperUtils {

    public static SwipeMenuLayout wrap(ViewGroup parent, int layoutId, SwipeMenuView menuView, Interpolator openInterpolator, Interpolator closeInterpolator) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(contentView, menuView, openInterpolator, closeInterpolator);
        return swipeMenuLayout;
    }
}

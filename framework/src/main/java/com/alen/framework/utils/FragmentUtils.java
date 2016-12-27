package com.alen.framework.utils;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alen.framework.R;
import com.alen.framework.fragment.base.BaseFragment;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jeff on 2016/5/5.
 */
public class FragmentUtils {

    private static CopyOnWriteArrayList<BaseFragment> allFragments = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<BaseFragment> addFragments = new CopyOnWriteArrayList<>();//保存已经添加的Fragment

    /**
     * 添加
     *
     * @param fragment
     */
    public static void addFragments(BaseFragment fragment) {
        allFragments.add(fragment);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     */
    public static void replaceFragment(FragmentActivity activity, BaseFragment fragment) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction fts = fm.beginTransaction();
        allFragments.remove(fragment);
        for (BaseFragment f : allFragments) {
            fts.hide(f);
        }
        boolean isAdd = addFragments.contains(fragment);//是否添加
        if (!isAdd) {
            fts.add(R.id.fl_content, fragment);
            addFragments.add(fragment);
        }
        fts.show(fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        allFragments.add(fragment);
    }
}

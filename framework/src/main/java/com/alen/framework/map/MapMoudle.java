package com.alen.framework.map;

/**
 * Created by Jeff on 2016/5/3.
 */
public interface MapMoudle<T> {

    void startLocation();

    void stopLocation();

    void onDestroy();

    void setFinishListener(T t);

    interface FinishListener<T> {
        void getInfo(T info);
    }
}

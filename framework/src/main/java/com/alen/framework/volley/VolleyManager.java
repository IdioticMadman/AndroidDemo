package com.alen.framework.volley;

import com.alen.framework.App;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Volley管理器
 * Created by Jeff on 2016/5/6.
 */
public class VolleyManager {

    private static final Object TAG = "VolleyManager";
    private static RequestQueue mRequestQueue;//请求队列
    private static ImageLoader mImageLoader;//图片加载器

    /**
     * 获取请求队列
     *
     * @return
     */
    public synchronized static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(App.getInstance());
        }
        return mRequestQueue;
    }

    /**
     * 添加请求并设置tag
     *
     * @param req
     * @param tag
     * @param <T>
     */
    public static <T> void addRequest(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);

    }

    /**
     * 添加请求
     *
     * @param req
     * @param <T>
     */
    public static <T> void addRequest(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * 根据tag取消请求
     *
     * @param tag
     */
    public void cancelRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 获取图片加载器
     *
     * @return
     */
    public static ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue,
                    new LruBitmapCache(App.getInstance()));
        }
        return mImageLoader;
    }
}

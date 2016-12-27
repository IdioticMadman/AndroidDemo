package com.alen.framework.map;

import android.content.Context;

/**
 * Created by Jeff on 2016/5/3.
 */
public class MapFactory {

    private MapMoudle map;

    public enum MapType {
        //Baidu,
        Gaode;
    }

    /**
     * 定位模式
     */
    public enum LocationMode {
        Hight_Accuracy,//高精度
        Battery_Saving,//网络定位
        Device_Sensors,//GPS定位
    }

    public  MapMoudle createClient(Context context, MapType type, LocationMode mode) {

        if (map == null) {
            switch (type) {
//                case Baidu:
//                    map = new BaiduMap(context, mode);
//                    break;
                case Gaode:
                    map = new GaoDeMap(context, mode);
                    break;
            }
        }
        return map;
    }
}

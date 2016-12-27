package com.alen.framework.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Jeff on 2016/5/3.
 */
public class GaoDeMap implements MapMoudle<MapMoudle.FinishListener>, AMapLocationListener {

    private static final String TAG = "GaoDeMap";
    public AMapLocationClient mLocationClient = null;

    private MapFactory.LocationMode mode;

    public GaoDeMap(Context context, MapFactory.LocationMode mode) {

        Log.d(TAG, "高德地图");

        this.mode = mode;

        mLocationClient = new AMapLocationClient(context.getApplicationContext());

        mLocationClient.setLocationListener(this);

        initOption();
    }

    private void initOption() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式
        if (mode == MapFactory.LocationMode.Battery_Saving) {
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        } else if (mode == MapFactory.LocationMode.Device_Sensors) {
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        } else {
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        }
        option.setNeedAddress(true);
        mLocationClient.setLocationOption(option);
    }

    public void startLocation() {
        mLocationClient.startLocation();
    }

    @Override
    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    /**
     * 销毁
     */
    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    public FinishListener<AMapLocation> listener = null;

    public void setFinishListener(FinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                if (listener != null) {
                    listener.getInfo(aMapLocation);
                }
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }
}

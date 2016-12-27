package com.example.gaodemap;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextureMapView textureMapView;
    private AMap aMap;
    private UiSettings uiSettings;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener onLocationChangedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureMapView = (TextureMapView) findViewById(R.id.map_view);
        textureMapView.onCreate(savedInstanceState);
        aMap = textureMapView.getMap();

        //设置地图类型
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        uiSettings = aMap.getUiSettings();

        // 显示默认的定位按钮
        uiSettings.setMyLocationButtonEnabled(true);

        //显示缩放控件
        uiSettings.setZoomControlsEnabled(true);
        //显示比例尺控件
        uiSettings.setScaleControlsEnabled(true);
        //显示指南针
        uiSettings.setCompassEnabled(true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行textureMapView.onDestroy()，实现地图生命周期管理
        textureMapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行textureMapView.onResume ()，实现地图生命周期管理
        textureMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行textureMapView.onPause ()，实现地图生命周期管理
        textureMapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行textureMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        textureMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {

                Toast.makeText(MainActivity.this, "经度:" + amapLocation.getLongitude() + "，纬度" + amapLocation.getLatitude() +
                        "，当前位于：" + amapLocation.getProvince() +
                        amapLocation.getCity() +
                        amapLocation.getDistrict() +
                        amapLocation.getStreet() +
                        amapLocation.getStreetNum(), Toast.LENGTH_LONG).show();
                if (onLocationChangedListener != null) {
                    //显示当前位置的小蓝点
                    onLocationChangedListener.onLocationChanged(amapLocation);
                    aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()),//新的中心点坐标
                            18, //新的缩放级别
                            40, //俯仰角0°~45°（垂直与地图时为0）
                            0  ////偏航角 0~360° (正北方为0)
                    )), 2000, new AMap.CancelableCallback() {
                        @Override
                        public void onFinish() {
                            Toast.makeText(MainActivity.this, "定位完成", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ;
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.e(TAG, "activate: ");
        this.onLocationChangedListener = onLocationChangedListener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
            //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//            mLocationOption.setInterval(1000);
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            mLocationOption.setHttpTimeOut(20000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        } else {
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        Log.e(TAG, "deactivate: ");
        onLocationChangedListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
}

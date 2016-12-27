package com.alen.framework.activity;

import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseToolbarActivity;
import com.alen.framework.map.GaoDeMap;
import com.alen.framework.map.MapFactory;
import com.alen.framework.map.MapMoudle;
import com.alen.framework.utils.Logger;
import com.alen.framework.utils.SettingUtils;
import com.alen.framework.utils.ToastUtils;
import com.amap.api.location.AMapLocation;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

/**
 * 签到位置
 * Created by Jeff on 2016/5/6.
 */
public class SignInPosActivity extends BaseToolbarActivity {

    private TextView tvPosition; //当前位置
    private TextView tvTime; //当前时间

    private ImageView ivPosit; //重新定位
    private Button btnGps; //打开GPS
    private Button btnOk; //确认
    private GaoDeMap map;

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_sign_in_pos, null);

        tvPosition = (TextView) view.findViewById(R.id.tv_position);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        ivPosit = (ImageView) view.findViewById(R.id.iv_posit);
        btnGps = (Button) view.findViewById(R.id.btn_gps);
        btnOk = (Button) view.findViewById(R.id.btn_ok);

        return view;
    }

    @Override
    public void setListener() {
        ivPosit.setOnClickListener(this);
        btnGps.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void initData() {
        map = new GaoDeMap(this, MapFactory.LocationMode.Hight_Accuracy);
        map.setFinishListener(new MapMoudle.FinishListener<AMapLocation>() {
            @Override
            public void getInfo(AMapLocation info) {
                Logger.d(info.getAddress());
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.iv_posit:
                startPosit();
                break;
            case R.id.btn_gps:
                //跳转到Gps设置页面
                SettingUtils.jumpGpsSetting(this);
                break;
            case R.id.btn_ok:
                // TODO: 2016/5/6 提交
                break;
        }
    }

    /**
     * 开启定位
     */
    private void startPosit() {
        // TODO: 2016/5/6 定位
        Acp.getInstance(this).request(new AcpOptions.Builder()
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION).build(), new AcpListener() {
            @Override
            public void onGranted() {
                map.startLocation();
                ToastUtils.showShort(SignInPosActivity.this, "正在定位...");
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showShort(SignInPosActivity.this, "权限被拒绝,可能定位不准哟亲");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (map != null) {
            map.onDestroy();
            map = null;
        }
    }
}

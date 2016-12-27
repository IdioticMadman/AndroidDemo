package com.alen.framework.activity;

import android.view.View;
import android.widget.TextView;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseToolbarActivity;
import com.alen.framework.global.Constants;

/**
 * Created by Jeff on 2016/5/6.
 */
public class ChangePhoneActivity extends BaseToolbarActivity {

    private TextView tvHint;

    @Override
    public View initView() {
        setToolbarTitle("修改手机");
        showToolbarBack(true);
        View view = View.inflate(this, R.layout.activity_change_phone, null);
        tvHint = (TextView) view.findViewById(R.id.tv_hint);
        return view;
    }

    @Override
    public void initData() {
        // TODO: 2016/5/6  根据角色提示
        Constants.Role role = Constants.Role.PM;
        if (role == Constants.Role.PM) {
            tvHint.setText("若要修改手机号，请线下申请");
        } else {
            tvHint.setText("若要修改手机号，请线下向管理者申请");
        }
    }
}

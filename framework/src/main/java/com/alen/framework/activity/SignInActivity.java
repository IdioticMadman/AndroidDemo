package com.alen.framework.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseToolbarActivity;
import com.alen.framework.utils.ToastUtils;

/**
 * 签到
 * Created by Jeff on 2016/5/6.
 */
public class SignInActivity extends BaseToolbarActivity {

    private TextView tvWeek; //星期
    private TextView tvDate; //日期
    private TextView tvUserName; //用户名
    private ImageView ivSignIn; //签到

    @Override
    public View initView() {

        setToolbarTitle("签到");
        showToolbarBack(true);

        View view = View.inflate(this, R.layout.activity_sign_in, null);
        tvWeek = (TextView) view.findViewById(R.id.tv_week);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        ivSignIn = (ImageView) view.findViewById(R.id.iv_sign_in);

        return view;
    }

    @Override
    public void setListener() {
        ivSignIn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sign_in:
                ToastUtils.showShort(this, "签到啦");
                startActivity(new Intent(this, SignInPosActivity.class));
                break;
        }
    }

    @Override
    public void initData() {
        // TODO: 2016/5/6 获取数据
    }
}

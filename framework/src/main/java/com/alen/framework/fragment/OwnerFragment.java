package com.alen.framework.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alen.framework.R;
import com.alen.framework.activity.ChangePWActivity;
import com.alen.framework.activity.ChangePhoneActivity;
import com.alen.framework.fragment.base.BaseFragment;

/**
 * Created by Jeff on 2016/5/5.
 */
public class OwnerFragment extends BaseFragment {

    private LinearLayout llInfo;//信息
    private RelativeLayout rlChangePW; //修改密码
    private RelativeLayout rlChangePhone;

    private ImageView ivAvatar; //头像
    private TextView tvUserName; //用户名

    @Override
    protected View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_owner, null);
        llInfo = (LinearLayout) view.findViewById(R.id.ll_info);

        ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);

        rlChangePW = (RelativeLayout) view.findViewById(R.id.rl_change_pw);
        rlChangePhone = (RelativeLayout) view.findViewById(R.id.rl_change_phone);
        return view;
    }

    @Override
    protected void setListener() {
        rlChangePW.setOnClickListener(this);
        rlChangePhone.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // TODO: 2016/5/6 获取用户信息
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_change_pw:
                startActivity(new Intent(getActivity(), ChangePWActivity.class));
                break;
            case R.id.rl_change_phone:
                startActivity(new Intent(getActivity(), ChangePhoneActivity.class));
                break;
        }
    }
}

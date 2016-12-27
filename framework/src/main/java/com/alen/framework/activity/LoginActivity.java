package com.alen.framework.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseToolbarActivity;

/**
 * Created by Jeff on 2016/5/5.
 */
public class LoginActivity extends BaseToolbarActivity {

    private Button btnLogin;

    @Override
    public View initView() {
        View view = View.inflate(LoginActivity.this, R.layout.activity_login, null);
        setToolbarTitle("登录");

        btnLogin = (Button) view.findViewById(R.id.btn_login);

        return view;
    }

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

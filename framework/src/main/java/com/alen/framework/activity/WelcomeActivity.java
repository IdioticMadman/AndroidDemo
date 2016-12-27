package com.alen.framework.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    private Button btnLogin;

    @Override
    public void initWidget() {
        setContentView(R.layout.activity_welcome);
        btnLogin = findView(R.id.btn_login);
    }

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}

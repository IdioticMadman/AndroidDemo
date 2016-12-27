package com.alen.framework.activity;

import android.view.View;
import android.widget.EditText;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseToolbarActivity;

/**
 * 修改密码
 * Created by Jeff on 2016/5/5.
 */
public class ChangePWActivity extends BaseToolbarActivity {

    private EditText etOld; //原密码
    private EditText etNew; //新密码
    private EditText etNewCheck; //确认新密码

    @Override
    public View initView() {
        setToolbarTitle("修改密码");
        showToolbarBack(true);
        View view = View.inflate(ChangePWActivity.this, R.layout.activity_change_pw, null);

        etOld = (EditText) view.findViewById(R.id.et_old);
        etNew = (EditText) view.findViewById(R.id.et_new);
        etNewCheck = (EditText) view.findViewById(R.id.et_new_check);

        return view;
    }

    public void click(View v) {
        // TODO: 2016/5/5 验证密码并确认
    }
}

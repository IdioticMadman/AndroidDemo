package com.alen.framework.activity;

import android.view.View;
import android.widget.RadioGroup;

import com.alen.framework.R;
import com.alen.framework.activity.base.BaseToolbarActivity;
import com.alen.framework.fragment.MessageFragment;
import com.alen.framework.fragment.OwnerFragment;
import com.alen.framework.fragment.WorkFragment;
import com.alen.framework.utils.FragmentUtils;
import com.alen.framework.utils.Logger;

/**
 * Created by Jeff on 2016/5/5.
 */
public class MainActivity extends BaseToolbarActivity {

    private RadioGroup bottomBtn; //底部按钮组
    private MessageFragment messageFragment;
    private WorkFragment workFragment;
    private OwnerFragment ownerFragment;

    @Override
    public View initView() {

        View view = View.inflate(MainActivity.this, R.layout.activity_main, null);
        setToolbarTitle("消息中心");
        bottomBtn = (RadioGroup) view.findViewById(R.id.btn_bottom);
        return view;
    }

    @Override
    public void initData() {
        messageFragment = new MessageFragment();
        FragmentUtils.addFragments(messageFragment);

        workFragment = new WorkFragment();
        FragmentUtils.addFragments(workFragment);

        ownerFragment = new OwnerFragment();
        FragmentUtils.addFragments(ownerFragment);

        FragmentUtils.replaceFragment(MainActivity.this, messageFragment);
    }

    @Override
    public void setListener() {

        bottomBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.btn_message:
                        Logger.d("消息中心");
                        setToolbarTitle("消息中心");
                        FragmentUtils.replaceFragment(MainActivity.this, messageFragment);
                        break;
                    case R.id.btn_work:
                        Logger.d("工作");
                        setToolbarTitle("工作");
                        FragmentUtils.replaceFragment(MainActivity.this, workFragment);
                        break;
                    case R.id.btn_owner:
                        Logger.d("我的");
                        setToolbarTitle("我的");
                        FragmentUtils.replaceFragment(MainActivity.this, ownerFragment);
                        break;
                }
            }
        });
    }
}

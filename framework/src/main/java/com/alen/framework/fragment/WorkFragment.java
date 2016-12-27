package com.alen.framework.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alen.framework.R;
import com.alen.framework.activity.SignInActivity;
import com.alen.framework.adapter.MyBaseAdapter;
import com.alen.framework.fragment.base.BaseFragment;
import com.alen.framework.global.Constants;
import com.alen.framework.holder.BaseHolder;
import com.alen.framework.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jeff on 2016/5/5.
 */
public class WorkFragment extends BaseFragment {

    private GridView gvWorks;
    private List<String> data;

    @Override
    protected View initView() {

        View view = View.inflate(getActivity(), R.layout.fragment_work, null);
        gvWorks = (GridView) view.findViewById(R.id.gv_works);

        gvWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpWork(position);
            }
        });

        return view;
    }

    /**
     * 根据GridView跳转
     *
     * @param position
     */
    private void jumpWork(int position) {
        switch (data.get(position)) {
            case "签到":
                Logger.d("签到");
                startActivity(new Intent(getActivity(), SignInActivity.class));
                break;
            case "人员管理":
                Logger.d("人员管理");

                break;
            case "床位状况":
                Logger.d("床位状况");

                break;
            case "财务状况":
                Logger.d("财务状况");

                break;
            case "员工考勤":
                Logger.d("员工考勤");

                break;
            case "拍摄巡视":
                Logger.d("拍摄巡视");

                break;
            case "巡视日志":
                Logger.d("巡视日志");

                break;
        }
    }

    @Override
    protected void initData() {
        MyBaseAdapter<String> adapter = new MyBaseAdapter<String>() {
            @Override
            public BaseHolder<String> getViewHolder() {
                return new ViewHolder(getActivity());
            }
        };
        // TODO: 2016/5/5 权限控制，根据不同的权限使用不用的data
        data = new ArrayList<>();
        Collections.addAll(data, Constants.Work_PM);
        adapter.setData(data);
        gvWorks.setAdapter(adapter);
    }

    static class ViewHolder extends BaseHolder<String> {

        private TextView tvWork;

        public ViewHolder(Context context) {
            super(context);
        }

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.adapter_work, null);
            tvWork = (TextView) view.findViewById(R.id.tv_work);
            return view;
        }

        @Override
        protected void refreshUI(String data) {
            tvWork.setText(data);
        }
    }
}

package com.robert.layout.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.robert.layout.R;
import com.robert.layout.adapter.MyBaseAdapter;
import com.robert.layout.bean.OtherOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    private ListView mListView;

    private List<OtherOrder> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_second);
        mListView = (ListView) findViewById(R.id.lv_list);
    }

    private void initData() {
        orders = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i = 0; i < 10; i++) {
            OtherOrder order = new OtherOrder(i, "测试", "黄瑞GR0325", "公用工程", "2016-2-2", bitmap);
            orders.add(order);
        }
        mListView.setAdapter(new SecondBaseAdapter(orders, SecondActivity.this));
    }

    public class SecondBaseAdapter extends MyBaseAdapter<OtherOrder> {

        public SecondBaseAdapter(List<OtherOrder> datas, Context context) {
            super(datas, context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(mContext, R.layout.item_second, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            if (mDatas.size() > 0) {
                OtherOrder order = (OtherOrder) mDatas.get(position);
                holder.tvOrder.setText(order.getOrder() + "");
                holder.tvProjectNo.setText(order.getProjectNo());
                holder.tvProjectName.setText(order.getProjectName());
                holder.tvProjectType.setText(order.getProjectType());
                holder.tvCompleteDate.setText(order.getCompleteDate());
            }

            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_project_no)
        TextView tvProjectNo;
        @BindView(R.id.tv_project_type)
        TextView tvProjectType;
        @BindView(R.id.tv_complete_date)
        TextView tvCompleteDate;
        @BindView(R.id.iv_result)
        ImageView ivResult;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


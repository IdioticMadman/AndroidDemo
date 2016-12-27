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
import com.robert.layout.bean.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv_list);
    }

    private void initData() {
        orders = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i = 0; i < 10; i++) {
            Order order = new Order(i, "2016-02", "2016-1-1", "人民广场", "2016-2-5", bitmap, bitmap);
            orders.add(order);
        }
        mListView.setAdapter(new MainBaseAdapter(orders, MainActivity.this));
    }

    public class MainBaseAdapter extends MyBaseAdapter<Order> {

        public MainBaseAdapter(List<Order> datas, Context context) {
            super(datas, context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(mContext, R.layout.item_main, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            if (mDatas.size() > 0) {
                Order order = (Order) mDatas.get(position);
                holder.tvOrder.setText(order.getOrder() + "");
                holder.tvNo.setText(order.getNo());
                holder.tvDate.setText(order.getDate());
                holder.tvCompleteDate.setText(order.getCompleteDate());
                holder.tvProjectName.setText(order.getProjectName());
            }

            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_no)
        TextView tvNo;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_complete_date)
        TextView tvCompleteDate;
        @BindView(R.id.iv_result)
        ImageView ivResult;
        @BindView(R.id.iv_sync)
        ImageView ivSync;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}




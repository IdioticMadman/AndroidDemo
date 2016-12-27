package com.example.robert.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private Context mContext;
    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景"};

    private int resId[] = {R.mipmap.img1, R.mipmap.img2, R.mipmap.img2, R.mipmap.img3,
            R.mipmap.img4, R.mipmap.img5, R.mipmap.img3, R.mipmap.img1};
    private RecyclerView recyclerView;
    private List<ModelBean> data;
    private RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initToolbar();
        initWidget();
        initData();
        setListener();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("标题");


    }

    private void initWidget() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        //设置布局显示方式
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayout.HORIZONTAL, true));
        //设置添加删除item时候的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < des.length; i++) {
            ModelBean modelBean = new ModelBean(des[i], resId[i]);
            data.add(modelBean);
        }

        recyclerAdapter = new RecyclerAdapter(mContext, data);
        recyclerView.setAdapter(recyclerAdapter);


    }

    private void setListener() {

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                Toast.makeText(MainActivity.this, ((ModelBean) object).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.grid_horizontal:
                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, GridLayout.HORIZONTAL, true));
                        break;
                    case R.id.grid_vertical:
                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                        break;
                    case R.id.linear_horizontal:
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, true));
                        break;
                    case R.id.linear_vertical:
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, true));
                        break;
                    case R.id.flow:
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
                        break;
                    case R.id.add:
                        ModelBean bean = new ModelBean("新加的",R.mipmap.img1);
                        data.add(bean);
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerAdapter.notifyItemInserted(0);
                        recyclerAdapter.notifyItemRemoved(0);
                        recyclerAdapter.notifyItemChanged(0);
                        recyclerAdapter.notifyItemMoved(0,1);
                        recyclerAdapter.notifyItemRangeChanged(0,2);
                        recyclerAdapter.notifyItemRangeInserted(0,2);
                        recyclerAdapter.notifyItemRangeRemoved(0,2);
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

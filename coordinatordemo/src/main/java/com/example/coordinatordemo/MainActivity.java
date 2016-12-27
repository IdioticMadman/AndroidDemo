package com.example.coordinatordemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import static com.example.coordinatordemo.BlurUtil.fastblur;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LinearLayout head_layout;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        //设置toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        head_layout = (LinearLayout) findViewById(R.id.head_layout);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            head_layout.setBackground(new BitmapDrawable(getResources(), fastblur(MainActivity.this, bitmap, 180)));
        } else {
            head_layout.setBackgroundDrawable(new BitmapDrawable(getResources(), fastblur(MainActivity.this, bitmap, 180)));
        }
        //折叠的toolbar
        final CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        assert mCollapsingToolbarLayout != null;
        mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(getResources(), fastblur(MainActivity.this, bitmap, 180)));
        //设置toolbar的title
        assert app_bar_layout != null;
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("se lang");
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }
            }
        });

        //设置toolbar上的menu点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.web_view:
                        msg += "博客跳转";
                        break;
                    case R.id.wei_bo:
                        msg += "微博跳转";
                        break;
                    case R.id.action_setting:
                        msg += "设置";
                        break;
                }
                if (!TextUtils.isEmpty(msg)) {
                    Snackbar.make(mCoordinatorLayout, msg, Snackbar.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.toolbar_tab);
        ViewPager main_vp_container = (ViewPager) findViewById(R.id.main_vp_container);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        main_vp_container.setAdapter(viewPagerAdapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp_container));
        //会重置tabLayout的布局文件上的
        //tabLayout.setupWithViewPager(main_vp_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        int pagerCount = 5;
       // String tabTitles[] = new String[]{"", "分享", "收藏", "关注", "关注者"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return pagerCount;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitles[position];
//        }
    }
}

package com.example.robert.helloworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.robert.helloworld.FrameTestActivity
 * @author: robert
 * @date: 2016-10-18 11:44
 */

public class FrameTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_test);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_container);
        TextView textView = new TextView(this);
        textView.setText(FrameTestActivity.class.getSimpleName());
        frameLayout.addView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change) {
            TestFragment testFragment = new TestFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, testFragment).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.robert.filedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lv_files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        initData();
        lv_files = (ListView) findViewById(R.id.lv_files);
    }

    private void initData() {

    }

    private void setListener() {

    }

    private void initView() {

    }
}

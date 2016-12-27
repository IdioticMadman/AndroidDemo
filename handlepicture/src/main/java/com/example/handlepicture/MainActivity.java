package com.example.handlepicture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        TestViewAdapter adapter = new TestViewAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setUrls(new int[]{R.mipmap.ic_add_img_default, R.mipmap.ic_add_img_default, R.mipmap.ic_add_img_default, R.mipmap.ic_add_img_default});
        adapter.notifyDataSetChanged();
    }
}

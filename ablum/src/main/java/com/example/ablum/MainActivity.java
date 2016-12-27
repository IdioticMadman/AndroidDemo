package com.example.ablum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoAdapter = new PhotoAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_image);
        List<ImgFile> imgFiles = Arrays.asList(new ImgFile(1, "", ""), new ImgFile(1, "", ""), new ImgFile(1, "", ""), new ImgFile(1, "", ""), new ImgFile(1, "", ""));
        photoAdapter.setImgFiles(imgFiles);
        mRecyclerView.setAdapter(photoAdapter);
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new GridDividerDecorator(this));
        photoAdapter.notifyDataSetChanged();
    }
}

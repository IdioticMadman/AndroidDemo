package com.example.handlepicture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by robert on 2016/12/16.
 */

public class TestViewAdapter extends RecyclerView.Adapter<TestViewAdapter.TestViewHolder> {
    private static final String TAG = TestViewAdapter.class.getSimpleName();
    private int[] urls;
    private Context mContext;

    public TestViewAdapter(Context context) {
        this.mContext = context;
    }

    public void setUrls(int[] urls) {
        this.urls = urls;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
        Log.e(TAG, "onCreateViewHolder: " + parent.getWidth());
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + urls[position]);
        Glide.with(mContext).load(urls[position]).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: " + urls.length);
        return urls == null ? 0 : urls.length;
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        TestViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img_default);
        }
    }
}

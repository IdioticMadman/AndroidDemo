package com.example.ablum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 2016/11/15.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context mContext;
    private List<ImgFile> imgFiles;

    public PhotoAdapter(Context context) {
        this.mContext = context;
        this.imgFiles = new ArrayList<>();
    }

    public void setImgFiles(List<ImgFile> imgFiles) {
        this.imgFiles = imgFiles;
    }

    public void clearData() {
        imgFiles.clear();
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        view.getLayoutParams().height = parent.getWidth() / 3;
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        ImgFile imgFile = imgFiles.get(position);
        if (imgFile.getTag() == 1) {
            Glide.with(mContext).load(R.drawable.ic_add_img_default).into(holder.img);
        } else {
            Glide.with(mContext).load(imgFile.getThumbnail()).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return imgFiles == null ? 0 : imgFiles.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        PhotoViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_default);
        }
    }
}

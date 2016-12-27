package com.example.robert.helloworld;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author robert
 * @version 1.0
 * @time 2016/5/27.
 * @description ${CLASSNAME}
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyRecyclerHolder> {

    private Context mContext;
    private List<ModelBean> data;
    private Resources resources;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(Context context, List<ModelBean> data) {
        this.mContext = context;
        this.data = data;
        this.resources = context.getResources();
    }

    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card_view, parent, false);
        return new MyRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecyclerHolder holder, final int position) {

        final ModelBean modelBean = data.get(position);
        holder.iv_pic.setImageResource(modelBean.getResId());
        holder.tv_name.setText(modelBean.getTitle());
        Bitmap bitmap = BitmapFactory.decodeResource(resources, modelBean.getResId());
        //异步获得bitmap图片颜色值
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
                Palette.Swatch c = palette.getDarkVibrantSwatch();//有活力 暗色
                Palette.Swatch d = palette.getLightVibrantSwatch();//有活力 亮色
                Palette.Swatch f = palette.getMutedSwatch();//柔和
                Palette.Swatch a = palette.getDarkMutedSwatch();//柔和 暗色
                Palette.Swatch b = palette.getLightMutedSwatch();//柔和 亮色

                if (vibrant != null) {
                    int color1 = vibrant.getBodyTextColor();//内容颜色
                    int color2 = vibrant.getTitleTextColor();//标题颜色
                    int color3 = vibrant.getRgb();//rgb颜色

                    holder.tv_name.setBackgroundColor(
                            vibrant.getRgb());
                    holder.tv_name.setTextColor(
                            vibrant.getTitleTextColor());
                }
            }
        });

        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position, modelBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyRecyclerHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView iv_pic;

        public MyRecyclerHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name);
            iv_pic = (ImageView) itemView.findViewById(R.id.pic);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

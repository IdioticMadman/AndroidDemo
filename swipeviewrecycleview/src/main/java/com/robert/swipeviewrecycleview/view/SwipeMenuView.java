package com.robert.swipeviewrecycleview.view;

import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robert.swipeviewrecycleview.Bean.SwipeMenu;
import com.robert.swipeviewrecycleview.Bean.SwipeMenuItem;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.robert.swipeviewrecycleview.view.SwipeMenuLayout
 * @author: robert
 * @date: 2016-07-25 14:35
 */
public class SwipeMenuView extends LinearLayout implements View.OnClickListener {

    private SwipeMenuLayout mLayout;
    private SwipeMenu mMenu;
    private OnMenuItemClickListener onMenuItemClickListener;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SwipeMenuView(SwipeMenu menu) {
        super(menu.getContext());
        setOrientation(HORIZONTAL);
        mMenu = menu;
        List<SwipeMenuItem> items = mMenu.getItems();
        int id = 0;
        for (SwipeMenuItem item : items) {
            addItem(item, id++);
        }
    }

    private void addItem(SwipeMenuItem item, int id) {
        LayoutParams layoutParams = new LayoutParams(item.getWidth(), LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setId(id);
        parent.setOrientation(VERTICAL);
        parent.setGravity(Gravity.CENTER);
        parent.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            parent.setBackground(item.getBackground());
        } else {
            parent.setBackgroundDrawable(item.getBackground());
        }
        parent.setOnClickListener(this);
        addView(parent);

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }

        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }
    }

    private View createIcon(SwipeMenuItem item) {

        ImageView imageView = new ImageView(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(item.getBackground());
        } else {
            imageView.setBackgroundDrawable(item.getBackground());
        }
        return imageView;
    }

    private View createTitle(SwipeMenuItem item) {

        TextView tv = new TextView(getContext());
        tv.setText(item.getTitle());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getTitleSize());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(item.getTitleColor());

        return tv;
    }

    @Override
    public void onClick(View v) {
        if (onMenuItemClickListener != null && mLayout.isOpen()) {
            onMenuItemClickListener.onMenuItemClick(position, mMenu, v.getId());
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position, SwipeMenu menu, int index);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setLayout(SwipeMenuLayout layout) {
        this.mLayout = layout;
    }
}

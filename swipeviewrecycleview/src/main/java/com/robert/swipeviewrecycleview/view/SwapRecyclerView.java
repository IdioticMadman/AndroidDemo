package com.robert.swipeviewrecycleview.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by robert on 2016/8/4.
 */

public class SwapRecyclerView extends RecyclerView {

    private static final int TOUCH_STATE_NONE = 0;  //没有滑动
    private static final int TOUCH_STATE_X = 1;     //是否处于左右滑动
    private static final int TOUCH_STATE_Y = 2;     //是否处于上下滑动
    private int mTouchState = TOUCH_STATE_NONE;     //当前状态


    private Rect mTouchFrame = new Rect();          //点击后坐标产生的矩形
    private SwipeMenuLayout mTouchView;             //当前按下去的位置的那个view
    private int mTouchPosition = -1;                //当前按下去的位置
    private int oldPos = -1;                        //上一次按下去的位置
    private int mTouchSlop;
    private float mDownX;
    private float mDownY;
    private OnSwipeListener mOnSwipeListener;

    public SwapRecyclerView(Context context) {
        this(context, null);
    }

    public SwapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = e.getX();
                mDownY = e.getY();
                boolean handled = super.onInterceptTouchEvent(e);
                mTouchState = TOUCH_STATE_NONE;
                oldPos = mTouchPosition;
                //判断当前点击坐标下RecycleView的item的position
                int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(mTouchFrame);
                        //判断是否点击在该控件上
                        if (mTouchFrame.contains(x, y)) {
                            mTouchPosition = mFirstPosition + i;
                            break;
                        }
                    }
                }
                //通过for循环找到了
                if (mTouchPosition != -1) {
                    //通过position获取item的ViewHolder，并判断合法性
                    View childAt = getChildAt(mTouchPosition - mFirstPosition);
                    ViewHolder viewHolder = getChildViewHolder(childAt);
                    if (viewHolder.itemView instanceof SwipeMenuLayout) {
                        if (mTouchView != null && mTouchView.isOpen() && !inRangeOfView(mTouchView.getMenuView(), e)) {
                            return true;
                        }
                        mTouchView = (SwipeMenuLayout) childAt;
                    } else {
                        throw new RuntimeException("ViewHolder.itemView must be SwipeMenuLayout layout");
                    }
                    mTouchView.onSwipe(e);
                }
                return handled;
            case MotionEvent.ACTION_MOVE:
                float dy = Math.abs((e.getY() - mDownY));
                float dx = Math.abs((e.getX() - mDownX));
                //达到了边界值
                if (dy > mTouchSlop || dx > mTouchSlop) {
                    if (mTouchState == TOUCH_STATE_NONE) {
                        if (dy > mTouchSlop) {
                            mTouchState = TOUCH_STATE_Y;
                        } else if (dx > mTouchSlop) {
                            mTouchState = TOUCH_STATE_X;
                            if (mOnSwipeListener != null) {
                                mOnSwipeListener.onSwipeStart(mTouchPosition);
                            }
                        }
                    }
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (e.getAction() != MotionEvent.ACTION_DOWN && mTouchView == null) {
            return super.onTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTouchPosition == oldPos && mTouchView != null && mTouchView.isOpen()) {
                    mTouchState = TOUCH_STATE_X;
                    mTouchView.onSwipe(e);
                    return true;
                } else {
                    if (mTouchView != null && mTouchView.isOpen()) {
                        mTouchView.smoothCloseMenu();
                        mTouchView = null;
                        return super.onTouchEvent(e);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(e);
                    }
                    e.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(e);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(e);
                        if (!mTouchView.isOpen()) {
                            mTouchPosition = -1;
                            mTouchView = null;
                        } else {
                            mTouchView.getMenuView().setPosition(mTouchPosition);
                        }
                    }
                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeEnd(mTouchPosition);
                    }
                    e.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(e);
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }

    private boolean inRangeOfView(SwipeMenuView menuView, MotionEvent e) {
        int[] location = new int[2];
        menuView.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        if (e.getRawX() < x || e.getRawX() < (x + menuView.getX()) || e.getRawY() < y || e.getRawY() > (y + menuView.getHeight())) {
            return false;
        } else {
            return true;
        }
    }

    public void smoothCloseMenu(int position) {
        int firstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        if (position > 0) {
            View view = getChildAt(position - firstPosition);
            RecyclerView.ViewHolder viewHolder = getChildViewHolder(view);
            if (viewHolder.itemView instanceof SwipeMenuLayout) {
                SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) viewHolder.itemView;
                if (swipeMenuLayout.isOpen()) {
                    swipeMenuLayout.smoothCloseMenu();
                }
            }
        }
    }


    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }

    public interface OnSwipeListener {
        void onSwipeStart(int position);

        void onSwipeEnd(int position);
    }
}

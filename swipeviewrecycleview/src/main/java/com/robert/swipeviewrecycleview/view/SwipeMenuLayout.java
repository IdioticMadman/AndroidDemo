package com.robert.swipeviewrecycleview.view;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.robert.swipeviewrecycleview.view.SwipeMenuLayout
 * @author: robert
 * @date: 2016-07-25 14:36
 */
public class SwipeMenuLayout extends FrameLayout {


    private static final int STATE_OPEN = 1;
    private static final int STATE_CLOSE = 0;
    private static final int DURATION = 350;
    private View mContentView;
    private SwipeMenuView mMenuView;
    private GestureDetectorCompat mGestureDetector;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;
    private ScrollerCompat mOpenScroller;
    private ScrollerCompat mCloserScroller;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private boolean isFling;
    private int state = STATE_CLOSE;
    private int mDownX;
    private int mBaseX;

    public SwipeMenuLayout(View contentView, SwipeMenuView swipeMenuView) {
        this(contentView, swipeMenuView, null, null);
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView swipeMenuView, Interpolator openInterpolator, Interpolator closeInterpolator) {
        super(swipeMenuView.getContext());
        this.mContentView = contentView;
        this.mMenuView = swipeMenuView;
        this.mOpenInterpolator = openInterpolator;
        this.mCloseInterpolator = closeInterpolator;
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMinimumVelocity = (int) (ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity() * 2.2f);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mMenuView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mContentView);
        addView(mMenuView);
        GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                isFling = false;
                return super.onDown(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (e1.getX() - e2.getX() > mTouchSlop && Math.abs(velocityX) > mMinimumVelocity) {
                    isFling = true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }

        };

        mGestureDetector = new GestureDetectorCompat(getContext(), mGestureListener);

        mCloserScroller = ScrollerCompat.create(getContext(), mCloseInterpolator == null ? null : mCloseInterpolator);
        mOpenScroller = ScrollerCompat.create(getContext(), mOpenInterpolator == null ? null : mOpenInterpolator);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量menu的高度和宽度，和contentView的高度相同，
        mMenuView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContentView.layout(0, 0, getMeasuredWidth(), mContentView.getMeasuredHeight());
        mMenuView.layout(mContentView.getMeasuredWidth(), 0, getMeasuredWidth() + mMenuView.getMeasuredWidth(), mContentView.getMeasuredHeight());
    }

    private void swipe(int dis) {

        if (dis > mMenuView.getMeasuredWidth()) {
            dis = mMenuView.getMeasuredWidth();
        }

        if (dis < 0) {
            dis = 0;
        }

        mContentView.layout(-dis, mContentView.getTop(), mContentView.getWidth() - dis, getMeasuredHeight());

        mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(), mContentView.getWidth() + mMenuView.getWidth() - dis, mMenuView.getBottom());
    }

    public void onSwipe(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                isFling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dis = (int) (event.getX() - mDownX);
                if (state == STATE_CLOSE) {
                    dis += mMenuView.getWidth();
                }
                swipe(dis);
                break;
            case MotionEvent.ACTION_UP:
                //快速滑动，或者滑动超过了屏幕的一半则，打开，反之则关闭
                if (isFling || (mDownX - event.getX()) > mMenuView.getWidth() / 2) {
                    smoothOpenMenu();
                } else {
                    smoothCloseMenu();
                }
                break;
        }
    }

    /**
     * 平滑关闭菜单
     */
    public void smoothCloseMenu() {
        state = STATE_CLOSE;
        mBaseX = -mContentView.getLeft();
        //让菜单关闭，关闭的宽度就是mContentView向左偏移量
        mCloserScroller.startScroll(0, 0, mBaseX, 0, DURATION);
        postInvalidate();
    }

    /**
     * 平滑打开菜单
     */
    public void smoothOpenMenu() {
        state = STATE_OPEN;
        mOpenScroller.startScroll(-mContentView.getLeft(), 0, mMenuView.getWidth(), 0, DURATION);
        postInvalidate();
    }

    /**
     * 无平滑的关闭mMenuView
     */
    public void closeMenu() {
        if (mCloserScroller.computeScrollOffset()) {
            mCloserScroller.abortAnimation();
        }
        if (state == STATE_OPEN) {
            state = STATE_CLOSE;
            swipe(0);
        }
    }

    /**
     * 无平滑的打开mMenuView
     */
    public void openMenu() {
        if (state == STATE_CLOSE) {
            state = STATE_OPEN;
            swipe(mMenuView.getWidth());
        }
    }

    @Override
    public void computeScroll() {
        if (state == STATE_OPEN) {
            if (mOpenScroller.computeScrollOffset()) {
                swipe(mOpenScroller.getCurrX());

                postInvalidate();
            }
        } else {
            if (mCloserScroller.computeScrollOffset()) {
                swipe(mBaseX - mCloserScroller.getCurrX());
                postInvalidate();
            }
        }
    }

    public boolean isOpen() {
        return state == STATE_OPEN;
    }

    public SwipeMenuView getMenuView() {
        return mMenuView;
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return mCloseInterpolator;
    }
}

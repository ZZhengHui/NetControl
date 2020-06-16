package com.zzh.netcontrol.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * Time 2019/12/26
 * Author Zzh
 * Description
 */
public class SlidingMenu extends ViewGroup {

    private ViewDragHelper mDragHelper;
    private View mMenuView;
    private View mContentView;

    public SlidingMenu(@NonNull Context context) {
        this(context, null);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setOrientation(HORIZONTAL);
        mDragHelper = ViewDragHelper.create(this, 1.0F, mDragCallBack);
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenuView.measure(widthMeasureSpec - 200, heightMeasureSpec);
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mMenuView != null) {
            mMenuView.layout(-mMenuView.getMeasuredWidth(), 0, 0, mMenuView.getMeasuredHeight());
        }
        if (mContentView != null) {
            mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    ViewDragHelper.Callback mDragCallBack = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mMenuView;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mMenuView, pointerId);
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            Log.d("left:", left + "");
            return Math.max(Math.min(left, 0), -child.getWidth());
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            invalidate();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (xvel > 300 || releasedChild.getLeft() > -releasedChild.getWidth() * 0.5) {
                mDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
            } else {
                mDragHelper.settleCapturedViewAt(-releasedChild.getWidth(), releasedChild.getTop());
            }
            invalidate();
        }
    };

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount >= 2) {
            mMenuView = getChildAt(1);
            mContentView = getChildAt(0);
        }
    }
}

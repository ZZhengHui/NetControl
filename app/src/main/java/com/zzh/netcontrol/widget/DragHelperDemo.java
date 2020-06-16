package com.zzh.netcontrol.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * Time 2019/12/26
 * Author Zzh
 * Description
 */
public class DragHelperDemo extends LinearLayout {

    private final String TAG = "DragHelperDemo";
    private Point mAutoBackOriginPos = new Point();
    private ViewDragHelper dragHelper;
    private View contentView;

    public DragHelperDemo(@NonNull Context context) {
        this(context, null);
    }

    public DragHelperDemo(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragHelperDemo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1.0F, dragCallback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == contentView;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int topBound = getPaddingTop();
            int bottomBound = getHeight() - contentView.getHeight() - topBound;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int leftBound = getPaddingLeft();
            int rightBound = getWidth() - leftBound - contentView.getWidth();
            int min = Math.min(Math.max(left, leftBound), rightBound);
            Log.e(TAG, "left:" + left + "  leftBound:" + leftBound + "  rightBound:" + rightBound + "  min:" + min);
            return min;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            dragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
            invalidate();
        }

    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mAutoBackOriginPos.x = contentView.getLeft();
        mAutoBackOriginPos.y = contentView.getTop();
    }
}

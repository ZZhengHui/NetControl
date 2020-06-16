package com.zzh.netcontrol.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * Time 2020/2/24
 * Author Zzh
 * Description 可以滑动删除的item
 */
public class DeleteItemLayout extends FrameLayout {

    private static final String TAG = "DeleteItemLayout";
    private ViewDragHelper mDragHelper;
    private LinearLayout mContentView;
    private LinearLayout mDeleteView;
    private int mDelWidth;
    private int mContentWidth;
    private int mDelHeight;
    private int mContentHeight;
    private float downX;
    private float downY;

    public DeleteItemLayout(Context context) {
        this(context, null);
    }

    public DeleteItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeleteItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = (LinearLayout) getChildAt(0);
        mDeleteView = (LinearLayout) getChildAt(1);
        mDragHelper = ViewDragHelper.create(this, 1.0F, mDragCallback);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentWidth = mContentView.getMeasuredWidth();
        mContentHeight = mContentView.getMeasuredHeight();

        mDelWidth = mDeleteView.getMeasuredWidth();
        mDelHeight = mDeleteView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContentView.layout(0, 0, mContentWidth, mContentHeight);
        mDeleteView.layout(mContentWidth, 0, mContentWidth + mDelWidth, mDelHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (DeleteItemManager.getInstance().otherItemOpened(this)) {        //当有其他item处于open状态时 关闭
            DeleteItemManager.getInstance().closeItem();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                float dx = Math.abs(moveX - downX);
                float dy = Math.abs(moveY - downY);
                if (dx >= dy) {      //此时item横向滑动
                    requestDisallowInterceptTouchEvent(true);       //请求父控件不拦截事件 解决和recycler的滑动冲突
                }
                break;
        }
        mDragHelper.processTouchEvent(event);
        return true;
    }

    ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int newLeft;
            if (child == mContentView) {
                newLeft = Math.min(0, Math.max(left, -mDelWidth));
            } else {
                newLeft = Math.min(mContentWidth, Math.max(left, mContentWidth - mDelWidth));
            }
            return newLeft;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mContentView) {
                mDeleteView.offsetLeftAndRight(dx);
            } else {
                mContentView.offsetLeftAndRight(dx);
            }
            invalidate();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.e("xvel:", xvel + "");
            if (xvel < -2000) {   //    手抬起瞬间向右拖动的速度
                open();
                return;
            }
            if (xvel > 2000) {    //     手抬起瞬间向左拖动的速度
                close();
                return;
            }
            if (-mContentView.getLeft() >= mDelWidth / 2) {
                open();
            } else {
                close();
            }
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return mDelWidth;
        }
    };

    private void open() {
        mDragHelper.smoothSlideViewTo(mContentView, -mDelWidth, 0);
        mDragHelper.smoothSlideViewTo(mDeleteView, mContentWidth - mDelWidth, 0);
        ViewCompat.postInvalidateOnAnimation(DeleteItemLayout.this);
        DeleteItemManager.getInstance().setDeleteItem(this);
    }

    public void close() {
        mDragHelper.smoothSlideViewTo(mContentView, 0, 0);
        mDragHelper.smoothSlideViewTo(mDeleteView, mContentWidth, 0);
        ViewCompat.postInvalidateOnAnimation(DeleteItemLayout.this);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}

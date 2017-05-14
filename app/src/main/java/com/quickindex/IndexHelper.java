package com.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tony on 2017/5/2.
 */
public class IndexHelper extends View {
    private String[] textArr = {"#",
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static int DEFAULT_COLOR = 32;
    private static int SELECTED_COLOR = 46;
    private Paint paint;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private float mUnitHeight;

    public IndexHelper(Context context) {
        this(context, null);
    }

    public IndexHelper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexHelper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(DEFAULT_COLOR);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
        mUnitHeight = mMeasuredHeight * 1.0f / textArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int index = 0; index < textArr.length; index++) {
            float x = mMeasuredWidth / 2;
            float y = mUnitHeight / 2 + getTextHeight(textArr[index]) / 2 + mUnitHeight * index;
            if (index == lastDrawPosition) {
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
                paint.setTextSize(SELECTED_COLOR);
                paint.setUnderlineText(true);
                canvas.drawText(textArr[index], x, y, paint);
                paint.setColor(Color.DKGRAY);
                paint.setTextSize(DEFAULT_COLOR);
                paint.setUnderlineText(false);
            } else {
                canvas.drawText(textArr[index], x, y, paint);
            }
        }
        lastDrawPosition = -1;
    }

    private int prePosition = -1;
    //这个变量记录了当前哪个字母应该变色和加下划线,解决了因快速滑动,还没来得及重绘,prePosition被置为-1
    //而导致onDraw方法中拿到的prePosition是-1的情况引起的bug.
    private int lastDrawPosition = -1;
    private float mTouchY;
    private int mPosition;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mTouchY = event.getY();
                //根据点击的位置获取到索引position
                mPosition = (int) (mTouchY / mUnitHeight);
                if (prePosition != mPosition && mPosition >= 0 && mPosition < textArr.length) {
                    prePosition = mPosition;
                    lastDrawPosition = mPosition;
                    if (mListener != null) {
                        mListener.onLetterSelect(textArr[mPosition]);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                prePosition = -1;
                break;
        }
        return true;
    }

    /**
     * 获取文本的高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.bottom - bounds.top;
    }

    //根据传递进来的数据判断到底当前应该跳转到哪里.
    public void setCurrentPostion(int charAt) {
        int quickIndexPosition = 0;
        if (charAt >= 'A' && charAt <= 'Z') {
            quickIndexPosition = charAt - 'A' + 1;
        }
        lastDrawPosition = quickIndexPosition;
        invalidate();
    }

    private OnLetterSelectedListener mListener;

    public void setOnLetterSelectedListener(OnLetterSelectedListener listener) {
        mListener = listener;
    }

    public interface OnLetterSelectedListener {
        void onLetterSelect(String letter);
    }
}
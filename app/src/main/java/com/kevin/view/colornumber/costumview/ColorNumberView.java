package com.kevin.view.colornumber.costumview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kevin.view.colornumber.R;

/**
 */

public class ColorNumberView extends View {

    private String mNumber;
    private int mNumberColor = 0xffffff;
    private int mBackgroundColor = 0x000000;
    private int mNumberSize = 15;
    private int mGapSize = 0;
    private Paint mNumberPaint, mBGPaint;
    private int mBoxWidth, mBoxHeight;
    private int mBoxRadius;

    public ColorNumberView(Context context) {
        this(context, null);
    }

    public ColorNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorNumberView);
        try {
            mNumber = a.getString(R.styleable.ColorNumberView_number);
            mNumberColor = a.getColor(R.styleable.ColorNumberView_numberColor, 0xffffff);
            mBackgroundColor = a.getColor(R.styleable.ColorNumberView_backgroundColor, 0x000000);
            mGapSize = a.getDimensionPixelSize(R.styleable.ColorNumberView_gapSize, 0);
            mNumberSize = a.getDimensionPixelSize(R.styleable.ColorNumberView_numberSize, 15);
            mBoxHeight = a.getDimensionPixelSize(R.styleable.ColorNumberView_boxHeight, 0);
            mBoxWidth = a.getDimensionPixelSize(R.styleable.ColorNumberView_boxWidth, 0);
            mBoxRadius = a.getDimensionPixelSize(R.styleable.ColorNumberView_boxRadius, 0);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mNumberPaint = new Paint();
        mNumberPaint.setColor(mNumberColor);
        mNumberPaint.setAntiAlias(true);
        mNumberPaint.setTextSize(mNumberSize);
        mBGPaint = new Paint();
        mBGPaint.setColor(mBackgroundColor);
        mTextHeight = (int) ((mNumberPaint.descent() - mNumberPaint.ascent()));
        mTextWidth = (int) mNumberPaint.measureText(mNumber);
        mCharWidth = (int) mNumberPaint.measureText("0");
        descent = (int) mNumberPaint.descent();
        ascent = (int) mNumberPaint.ascent();
        totalGap = mGapSize * (mNumber.length() + 1);



    }

    int mCharWidth;
    int descent;
    int ascent;
    int baseY;
    int baseX;
    int mWidth;
    int mHeight;
    int mTextHeight;
    int mTextWidth;
    int totalGap;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = mTextWidth + getPaddingLeft() + getPaddingRight() + totalGap;
        mHeight = mTextHeight + getPaddingBottom() + getPaddingTop();
        baseY = mHeight / 2 - (descent + ascent) / 2;
        baseX = (mWidth - mTextWidth) / 2 - totalGap / 2;


        setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec), resolveSize(mHeight, heightMeasureSpec));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mNumber.length(); i++) {
            int curX = baseX + mGapSize * (i + 1) + mCharWidth * i;
            int x = curX + mCharWidth / 2 - mBoxWidth / 2;
            int y = (mHeight-mBoxHeight)/2;
            RectF r = new RectF(x,y,x+mBoxWidth,y+mBoxHeight);
            canvas.drawRoundRect(r, mBoxRadius, mBoxRadius, mBGPaint);
            canvas.drawText(mNumber.substring(i, i + 1), curX, baseY, mNumberPaint);
        }


    }
}

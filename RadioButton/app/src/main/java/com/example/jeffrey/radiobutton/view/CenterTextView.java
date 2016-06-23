package com.example.jeffrey.radiobutton.view;

/**
 * Created by Administrator on 2016/6/23.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2015/3/17.
 */
public class CenterTextView extends View {
    RectF mRect;
    Paint mLinePaint;
    Paint mCirclePaint;
    Paint mTextPaint;

    private String text = "80%";

    int a[] = new int[]{android.R.attr.text};
//    public CenterTextView(Context context) {
//        super(context);
//        initPaint();
//    }

    public CenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(attrs);
    }

    private void initPaint(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, a);
        text = typedArray.getString(0);
        typedArray.recycle();

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setColor(Color.GREEN);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, getContext().getResources().getDisplayMetrics());
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF leftTopRect = new RectF(mRect.left,mRect.top,mRect.centerX(),mRect.centerY());
        RectF rightBottmRect = new RectF(mRect.centerX(),mRect.centerY(),mRect.right,mRect.bottom);

        doDraw(canvas,leftTopRect);
        doDraw(canvas,rightBottmRect);
    }

    private void doDraw(Canvas canvas, RectF rect) {
        //draw circle
        canvas.drawCircle(rect.centerX(),rect.centerY(),rect.width()/2,mCirclePaint);
        //draw centerLine horizontal
        canvas.drawLine(rect.left, rect.centerY(), rect.right, rect.centerY(), mLinePaint);
        //draw centerLine vertical
        canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.bottom, mLinePaint);

        Rect rectText = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rectText);
        //这种会文字偏上
//        canvas.drawText(text,0,text.length(),mRect.centerX()-rectText.width()/2 ,mRect.centerY()-rectText.height()/2,mTextPaint);
        //x正确了 ,y有点偏
        //canvas.drawText(text,0,text.length(),mRect.centerX() -rectText.width()/2,mRect.centerY()+rectText.height()/2,mTextPaint);
        //百度出来的解决方法
        Paint.FontMetricsInt fmi = mTextPaint.getFontMetricsInt();
        float y = rect.centerY() - (fmi.bottom - fmi.top) / 2f - fmi.top;
        canvas.drawText(text, 0, text.length(), rect.centerX(), y, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        float left = 0;
        float top = 0;

        mRect = new RectF(left, top, width + left, height + top);
        Log.d("fei", "onMeasure width = " + width + " height = " + height + " rect=" + mRect.toString());
        Log.d("fei", "centerX = " + mRect.centerX() + "centerY= " + mRect.centerY());
    }
}




package com.example.jeffrey.radiobutton.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RadioButton;

import com.example.jeffrey.radiobutton.R;

/**
 * Created by Li on 2016/6/23.
 */
public class NumberRadioButton extends RadioButton {
    private int mNum;

    private Paint mNumPaint;
    private Paint mCirclePaint;
    private Paint mLinePaint;
    float numPaddingRight = 0;//dp
    float numPaddingTop = 0;//dp

    private int defalutRadisPlus = 10;//px
    private int mTextSize;

    private boolean debug = false;

    public NumberRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public NumberRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init( Context context, AttributeSet attrs){
        TypedArray  typedArray = context.getResources().obtainAttributes(attrs,R.styleable.NumberRadioButton);
        numPaddingRight = typedArray.getDimension(R.styleable.NumberRadioButton_numPaddingRight,0);
        numPaddingTop = typedArray.getDimension(R.styleable.NumberRadioButton_numPaddingTop,0);
        mNum = typedArray.getInt(R.styleable.NumberRadioButton_num,0);
        typedArray.recycle();

        int[] sysAttrs = new int[]{android.R.attr.textSize};
        TypedArray  sysArray = context.getResources().obtainAttributes(attrs,sysAttrs);
        mTextSize =sysArray.getInt(0,12);

        sysArray.recycle();

        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,mTextSize,getResources().getDisplayMetrics()));//sp??
        mNumPaint.setColor(Color.WHITE);
        mNumPaint.setTextAlign(Paint.Align.CENTER);

        mCirclePaint= new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);


        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mNum >0 ){
            String text = String.valueOf(mNum);
            Rect numRect = new Rect();
            mNumPaint.getTextBounds(text,0,text.length(),numRect);

            //1 算出文字矩形rect
            int maxLength = numRect.width()>numRect.height() ? numRect.width() : numRect.height();
            maxLength += defalutRadisPlus*2;
            int left = (int) (getWidth() - numPaddingRight -maxLength) ;
            int top  = (int) numPaddingRight;
            int right = left + maxLength;
            int bottom = top + maxLength ;

            Rect textRect = new Rect(left,top,right,bottom);

            doDraw(String.valueOf(mNum),canvas,textRect);
        }

    }


    private void doDraw(String text,Canvas canvas, Rect rect) {
        //draw circle
        canvas.drawCircle(rect.centerX(),rect.centerY(),rect.width()/2,mCirclePaint);
        Rect rectText = new Rect();
        mNumPaint.getTextBounds(text, 0, text.length(), rectText);
        //这种会文字偏上
//        canvas.drawText(text,0,text.length(),mRect.centerX()-rectText.width()/2 ,mRect.centerY()-rectText.height()/2,mTextPaint);
        //x正确了 ,y有点偏
        //canvas.drawText(text,0,text.length(),mRect.centerX() -rectText.width()/2,mRect.centerY()+rectText.height()/2,mTextPaint);
        //百度出来的解决方法
        Paint.FontMetricsInt fmi = mNumPaint.getFontMetricsInt();
        float y = rect.centerY() - (fmi.bottom - fmi.top) / 2f - fmi.top;
        canvas.drawText(text, 0, text.length(), rect.centerX(), y, mNumPaint);

        if (debug){
            //draw centerLine horizontal
            canvas.drawLine(rect.left, rect.centerY(), rect.right, rect.centerY(), mLinePaint);
            //draw centerLine vertical
            canvas.drawLine(rect.centerX(), rect.top, rect.centerX(), rect.bottom, mLinePaint);
        }
    }



    public void setDebug(boolean debug){
        this.debug = debug;
        invalidate();

    }
    public void setNum(int num){
        this.mNum = num;
    }
}

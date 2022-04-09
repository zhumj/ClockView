package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import java.util.Calendar;

/**
 * @ProjectName : My Application
 * @Author : zhumj
 * @Time : 2022/4/8 15:55
 * @Description : 自定义时钟
 */
public class ZClockView extends View {

    private final int CLOCK_STEP = 1000;

    private Paint outerCirclePaint;// 外圆画笔对象
    public float outerRadius; // 外圆半径
    private float outerCircleWidth;// 外圆宽度
    private @ColorInt int outerCircleColor;// 外圆颜色

    private Paint innerCirclePaint; // 内圆画笔对象
    public float innerRadius; // 内圆半径
    private @ColorInt int innerCircleColor;// 内圆颜色

    private Paint centerCirclePaint; // 大刻度画笔对象
    private float centerRadius;// 圆心半径
    private @ColorInt int centerCircleColor;// 圆心颜色

    private Paint textPaint; // 文字画笔对象
    public float textSize; // 文字的大小
    public float textPadding; // 文字离刻度线距离
    public @ColorInt int textColor; // 文字的颜色

    private Paint bigScaleLinePaint; // 大刻度画笔对象
    public float bigScaleLineWidth; // 大刻度宽度
    public float bigScaleLineHeight; // 大刻度长度
    public @ColorInt int bigScaleLineColor; // 大刻度颜色

    private Paint middleScaleLinePaint; // 中刻度画笔对象
    public float middleScaleLineWidth; // 中刻度宽度
    public float middleScaleLineHeight; // 中刻度长度
    public @ColorInt int middleScaleLineColor; // 中刻度颜色

    private Paint smallScaleLinePaint; // 小刻度画笔对象
    public float smallScaleLineWidth; // 大刻度宽度
    public float smallScaleLineHeight; // 大刻度长度
    public @ColorInt int smallScaleLineColor; // 小刻度颜色

    private Paint hourPointerPaint; // 时针画笔对象
    public float hourPointerWidth; // 时针宽度
    public @ColorInt int hourPointerColor; // 时针颜色

    private Paint minutePointerPaint; // 分针画笔对象
    public float minutePointerWidth; // 分针宽度
    public int minutePointerColor; // 分针颜色

    private Paint secondPointerPaint; // 秒针画笔对象
    public float secondPointerWidth; // 秒针宽度
    public int secondPointerColor; // 秒针颜色

    public ZClockView(Context context) {
        super(context);
    }

    public ZClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZClockView);
        innerCircleColor = a.getColor(R.styleable.ZClockView_innerCircleColor, Color.WHITE);
        outerCircleWidth = a.getDimension(R.styleable.ZClockView_outerCircleWidth, 10);
        outerCircleColor = a.getColor(R.styleable.ZClockView_outerCircleColor, Color.BLACK);
        centerRadius = a.getDimension(R.styleable.ZClockView_centerRadius, 10);
        centerCircleColor = a.getColor(R.styleable.ZClockView_centerCircleColor, Color.GREEN);
        textSize = a.getDimension(R.styleable.ZClockView_textSize, 20);
        textPadding = a.getDimension(R.styleable.ZClockView_textPadding, 10);
        textColor = a.getColor(R.styleable.ZClockView_textColor, Color.BLACK);
        bigScaleLineWidth = a.getDimension(R.styleable.ZClockView_bigScaleLineWidth, 3);
        bigScaleLineHeight= a.getDimension(R.styleable.ZClockView_bigScaleLineHeight, 25);
        bigScaleLineColor = a.getColor(R.styleable.ZClockView_bigScaleLineColor, Color.BLACK);
        middleScaleLineWidth = a.getDimension(R.styleable.ZClockView_middleScaleLineWidth, 2);
        middleScaleLineHeight= a.getDimension(R.styleable.ZClockView_middleScaleLineHeight, 20);
        middleScaleLineColor = a.getColor(R.styleable.ZClockView_middleScaleLineColor, Color.BLACK);
        smallScaleLineWidth = a.getDimension(R.styleable.ZClockView_smallScaleLineWidth, 1);
        smallScaleLineHeight= a.getDimension(R.styleable.ZClockView_smallScaleLineHeight, 15);
        smallScaleLineColor = a.getColor(R.styleable.ZClockView_smallScaleLineColor, Color.RED);
        hourPointerWidth = a.getDimension(R.styleable.ZClockView_hourPointerWidth, 6);
        hourPointerColor = a.getColor(R.styleable.ZClockView_hourPointerColor, Color.BLACK);
        minutePointerWidth = a.getDimension(R.styleable.ZClockView_minutePointerWidth, 3);
        minutePointerColor = a.getColor(R.styleable.ZClockView_minutePointerColor, Color.BLACK);
        secondPointerWidth = a.getDimension(R.styleable.ZClockView_secondPointerWidth, 2);
        secondPointerColor = a.getColor(R.styleable.ZClockView_secondPointerColor, Color.GREEN);
        a.recycle();
    }

    private void initPaint() {
        // 外圆
        outerCirclePaint = new Paint();
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setStrokeWidth(outerCircleWidth);
        outerCirclePaint.setColor(outerCircleColor);
        // 内圆
        innerCirclePaint = new Paint();
        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setColor(innerCircleColor);
        // 圆心
        centerCirclePaint = new Paint();
        centerCirclePaint.setAntiAlias(true);
        centerCirclePaint.setStyle(Paint.Style.FILL);
        centerCirclePaint.setColor(centerCircleColor);
        // 文字画笔对象
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        // 大刻度画笔对象
        bigScaleLinePaint = new Paint();
        bigScaleLinePaint.setAntiAlias(true);
        bigScaleLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bigScaleLinePaint.setColor(bigScaleLineColor);
        bigScaleLinePaint.setStrokeWidth(bigScaleLineWidth);
        // 中刻度画笔对象
        middleScaleLinePaint = new Paint();
        middleScaleLinePaint.setAntiAlias(true);
        middleScaleLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        middleScaleLinePaint.setColor(middleScaleLineColor);
        middleScaleLinePaint.setStrokeWidth(middleScaleLineWidth);
        // 小刻度画笔对象
        smallScaleLinePaint = new Paint();
        smallScaleLinePaint.setAntiAlias(true);
        smallScaleLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        smallScaleLinePaint.setColor(smallScaleLineColor);
        smallScaleLinePaint.setStrokeWidth(smallScaleLineWidth);
        // 时针画笔对象
        hourPointerPaint = new Paint();
        hourPointerPaint.setAntiAlias(true);
        hourPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPointerPaint.setStyle(Paint.Style.FILL);
        hourPointerPaint.setColor(hourPointerColor);
        hourPointerPaint.setStrokeWidth(hourPointerWidth);
        // 分针画笔对象
        minutePointerPaint = new Paint();
        minutePointerPaint.setAntiAlias(true);
        minutePointerPaint.setStrokeCap(Paint.Cap.ROUND);
        minutePointerPaint.setStyle(Paint.Style.FILL);
        minutePointerPaint.setColor(minutePointerColor);
        minutePointerPaint.setStrokeWidth(minutePointerWidth);
        // 秒针画笔对象
        secondPointerPaint = new Paint();
        secondPointerPaint.setAntiAlias(true);
        secondPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        secondPointerPaint.setStyle(Paint.Style.FILL);
        secondPointerPaint.setColor(secondPointerColor);
        secondPointerPaint.setStrokeWidth(secondPointerColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mMeasureWidth = measureSize(widthMeasureSpec);
        int mMeasureHeight = measureSize(heightMeasureSpec);
        outerRadius = mMeasureWidth/2f - outerCircleWidth/2f;
        if (mMeasureHeight < mMeasureWidth) {
            outerRadius = mMeasureHeight/2f - outerCircleWidth/2f;
        }
        innerRadius = outerRadius - outerCircleWidth/2f;
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    private int measureSize(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //设置一个默认值，就是这个View的默认宽度为400，这个看我们自定义View的要求
        int result = 400;
        if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = Math.max(specSize, 400);
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(400, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // 画外圆、内圆
        drawCircle(canvas, width, height);
        // 画刻度
        drawScaleLine(canvas, width, height);
        // 把原点移到圆心
        canvas.translate(width/2f, height/2f);
        // 画文字
        drawText(canvas);
        // 画时针、分针、秒针
        drawPointer(canvas);
    }

    private void drawCircle(Canvas canvas, int width, int height) {
        // 画外圆
        canvas.drawCircle(width / 2f, height / 2f, outerRadius, outerCirclePaint);
        // 画内圆
        canvas.drawCircle(width / 2f, height / 2f, innerRadius, innerCirclePaint);
    }

    private void drawScaleLine(Canvas canvas, int width, int height) {
        for (int i = 0; i< 60; i++) {
            if (i == 0 || i == 15 || i == 30 || i == 45) {
                canvas.drawLine(width/2f, height - outerCircleWidth - bigScaleLineHeight, width/2f, height - outerCircleWidth, bigScaleLinePaint);
            }
            else if (i == 5 || i == 10 || i == 20 || i == 25 || i == 35 || i == 40 || i == 50 || i == 55) {
                canvas.drawLine(width/2f, height - outerCircleWidth - middleScaleLineHeight, width/2f, height - outerCircleWidth, middleScaleLinePaint);
            }
            else {
                canvas.drawLine(width/2f, height - outerCircleWidth - smallScaleLineHeight, width/2f, height - outerCircleWidth, smallScaleLinePaint);
            }
            canvas.rotate(6, width / 2f, height / 2f);
        }
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i< 12; i++) {
            String text = String.valueOf(i == 0 ? 12 : i);

            float rR = innerRadius - bigScaleLineHeight - textPadding;
            int angle = i * 30;
            float x = 0;
            float y = 0;
            if(angle <= 90f){
                x = (float) Math.sin(angle*Math.PI/180) * rR;
                y = -(float) Math.cos(angle*Math.PI/180) * rR;
            }else if(angle <= 180f){
                x = (float) Math.cos((angle-90)*Math.PI/180) * rR;
                y = (float) Math.sin((angle-90)*Math.PI/180) * rR;
            }else if(angle <= 270f){
                x = -(float) Math.sin((angle-180)*Math.PI/180) * rR;
                y = (float) Math.cos((angle-180)*Math.PI/180) * rR;
            }else if(angle <= 360f){
                x = -(float) Math.cos((angle-270)*Math.PI/180) * rR;
                y = -(float) Math.sin((angle-270)*Math.PI/180) * rR;
            }
            Rect textBound = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), textBound);

            canvas.drawText(text, x - textBound.width()/2f, y + textBound.height()/2f, textPaint);
        }
    }

    private void drawPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        // 每隔一秒刷新一次
        handler.sendEmptyMessageDelayed(CLOCK_STEP, CLOCK_STEP - millisecond);

        // 画时针
        canvas.save();
        float hoursAngle = (hours * 60 + minutes) / 60f / 12f * 360;
        canvas.rotate(hoursAngle);
        canvas.drawLine(0, -innerRadius * 0.4f, 0, innerRadius * 0.15f, hourPointerPaint);
        canvas.restore();

        // 画分针
        canvas.save();
        float minutesAngle = (minutes * 60 + seconds) / 60f / 60f * 360;
        canvas.rotate(minutesAngle);
        canvas.drawLine(0, -innerRadius * 0.56f, 0, innerRadius * 0.15f, minutePointerPaint);
        canvas.restore();

        // 画秒针
        canvas.save();
        float secondsAngle = seconds / 60f * 360;
        canvas.rotate(secondsAngle);
        canvas.drawLine(0, -innerRadius * 0.72f, 0, innerRadius * 0.2f, secondPointerPaint);
        canvas.restore();

        // 画圆心
        canvas.save();
        canvas.drawCircle(0, 0, centerRadius, centerCirclePaint);
        canvas.restore();
    }

    private final Handler handler = new Handler(msg -> {
        if (msg.what == CLOCK_STEP) {
            invalidate();
            return true;
        }
        return false;
    });

}

package com.example.XiaMuYao.StuView.View;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * XiaMuYao
 * XiaMuYaodqx@gmail.com
 * 2017/10/13
 * =========================
 * 说明：
 */
public class HealthWheelView extends View {
    Paint mPaint;
    Paint changePaint; // 变色处理
    /**
     * 换算角度的进度
     */
    int progress_change = 0;
    /**
     * 用于变量的progress
     */
    int progress;
    String CONTENT_TEXT = "信用极好";
    String BETA = "BETA";
    String TEXT_TIME = "评估时间 : 8888-88-88";
    int COLOR_WHITE = 0xffFFFFFF;
    float PROGRESS_WIDTH = 5;
    private int circleRadius = 200;
    /**
     * 起始绘制的角度位置
     */
    private int START_CANVAS = 180;
    /**
     * 总数旋转的角度
     */
    private int ROTATE_NUM = 180;
    /**
     * 内容数字
     */
    String CONTENT_NUM = "0";
    /**
     * 当前的进度
     */
    int currentProgress = 0;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public HealthWheelView(Context context) {
        super(context, null);
    }

    public HealthWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public HealthWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }
    private void intiView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        changePaint = new Paint();
        changePaint.setAntiAlias(true);
        changePaint.setDither(true);// 设置抖动,颜色过渡更均匀
        changePaint.setStrokeCap(Paint.Cap.ROUND);
    }
}
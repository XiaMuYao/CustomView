package com.example.XiaMuYao.StuView.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.XiaMuYao.StuView.R;

import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 *
 */
public class PayView extends View {
    Paint mPaint;
    Paint changePaint; // 变色处理
    // 换算角度的进度
    int progress_change = 0;
    // 用于变量的progress
    int progress;
    String CONTENT_TEXT = "信用极好";
    String BETA = "BETA";
    String TEXT_TIME = "评估时间 : 8888-88-88";
    int COLOR_WHITE = 0xffFFFFFF;
    float PROGRESS_WIDTH = 5;
    /**
     * 半径
     */
    private int CircleRad = 400;
    /**
     * 起始绘制的角度位置
     */
    private int START_CANVAS = 180;
    /**
     * 总数旋转的角度
     */
    private int ROTATE_NUM = 180;
    String CONTENT_NUM = "0";
    int currentProgress = 0;
    private RectF oval1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setProgress(int progress) {
        this.progress = progress;
        changText(progress);
        invalidate();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private void changText(int progress2) {
        if (progress2 > 0 && progress2 <= 40) {
            CONTENT_TEXT = "信用极差";
        } else if (progress2 > 40 && progress2 <= 60) {
            CONTENT_TEXT = "信用一般";
        } else if (progress2 > 60 && progress2 <= 80) {
            CONTENT_TEXT = "信用良好";
        } else if (progress2 > 80 && progress2 <= 100) {
            CONTENT_TEXT = "信用极好";
        }
        // 显示评估的时间
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        String time = myFmt.format(new Date());
        TEXT_TIME = "评估时间 : " + time;
    }

    public PayView(Context context) {
        this(context, null);
    }

    public PayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas); // 绘制外围的暗色半圆
        drawPoint(canvas); // 绘制内圈的暗色点
//        drawTitleText(100, BETA, canvas); // 绘制beta
//        drawContent(canvas); // 绘制主题文字
        drawNum(canvas); // 绘制progress文字_________________需要重绘
//        drawTitleText(270, TEXT_TIME, canvas); // 绘制beta
//        drawBgChange(canvas); // 绘制外围变色区域__________需要重绘
//        drawPointLight(canvas); // 绘制亮点__________需要重绘
        drawPointTo(canvas); // 指向箭头的位置_________________需要重绘
        drawPoinCircle(canvas); //指向点
        if (currentProgress < progress) {
            currentProgress++;
            CONTENT_NUM = currentProgress + "";
            // 角度280.换算成100分来算
            progress_change = currentProgress * ROTATE_NUM / 100;
            // 回调接口,用于背景渐变
            listener.changeBg(currentProgress);
            invalidate();
        }
    }

    //绘制指向点
    private void drawPoinCircle(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        int piont_num = ROTATE_NUM / 4;
        int changeCircle = progress_change * piont_num / ROTATE_NUM;
        Log.d(TAG, "drawPoinCircle: changeCircle==" + changeCircle);
        // 图片和圆点偏差3度的角度,这里少转3度
        canvas.rotate(START_CANVAS - 3, startX, startY);// 以圆中心进行旋转
        for (int i = 0; i < piont_num; i++) {
            if (i == changeCircle) {
                Log.d(TAG, "drawPoinCircle: i==" + i);
//               Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.zhizhen);
//                canvas.drawBitmap(map, startX + CircleRad - 50, startY, mPaint);
                canvas.drawPoint(oval1.right, oval1.centerY(), mPaint);
            }
            canvas.rotate(degree, startX, startY);// 以圆中心进行旋转
        }
        canvas.restore();
    }

    // 外围渐变色
    private void drawBgChange(Canvas canvas) {
        changePaint.setStyle(Paint.Style.STROKE);
        changePaint.setStrokeWidth(PROGRESS_WIDTH);
//        changePaint.setAlpha(255);
//         梯度渐变色.类似雷达扫描
        Shader mShader = new SweepGradient(getWidth() / 2, getHeight() / 2, new int[]{0x00000000, 0xffffffff},
                new float[]{START_CANVAS, START_CANVAS + ROTATE_NUM});
        changePaint.setShader(mShader);
        int width = getWidth();
        int height = getHeight();
        int left = width / 2 - CircleRad;
        int top = height / 2 - CircleRad;
        int right = width / 2 + CircleRad;
        int bottom = height / 2 + CircleRad;
        RectF oval1 = new RectF(left, top, right, bottom);
        canvas.drawArc(oval1, START_CANVAS, progress_change, false, changePaint);// 小弧形
    }

    //     绘制秒速文字
    private void drawNum(Canvas canvas) {

        //进度文字绘制
        float stringWidth = mPaint.measureText(CONTENT_NUM + "%");
        float x = oval1.centerX() - (stringWidth) / 2;
        Log.d(TAG, "onDraw: 字体宽度" + stringWidth);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float y = (Math.abs(oval1.right - oval1.left) + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2);
        mPaint.setTextSize(200);
        canvas.drawText(CONTENT_NUM + "%", x, y, mPaint);

    }

    // 绘制信用很好
    private void drawContent(Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        final Rect bounds = new Rect();
        mPaint.setColor(COLOR_WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(35);
//        mPaint.setAlpha(180); // 透明度值在0~255之间
        mPaint.getTextBounds(CONTENT_TEXT, 0, CONTENT_TEXT.length(), bounds);
        canvas.drawText(CONTENT_TEXT, (width / 2) - (bounds.width() / 2), (height / 2) - CircleRad + 150, mPaint);
    }

    private void drawTitleText(int distance, String textD, Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        final Rect bounds = new Rect();
        mPaint.setColor(COLOR_WHITE);
//        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(25);
//        mPaint.setAlpha(130); // 透明度值在0~255之间
        mPaint.getTextBounds(textD, 0, textD.length(), bounds);
        canvas.drawText(textD, (width / 2) - (bounds.width() / 2), (height / 2) - CircleRad + distance, mPaint);
    }

    /**
     * 绘制指向点
     */
    @SuppressLint("NewApi")

    private void drawPointTo(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;

        //每隔4度绘制一次 需要绘制(旋转)多少次
        int piont_num = ROTATE_NUM / 4;
        // 每次改变的度数  =  我要变到多少 * 我要一共要转几次 / 整体旋转的度数
        int changeCircle = progress_change * piont_num / ROTATE_NUM;
        // 图片和圆点偏差3度的角度,这里少转3度
        canvas.rotate(START_CANVAS - 6, startX, startY);// 以圆中心进行旋转

        for (int i = 0; i < piont_num; i++) {
            if (i == changeCircle) {
                Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.zhizhen2);
                canvas.drawBitmap(map, startX + CircleRad - 110, startY, mPaint);
            }
            canvas.rotate(degree, startX, startY);// 以圆中心进行旋转
        }
        canvas.restore();
    }

    /**
     * 绘制指向点
     */
    @SuppressLint("NewApi")
    private void drawPointLight(Canvas canvas) {
        canvas.save();
        mPaint.setColor(COLOR_WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;
        int piont_num = ROTATE_NUM / degree;
        int changeCircle = progress_change * piont_num / ROTATE_NUM;
        canvas.rotate(START_CANVAS, startX, startY);// 以圆中心进行旋转
        for (int i = 0; i < piont_num; i++) {
            if (i < changeCircle + 1) {
                canvas.drawCircle(startX + CircleRad - 20, startY, 3, mPaint);
            }
            canvas.rotate(degree, startX, startY);// 以圆中心进行旋转
        }
        canvas.restore();
    }

    /**
     * 绘制一圈的点.每4度画一个点
     */
    int degree = 4;

    private void drawPoint(Canvas canvas) {
        canvas.save();
        mPaint.setColor(COLOR_WHITE);
//        mPaint.setAlpha(80);
        mPaint.setStyle(Paint.Style.FILL);
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;
        // 点的格式
        int piont_num = ROTATE_NUM / degree;
        canvas.rotate(START_CANVAS, startX, startY);// 以圆中心进行旋转
        for (int i = 0; i < piont_num; i++) {
            canvas.drawLine(oval1.left + 30, oval1.centerY() - 5, oval1.left + 30, oval1.centerY() - 10, mPaint);
            canvas.rotate(-degree, startX, startY);// 以圆中心进行旋转
        }

        canvas.restore();
    }

    /***
     * 绘制外围的线
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        mPaint.setColor(COLOR_WHITE);
//        mPaint.setAlpha(50);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PROGRESS_WIDTH);
        int width = getWidth();
        int height = getHeight();
        int left = width / 2 - CircleRad;
        int top = height / 2 - CircleRad;
        int right = width / 2 + CircleRad;
        int bottom = height / 2 + CircleRad;
        oval1 = new RectF(left, top, right, bottom);
        canvas.drawRect(oval1, mPaint);
        canvas.drawArc(oval1, START_CANVAS, ROTATE_NUM, false, mPaint);// 小弧形
    }

    ChangeBgListener listener;

    public void setOnChangeListener(ChangeBgListener listener) {
        this.listener = listener;
    }

    /**
     * @author XIamuyao
     * @see  "yinyongni"
     */
    public interface ChangeBgListener {
        /**
         * @author XIamuyao
         * @see  "yinyongni"
         */
        void changeBg(int progress);
    }

}

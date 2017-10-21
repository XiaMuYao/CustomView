package com.example.XiaMuYao.StuView.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * XiaMuYao
 * XiaMuYaodqx@gmail.com
 * 2017/10/13
 * =========================
 * 说明：
 */
public class ZhiFuBao extends View {

    private static final int ROTATE_NUM = 180;
    private Paint paint = new Paint();
    private Paint mTextPaint = new Paint();
    private Paint mCirclrpaint = new Paint();
    private int lastH;
    private int lastW;
    int mTextNum = 0;
    private int progress;
    private RectF mInrectF;
    private int circleRadius = 180;
    private RectF mOutrectF;
    private int left;
    private int right;

    public float getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public ZhiFuBao(Context context) {
        super(context, null);
    }

    public ZhiFuBao(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ZhiFuBao(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure: 宽度+" + width);
        Log.d(TAG, "onMeasure: 高度+" + height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lastW = w;
        lastH = h;
        Log.d(TAG, "onSizeChanged: 宽度" + lastW);
        Log.d(TAG, "onSizeChanged: 高度" + lastH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.RED); // 设置为红色
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(5);
        mTextPaint.setTextSize(50);
        //在屏幕里面 不要出去
        mOutrectF = new RectF(getLeft() + paint.getStrokeWidth(), getTop() + paint.getStrokeWidth(), getRight() - paint.getStrokeWidth(), getBottom());
        canvas.drawRect(mOutrectF, paint);

        //辅助线
        canvas.drawLine(0, mOutrectF.centerY(), mOutrectF.right, mOutrectF.centerY(), paint);
        canvas.drawLine(mOutrectF.centerX(), 0, mOutrectF.centerX(), mOutrectF.bottom, paint);

        // 宽度 / 0.1875
        left = (int) (getWidth() * 0.1875);
        right = (int) (left + getWidth() * 0.625);
        //绘制正方形
        mInrectF = new RectF(left, (float) (getHeight() * 0.3), right, (float) right);
        canvas.drawRect(mInrectF, paint);
        //绘制里面得圆弧
        canvas.drawArc(mInrectF, 180, 180, false, paint);

        //进度文字绘制
        float stringWidth = mTextPaint.measureText(progress + "%");
        float x = mInrectF.centerX() - (stringWidth) / 2;
        Log.d(TAG, "onDraw: 字体宽度" + stringWidth);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float y = (float) (mInrectF.bottom / 2.5 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2);
        paint.setTextSize(200);
        canvas.drawText(progress + "%", x, y, mTextPaint);

        //签单率较高文字绘制
        // TODO: 2017/10/14 这里字体没有计算
        stringWidth = mTextPaint.measureText("签单率较高");
        float xDText = mInrectF.centerX() - (stringWidth) / 2;
        canvas.drawText("签单率较高", xDText, (float) (y * 1.15), mTextPaint);

        //绘制小的线段
        drawPoint(canvas);

        drawCircle(canvas);


    }

    /**
     * 绘制小的圆点
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        //绘制圆点
        mCirclrpaint.setColor(Color.BLACK);
        mCirclrpaint.setStrokeWidth(15);
        mCirclrpaint.setStrokeCap(Paint.Cap.ROUND);


//        int need = (int) (46 / 1.8);
        canvas.drawPoint(mInrectF.left, mInrectF.centerY(), mCirclrpaint);

//        for (int i = 0; i < 21; i++) {
            canvas.rotate((float) 2, mInrectF.centerX(), mInrectF.centerY());// 以圆中心进行旋转
//        }
        canvas.drawPoint(mInrectF.left, mInrectF.centerY(), mCirclrpaint);
        canvas.restore();

    }



    /**
     * 绘制小的线段
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        canvas.save();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);

        for (int i = 0; i < 17; i++) {
            //线段开始的X = 矩形的X
            canvas.drawLine(mInrectF.left + 25, mOutrectF.bottom - 68, mInrectF.left + 25, mOutrectF.bottom - 98, mTextPaint);
            canvas.rotate((float) 11, mInrectF.centerX(), mInrectF.centerY());// 以圆中心进行旋转
        }
        canvas.restore();
    }
}
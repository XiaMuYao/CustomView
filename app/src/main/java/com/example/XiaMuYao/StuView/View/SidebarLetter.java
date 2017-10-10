package com.example.XiaMuYao.StuView.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.XiaMuYao.StuView.R;

/**
 * XiaMuYao
 * XiaMuYaodqx@gmail.com
 * 2017/10/9
 * =========================
 * 说明：26字母侧边栏
 * 字母默认大小12px,默认颜色蓝色，选中颜色红色
 */
public class SidebarLetter extends View {
    /**
     * 触碰前的画笔
     */
    private Paint mPaintTouchUn;
    /**
     * 触碰后的画笔
     */
    private Paint mPaintTouch;
    /**
     * 控件的高度
     */
    private int mSidebarLetterHight;
    /**
     * 控件的宽度
     */
    private int mSidebarLetterWight;
    /**
     * 字母宽度
     */
    private int mLettersWight = 0;
    /**
     * 字母X位置 = 控件宽度/2 - 字母宽度/2
     */
    int mLettersX = 0;
    /**
     * 基线高度
     */
    private float mBaseline;
    /**
     * 每个字母块的高度
     */
    private int mEveLettersHight;
    /**
     * 现在正在触摸的字母
     */
    private String mNowSelectLetter;
    /**
     * 现在触摸字母的索引下标
     */
    private int mNowSelectLetterIndex;
    /**
     * 当前选中字母的下标
     */
    private int index;
    private String[] Letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"
            , "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};


    public SidebarLetter(Context context) {
        this(context, null);
    }

    public SidebarLetter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SidebarLetter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaintAndType(context, attrs);
    }

    /**
     * 初始化画笔以及自定义属性
     *
     * @param mContext
     * @param attrs
     */
    private void initPaintAndType(Context mContext, AttributeSet attrs) {
        mPaintTouch = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTouchUn = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.SidebarLetter);
        //字体大小
        mPaintTouch.setTextSize(px2dip(mContext, array.getInteger(R.styleable.SidebarLetter_TextSize, px2dip(mContext, 12))));
        mPaintTouchUn.setTextSize(px2dip(mContext, array.getInteger(R.styleable.SidebarLetter_TextSize, px2dip(mContext, 12))));
        //字体颜色
        mPaintTouch.setColor(array.getColor(R.styleable.SidebarLetter_TextColor, Color.RED));
        mPaintTouchUn.setColor(array.getColor(R.styleable.SidebarLetter_UnTextColor, Color.BLUE));
        //回收该定义
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLettersWight = (int) mPaintTouchUn.measureText("A");
        //控件高度 = 嗯，他就是那么高= =
        mSidebarLetterHight = MeasureSpec.getSize(heightMeasureSpec);
        //控件宽度 = 左边边距 + 右边边距 + 字母宽度
        mSidebarLetterWight = (getPaddingLeft() + getPaddingRight() + mLettersWight);
        setMeasuredDimension(mSidebarLetterWight, mSidebarLetterHight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //每个字母块的高度 = 控件高度/字符数组长度
        mEveLettersHight = mSidebarLetterHight / Letters.length;
        //基线 = 每个字母块/2 + ascent - descent;
        mBaseline = mEveLettersHight / 2 + (Math.abs(mPaintTouch.ascent()) - mPaintTouch.descent()) / 2;

        for (int i = 0; i < Letters.length; i++) {
            mLettersX = (int) ((mSidebarLetterWight / 2) - ((mPaintTouch.measureText(Letters[i])) / 2));
            if (!Letters[i].equals(mNowSelectLetter)) {
                canvas.drawText(Letters[i], mLettersX, mEveLettersHight * i + mBaseline, mPaintTouchUn);
            } else {
                canvas.drawText(Letters[i], mLettersX, mEveLettersHight * i + mBaseline, mPaintTouch);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                index = (int) (event.getY() / mEveLettersHight);
                /**
                 * 如果越界则约束
                 */
                index = (index < 0) ? 0 : index;
                index = (index > Letters.length - 1) ? Letters.length - 1 : index;

                mNowSelectLetter = Letters[index];
                mNowSelectLetterIndex = index;

                if (mOnTouchMyListener != null) {
                    mOnTouchMyListener.SidebarLetterTouchListener(mNowSelectLetter, mNowSelectLetterIndex, true);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mOnTouchMyListener != null) {
                    mOnTouchMyListener.SidebarLetterTouchListener(mNowSelectLetter, mNowSelectLetterIndex, false);
                }
                /**
                 * 如果越界则约束
                 */
                index = (index < 0) ? 0 : index;
                index = (index > Letters.length - 1) ? Letters.length - 1 : index;
                mNowSelectLetter = null;
                invalidate();
                break;
        }
        return true;
    }

    private OnTouchMyListener mOnTouchMyListener;

    public void setOnTouchMyListener(OnTouchMyListener Listener) {
        this.mOnTouchMyListener = Listener;
    }

    /**
     * 回调监听
     */
    public interface OnTouchMyListener {
        /**
         * 侧边栏滑动监听
         *
         * @param nowLetter 选中字母
         * @param nowIndex  选中数字
         * @param isTouch   是否正在触碰
         */
        void SidebarLetterTouchListener(String nowLetter, int nowIndex, boolean isTouch);
    }

    /**
     * px -> dp
     *
     * @param mContext
     * @param dp
     * @return
     */
    public int px2dip(Context mContext, float dp) {

        float scale = mContext.getResources().getDisplayMetrics().density;

        return (int) (dp * scale + 0.5f);

    }
}

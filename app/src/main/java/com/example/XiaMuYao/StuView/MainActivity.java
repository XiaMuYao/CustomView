package com.example.XiaMuYao.StuView;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.XiaMuYao.StuView.View.PayView;


public class MainActivity extends Activity implements PayView.ChangeBgListener {

    PayView myview;
    Button btn_text;
    EditText et_num;
    private RelativeLayout rela_bgg;
    private int maxNum = 100;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian);
        rela_bgg = (RelativeLayout) findViewById(R.id.rela_bgg);
        myview = (PayView) findViewById(R.id.myview);
        myview.setOnChangeListener(this);
        myview.setProgress(80);
    }

    @Override
    public void changeBg(int progress) {
//        rela_bgg.setBackgroundColor(calculateColor(progress));
        System.out.println("==" + progress);
    }

    @SuppressLint("NewApi")
    private int calculateColor(int value) {
        ArgbEvaluator evealuator = new ArgbEvaluator();
        float fraction = 0;
        int color = 0;
        if (value <= maxNum / 2) {
            fraction = (float) value / (maxNum / 2);
            color = (Integer) evealuator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00); // 由红到橙
        } else {
            fraction = ((float) value - maxNum / 2) / (maxNum / 2);
            color = (Integer) evealuator.evaluate(fraction, 0xFFFF8C00, 0xFF00CED1); // 由橙到蓝
        }
        return color;
    }

}
package com.example.XiaMuYao.StuView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.XiaMuYao.StuView.View.SidebarLetter;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

public class MainActivity extends Activity implements SidebarLetter.OnTouchMyListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian);
        SidebarLetter sidebarLetter = findViewById(R.id.SidebarLetter);
        sidebarLetter.setOnTouchMyListener(this);
    }



    @Override
    public void SidebarLetterTouchListener(String nowLetter, int nowIndex, boolean isTouch) {
        Log.d(TAG, "nowLetter: " + nowLetter + "   nowIndex  +" + nowIndex + "    isTouch + " + isTouch);
    }


}
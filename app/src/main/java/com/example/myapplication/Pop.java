package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Pop extends Activity {

    private TextView mDisplayText;
    private FrameLayout frameLayout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        frameLayout = findViewById(R.id.popup_layout);
        mDisplayText = findViewById(R.id.popup_text);

        Bundle displayData = getIntent().getExtras();
        mDisplayText.setText(displayData.getString(TaskFragment.EXTRA_POPUP_TEXT));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

//        getWindow().setLayout((int) (width*0.5), (int) (height*0.25));
//        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mDisplayText.setLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        getWindow().setLayout(mDisplayText.getLayoutParams().width, mDisplayText.getLayoutParams().height);
//        getWindow().setLayout(mDisplayText.getWidth(), mDisplayText.getHeight());
        getWindow().setLayout(frameLayout.getLayoutParams().width, frameLayout.getLayoutParams().height);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Log.e("POPUP", mDisplayText.getWidth() + "  " + mDisplayText.getHeight());
    }
}

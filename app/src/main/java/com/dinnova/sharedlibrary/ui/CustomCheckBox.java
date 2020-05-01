package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.dinnova.sharedlibrary.R;


public class CustomCheckBox extends AppCompatCheckBox {

    public CustomAddition customAddition;

    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        customAddition = new CustomAddition(context, this, attrs, R.styleable.CustomCheckBox, R.styleable.CustomCheckBox_customText, R.styleable.CustomCheckBox_customFont);
        customAddition.recycleView();
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("NewApi")
    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled) {
            this.setAlpha(0f);
        } else {
            this.setAlpha(.5f);
        }
        super.setEnabled(enabled);
    }


}
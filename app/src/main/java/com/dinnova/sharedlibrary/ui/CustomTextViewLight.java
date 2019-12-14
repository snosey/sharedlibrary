package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextViewLight extends AppCompatTextView {
    public CustomTextViewLight(Context context) {
        super(context);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
    }

    public CustomTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
    }

    public CustomTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
    }
}
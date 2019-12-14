package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;


public class CustomCheckBox extends AppCompatCheckBox {
    private final ColorStateList color;
    Context context;

    public CustomCheckBox(Context context) {
        super(context);
        this.context = context;
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
        color = this.getTextColors();
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
        color = this.getTextColors();
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
        color = this.getTextColors();
    }

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
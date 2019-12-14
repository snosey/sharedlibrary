package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;


public class CustomButton extends AppCompatButton {
    public CustomButton(Context context) {
        super(context);
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
    }
/*
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            CustomButton.this.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
          //  CustomButton.this.setBackgroundResource(R.drawable.rounded_button_silver);
        }
    }*/
}
package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.dinnova.sharedlibrary.R;

public class CustomTextViewBold extends AppCompatTextView {
    public CustomTextViewBold(Context context) {
        super(context);
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
        setCustomText(context,attrs);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
        setCustomText(context,attrs);
    }

    private void setCustomText(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextViewBold, 0, 0);
        String text = "";
        try {
            text = typedArray.getString(R.styleable.CustomTextViewBold_customText);
        } finally {
            typedArray.recycle();
        }
        if (text != null) {
            setText(text);
        }
    }
}
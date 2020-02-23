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
        setCustomText(context, attrs);
    }

    public CustomTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
        setCustomText(context, attrs);
    }

    private void setCustomText(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextViewLight, 0, 0);
        String text = "";
        try {
            text = LanguageText.get(typedArray.getString(R.styleable.CustomTextViewLight_customText));
        } catch (Exception e) {

        } finally {
            typedArray.recycle();
        }
        if (text.isEmpty()) {
            return;
        }
        if (text != null) {
            setText(text);
        }
    }
}
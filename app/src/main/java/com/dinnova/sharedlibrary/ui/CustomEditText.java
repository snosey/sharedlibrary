package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.dinnova.sharedlibrary.R;


public class CustomEditText extends AppCompatEditText {

    public CustomAddition customAddition;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        customAddition = new CustomAddition(context, this, attrs, R.styleable.CustomEditText, R.styleable.CustomEditText_customText, R.styleable.CustomEditText_customFont);
        customAddition.setCustomHint(customAddition.getCustomStringById(R.styleable.CustomEditText_customHint));
    }


    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
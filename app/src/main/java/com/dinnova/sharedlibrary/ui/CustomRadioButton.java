package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.util.AttributeSet;

import com.dinnova.sharedlibrary.R;

public class CustomRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {


    public CustomAddition customAddition;

    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        customAddition = new CustomAddition(context, this, attrs, R.styleable.CustomRadioButton, R.styleable.CustomRadioButton_customText, R.styleable.CustomRadioButton_customFont);
        customAddition.recycleView();
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.dinnova.sharedlibrary.R;

public class CustomTextView extends AppCompatTextView {
    public CustomAddition customAddition;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        customAddition = new CustomAddition(context, this, attrs, R.styleable.CustomTextView, R.styleable.CustomTextView_customText, R.styleable.CustomTextView_customFont);
        customAddition.recycleView();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

}
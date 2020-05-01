package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.dinnova.sharedlibrary.R;


public class CustomButton extends AppCompatButton {
    public CustomAddition customAddition;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        customAddition = new CustomAddition(context,this, attrs, R.styleable.CustomButton,R.styleable.CustomButton_customText,R.styleable.CustomButton_customFont);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setCustomEnabled(boolean enabled, int textColor, int backgroundColor) {
        super.setEnabled(enabled);
        if (!enabled) {
            CustomButton.this.setTextColor(ContextCompat.getColor(getContext(), textColor));
            CustomButton.this.setBackgroundTintList(getContext().getResources().getColorStateList(backgroundColor));
            customAddition.recycleView();
        }
    }
}
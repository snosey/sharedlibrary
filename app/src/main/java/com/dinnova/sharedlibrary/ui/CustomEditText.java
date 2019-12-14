package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;


public class CustomEditText extends AppCompatEditText {


    private boolean isPassword = false;

    public CustomEditText(Context context) {
        super(context);
        Typeface face = CustomTypeFace.BoldFont();
        drawableClick();
        this.setTypeface(face);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = CustomTypeFace.BoldFont();
        drawableClick();
        this.setTypeface(face);
    }

 /*   @Override
    public void setError(CharSequence error) {
        try {
            Drawable customErrorDrawable = getResources().getDrawable(android.R.drawable.stat_notify_error);
            customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
            CustomEditText.this.setError(error + "", customErrorDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = CustomTypeFace.BoldFont();
        drawableClick();
        this.setTypeface(face);
    }

    public void setPassword(Boolean isPassword) {
        this.isPassword = isPassword;
    }

    void drawableClick() {
        this.setOnTouchListener(new OnTouchListener() {
            private int TEXT_PASSWORD_TYPE = 129;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isPassword)
                    return false;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (CustomEditText.this.getInputType() != TEXT_PASSWORD_TYPE)
                    return false;

                Log.e("passwordCheck", CustomEditText.this.getInputType() + "");

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (event.getRawX() >= (CustomEditText.this.getRight() - CustomEditText.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        {
                            if (CustomEditText.this.getTransformationMethod() == null)
                                CustomEditText.this.setTransformationMethod(new PasswordTransformationMethod());
                            else
                                CustomEditText.this.setTransformationMethod(null);
                        }

                        return true;
                    }
                }
                return false;
            }
        });
    }


}
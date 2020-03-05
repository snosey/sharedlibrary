package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.dinnova.sharedlibrary.R;


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
        setCustomText(context,attrs);
        color = this.getTextColors();
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Typeface face = CustomTypeFace.BoldFont();
        this.setTypeface(face);
        setCustomText(context,attrs);
        color = this.getTextColors();
    }

    private void setCustomText(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCheckBox, 0, 0);
        String text = "";
        try {
            text = LanguageText.get(typedArray.getString(R.styleable.CustomCheckBox_customText));
        } catch (Exception e) {

        } finally {
            typedArray.recycle();
        }
        if (text != null) {
            setText(text);
        }
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

    public void setHtmlText(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            this.setText(Html.fromHtml(text.replaceAll("\n", "<br>"), Html.FROM_HTML_MODE_LEGACY));
        } else {
            this.setText(Html.fromHtml(text.replaceAll("\n", "<br>")));
        }
    }

}
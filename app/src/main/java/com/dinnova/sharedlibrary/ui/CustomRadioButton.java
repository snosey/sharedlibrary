package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import com.dinnova.sharedlibrary.R;

public class CustomRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {


    public CustomRadioButton(Context context) {
        super(context);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
        setCustomText(context,attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = CustomTypeFace.LightFont();
        this.setTypeface(face);
        setCustomText(context,attrs);
    }

    private void setCustomText(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomRadioButton, 0, 0);
        String text = "";
        try {
            text = LanguageText.get(typedArray.getString(R.styleable.CustomRadioButton_customText));
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

    public void setHtmlText(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            this.setText(Html.fromHtml(text.replaceAll("\n", "<br>"), Html.FROM_HTML_MODE_LEGACY));
        } else {
            this.setText(Html.fromHtml(text.replaceAll("\n", "<br>")));
        }
    }

}
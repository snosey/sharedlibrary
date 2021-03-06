package com.dinnova.sharedlibrary.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dinnova.sharedlibrary.R;

public class CustomAddition {
    public TypedArray typedArray;
    private Context context;
    private int attrCustomText;
    private int attrCustomFont;
    private TextView textView;

    CustomAddition(Context context, TextView textView, AttributeSet attrs, int[] styleId, int attrCustomText, int attrCustomFont) {
        this.context = context;
        this.attrCustomFont = attrCustomFont;
        this.attrCustomText = attrCustomText;
        this.textView = textView;
        typedArray = context.getTheme().obtainStyledAttributes(attrs, styleId, 0, 0);
        xmlFontType();
        xmlCustomText(getCustomStringById(attrCustomText));
    }

    public String getCustomStringById(int id) {
        return typedArray.getString(id);
    }

    @SuppressLint("NewApi")
    private void xmlCustomText(String key) {
        String text = "";
        try {
            text = LanguageText.get(typedArray.getString(attrCustomText));
        } catch (Exception ignored) {

        }
        if (text.isEmpty()) {
            return;
        }

        textView.setText(text);
    }

    @SuppressLint("NewApi")
    void setCustomHint(String key) {
        String text = "";
        try {
            text = LanguageText.get(key);
        } catch (Exception ignored) {

        }
        if (text.isEmpty()) {
            return;
        }
        textView.setHint(text);
    }

    @SuppressLint("NewApi")
    void setCustomColor(int textColor, int hintColor) {
        try {
            textView.setTextColor(typedArray.getColor(textColor, context.getResources().getColor(R.color.black_8am2)));
            textView.setHintTextColor(typedArray.getColor(hintColor, context.getResources().getColor(R.color.black_8am2)));
        } catch (Exception ignored) {

        }
    }

    public void setHtmlText(String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text.replaceAll("\n", "<br>"), Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(text.replaceAll("\n", "<br>")));
        }
    }

    public void changeToOffer() {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @SuppressLint("NewApi")
    private void xmlFontType() {
        try {
            String font = getCustomStringById(attrCustomFont);
            if (font != null && !font.isEmpty()) {
                Typeface face = Typeface.createFromAsset(context.getAssets(), font);
                textView.setTypeface(face);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void recycleView() {
        try {
            if (typedArray != null)
                typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;

import com.dinnova.sharedlibrary.R;


public class CustomSearchView extends SearchView {

    public CustomAddition customAddition;

    public CustomSearchView(Context context) {
        super(context);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        EditText searchEditText = findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        customAddition = new CustomAddition(context, searchEditText, attrs, R.styleable.CustomSearchView, R.styleable.CustomSearchView_customText, R.styleable.CustomSearchView_customFont);
        customAddition.setCustomHint(customAddition.getCustomStringById(R.styleable.CustomSearchView_customHint));
        customAddition.setCustomColor(R.styleable.CustomSearchView_textColor, R.styleable.CustomSearchView_hintColor);
        ImageView searchIcon = findViewById(R.id.search_button);
        searchIcon.setColorFilter(customAddition.typedArray.getColor(R.styleable.CustomSearchView_searchColor,getContext().getResources().getColor(R.color.black_8am2)), android.graphics.PorterDuff.Mode.MULTIPLY);

    }


    public CustomSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
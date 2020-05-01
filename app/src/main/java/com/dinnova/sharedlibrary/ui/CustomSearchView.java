package com.dinnova.sharedlibrary.ui;

/**
 * Created by Snosey on 5/23/2018.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import com.dinnova.sharedlibrary.R;


public class CustomSearchView extends SearchView {

    public CustomAddition customAddition;

    public CustomSearchView(Context context) {
        super(context);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImageView searchIcon = findViewById(R.id.search_button);
        ImageView closeIcon = findViewById(R.id.search_close_btn);
        EditText searchEditText = findViewById(R.id.search_src_text);
        customAddition = new CustomAddition(context, searchEditText, attrs, R.styleable.CustomSearchView, R.styleable.CustomSearchView_customText, R.styleable.CustomSearchView_customFont);
        customAddition.setCustomColor(R.styleable.CustomSearchView_textColor, R.styleable.CustomSearchView_hintColor);
        searchIcon.setColorFilter(customAddition.typedArray.getColor(R.styleable.CustomSearchView_searchColor,getContext().getResources().getColor(R.color.black_8am2)), android.graphics.PorterDuff.Mode.MULTIPLY);
        closeIcon.setColorFilter(customAddition.typedArray.getColor(R.styleable.CustomSearchView_searchColor,getContext().getResources().getColor(R.color.black_8am2)), android.graphics.PorterDuff.Mode.MULTIPLY);

    }


    public CustomSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
package com.dinnova.sharedlibrary.ui;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class TextCustomsView {
    public View changeToOffer(TextView view)
    {
        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        return view;
    }
}

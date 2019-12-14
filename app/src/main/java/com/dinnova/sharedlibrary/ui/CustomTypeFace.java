package com.dinnova.sharedlibrary.ui;

import android.content.Context;
import android.graphics.Typeface;

public class CustomTypeFace {
    public static String light;
    public static Context context;
    public static String bold;

    public static Typeface BoldFont() {
        return Typeface.createFromAsset(context.getAssets(), bold);
    }

    public static Typeface LightFont() {
        return Typeface.createFromAsset(context.getAssets(), light);
    }

}

package com.dinnova.sharedlibrary.ui;

import android.content.Context;
import android.graphics.Typeface;

public class CustomTypeFace {
    private static String light;
    private static Context context;
    private static String bold;

    public static void init(String bold,String light,Context context) {
        CustomTypeFace.bold=bold;
        CustomTypeFace.light=light;
        CustomTypeFace.context=context;
    }

    public static Typeface BoldFont() {
        return Typeface.createFromAsset(context.getAssets(), bold);
    }

    public static Typeface LightFont() {
        return Typeface.createFromAsset(context.getAssets(), light);
    }

}

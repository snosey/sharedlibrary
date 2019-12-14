package com.dinnova.sharedlibrary.utils;

import android.content.res.Configuration;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import java.util.Locale;

public class CheckLanguage {
    public CheckLanguage(FragmentActivity fragmentActivity, boolean isArabic) {
        Locale locale;

        if (isArabic)
            locale = new Locale("ar");
        else
            locale = new Locale("en");

        //  //Locale.setDefault
        Configuration config = new Configuration();
        config.locale = locale;
        fragmentActivity.getResources().updateConfiguration(config, fragmentActivity.getResources().getDisplayMetrics());
        fragmentActivity.onConfigurationChanged(config);
        if (isArabic)
            fragmentActivity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            fragmentActivity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

    }
}

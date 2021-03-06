package com.dinnova.sharedlibrary.utils.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.dinnova.sharedlibrary.MainActivity;
import com.dinnova.sharedlibrary.ui.LanguageText;

import java.util.Locale;


public class CustomActivity extends FragmentActivity {
    public Resources resources;

    public String getCustomText(int id){
        return LanguageText.get(getString(id));
    }
    @Override
    public Resources getResources() {
        if (resources == null) {
            try {
                return super.getResources();
            } catch (Exception e) {
                return null;
            }
        }
        return resources;
    }

    @SuppressLint("NewApi")
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @SuppressLint("NewApi")
    public void changeLang(String languageCode,boolean recreate) {
        LocaleHelper.persist(this, languageCode);
        if(recreate)
        recreate();
    }


    @SuppressLint("NewApi")
    public void changeLang(String languageCode, Intent intent) {
        LocaleHelper.persist(this, languageCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private static class LocaleHelper {

        private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        public static Context onAttach(Context context) {
            String lang = getPersistedData(context, Locale.getDefault().getLanguage());
            return setLocale(context, lang);
        }

        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        public static Context setLocale(Context context, String language) {
            persist(context, language);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResources(context, language);
            }
            return updateResourcesLegacy(context, language);
        }

        private static String getPersistedData(Context context, String defaultLanguage) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
        }

        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        static void persist(Context context, String language) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SELECTED_LANGUAGE, language);
            editor.apply();
        }

        @TargetApi(Build.VERSION_CODES.N)
        private static Context updateResources(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);

            return context.createConfigurationContext(configuration);
        }

        @SuppressWarnings("deprecation")
        private static Context updateResourcesLegacy(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources resources = context.getResources();

            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale);
            }

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

            return context;
        }
    }
}

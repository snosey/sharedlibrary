package com.dinnova.sharedlibrary.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;


import com.dinnova.sharedlibrary.image.FullScreen;
import com.dinnova.sharedlibrary.webservice.WebService;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class QuickActions {
    private Context context;


    public static List<Integer> getSpinnerList(int min, int max) {
        List<Integer> integers = new ArrayList<>();
        for (int i = min; i <= max; i++)
            integers.add(i);
        return integers;
    }

    public void setDefaultRTL(){
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("en"));
        context.getResources().updateConfiguration(configuration,context.getResources().getDisplayMetrics());
    }

    public QuickActions(Context activity) {
        this.context = activity;
    }

    public static int minuteBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.SECOND_IN_MILLIS);
    }

    public static int hoursBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.MINUTE_IN_MILLIS);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / DateUtils.DAY_IN_MILLIS);
    }

    public static void closeKeyboard(FragmentActivity fragmentActivity) {
        try {
            View keyboard = fragmentActivity.getCurrentFocus();
            if (keyboard != null) {
                InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMapThumb(String lat, String lng) {
        String imgMap = "https://maps.googleapis.com/maps/api/staticmap?center="
                + lat + "," + lng + "&zoom=14&size=600x300&Key=" + SHARED_KEY_REQUESTS.MapApiKey;
        Log.e("MapImageUrl", imgMap);
        return imgMap;
    }

    public CharSequence minAgo(String created_at) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", new Locale("en"));
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+02"));
        try {
            long now = System.currentTimeMillis();

            return
                    DateUtils.getRelativeTimeSpanString(sdf.parse(created_at).getTime(), now, DateUtils.MINUTE_IN_MILLIS);
            //               getRelativeTimeSpanString(context, sdf.parse(created_at));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void makeCall(String phone) {
        if (!phone.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            context.startActivity(intent);
        }
    }

    public void mailUs(String EXTRA_SUBJECT, String EXTRA_TEXT, String MAIL_TO, String TITLE) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", MAIL_TO, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, EXTRA_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, EXTRA_TEXT);
        context.startActivity(Intent.createChooser(emailIntent, TITLE));
    }

    public void contactUS(String phoneNumber, FragmentActivity fragmentActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.CALL_PHONE};
            if (!SHARED_KEY_REQUESTS.hasPermissions(context, PERMISSIONS)) {
                fragmentActivity.requestPermissions(PERMISSIONS, SHARED_KEY_REQUESTS.PHONE_PERMISSION);
            } else {
                makeCall(phoneNumber);
            }
        } else {
            makeCall(phoneNumber);
        }
    }

    public void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void goLocation(double lat, double lng) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",
                lat
                , lng, "");

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);

    }

    public void openUrlBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public void openUrlWebView(String webviewUrl) {
        Intent intent = new Intent(context, WebView.class);
        intent.putExtra(WebService.Data, webviewUrl);
        context.startActivity(intent);
    }


    public void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }

    }

    public void whatsapp(String phone) {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode("  ", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imageFullScreen(String imgUrl) {
        new FullScreen((FragmentActivity) context, imgUrl);
    }
}

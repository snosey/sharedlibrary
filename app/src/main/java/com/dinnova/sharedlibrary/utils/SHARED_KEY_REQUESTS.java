package com.dinnova.sharedlibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class SHARED_KEY_REQUESTS {
    public static final int PHONE_PERMISSION = 213;
    public static final int STORAGE_PERMISSION = 121;
    public static final int PICK_IMAGE_REQUEST = 122;
    public static final int OPEN_GPS_REQUEST = 133;
    public static final int LOCATION_PERMISSION = 99;
    public static final int PLACE_AUTOCOMPLETE_REQUEST = 1;
    public static final int GOOGLE_SIGN_REQUEST = 1;
    public static final int ACCESS_LOCATION_PERMISSION = 31123;
    public static String MapApiKey;


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}

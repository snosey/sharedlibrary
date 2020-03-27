package com.dinnova.sharedlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.dinnova.sharedlibrary.image.FullScreen;
import com.dinnova.sharedlibrary.webservice.WebService;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class QuickActions {

    public static List<Integer> getSpinnerList(int min, int max) {
        List<Integer> integers = new ArrayList<>();
        for (int i = min; i <= max; i++)
            integers.add(i);
        return integers;
    }

    public void setDefaultRTL(FragmentActivity context) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("en"));
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
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

    private void makeCall(String phone, FragmentActivity context) {
        if (!phone.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            context.startActivity(intent);
        }
    }

    public void mailUs(String EXTRA_SUBJECT, String EXTRA_TEXT, String MAIL_TO, String TITLE, FragmentActivity context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", MAIL_TO, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, EXTRA_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, EXTRA_TEXT);
        context.startActivity(Intent.createChooser(emailIntent, TITLE));
    }

    public void contactUS(String phoneNumber, FragmentActivity context) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.CALL_PHONE};
            if (!SHARED_KEY_REQUESTS.hasPermissions(context, PERMISSIONS)) {
                context.requestPermissions(PERMISSIONS, SHARED_KEY_REQUESTS.PHONE_PERMISSION);
            } else {
                makeCall(phoneNumber, context);
            }
        } else {
            makeCall(phoneNumber, context);
        }
    }

    public void shareApp(FragmentActivity context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public void goLocation(double lat, double lng, FragmentActivity context) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",
                lat
                , lng, "");

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);

    }

    public void openUrlBrowser(String url, FragmentActivity context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public void openUrlWebView(String webviewUrl, FragmentActivity context) {
        Intent intent = new Intent(context, WebView.class);
        intent.putExtra(WebService.Data, webviewUrl);
        context.startActivity(intent);
    }


    public void rateApp(FragmentActivity context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }

    }

    public void whatsapp(String phone, FragmentActivity context) {
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

    public void imageFullScreen(String imgUrl, FragmentActivity context) {
        new FullScreen(context, imgUrl);
    }

    public static class CalendarHelper {

        //Remember to initialize this activityObj first, by calling initActivityObj(this) from
//your activity
        private static final String TAG = "CalendarHelper";
        public static final int CALENDARHELPER_PERMISSION_REQUEST_CODE = 99;


        public static void MakeNewCalendarEntry(Activity caller, String title, String description, String location, long startTime, long endTime, boolean allDay, boolean hasAlarm, int calendarId, int selectedReminderValue) {

            ContentResolver cr = caller.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startTime);
            values.put(CalendarContract.Events.DTEND, endTime);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
            values.put(CalendarContract.Events.STATUS, CalendarContract.Events.STATUS_CONFIRMED);


            if (allDay) {
                values.put(CalendarContract.Events.ALL_DAY, true);
            }

            if (hasAlarm) {
                values.put(CalendarContract.Events.HAS_ALARM, true);
            }

            //Get current timezone
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            Log.i(TAG, "Timezone retrieved=>" + TimeZone.getDefault().getID());
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            Log.i(TAG, "Uri returned=>" + uri.toString());
            // get the event ID that is the last element in the Uri
            long eventID = Long.parseLong(uri.getLastPathSegment());

            if (hasAlarm) {
                ContentValues reminders = new ContentValues();
                reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
                reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                reminders.put(CalendarContract.Reminders.MINUTES, selectedReminderValue);

                Uri uri2 = cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
            }


        }

        public static void requestCalendarReadWritePermission(Activity caller) {
            List<String> permissionList = new ArrayList<String>();

            if (ContextCompat.checkSelfPermission(caller, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_CALENDAR);

            }

            if (ContextCompat.checkSelfPermission(caller, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_CALENDAR);

            }

            if (permissionList.size() > 0) {
                String[] permissionArray = new String[permissionList.size()];

                for (int i = 0; i < permissionList.size(); i++) {
                    permissionArray[i] = permissionList.get(i);
                }

                ActivityCompat.requestPermissions(caller,
                        permissionArray,
                        CALENDARHELPER_PERMISSION_REQUEST_CODE);
            }

        }

        public static Hashtable listCalendarId(Context c) {

            if (haveCalendarReadWritePermissions((Activity) c)) {

                String projection[] = {"_id", "calendar_displayName"};
                Uri calendars;
                calendars = Uri.parse("content://com.android.calendar/calendars");

                ContentResolver contentResolver = c.getContentResolver();
                Cursor managedCursor = contentResolver.query(calendars, projection, null, null, null);

                if (managedCursor.moveToFirst()) {
                    String calName;
                    String calID;
                    int cont = 0;
                    int nameCol = managedCursor.getColumnIndex(projection[1]);
                    int idCol = managedCursor.getColumnIndex(projection[0]);
                    Hashtable<String, String> calendarIdTable = new Hashtable<>();

                    do {
                        calName = managedCursor.getString(nameCol);
                        calID = managedCursor.getString(idCol);
                        Log.v(TAG, "CalendarName:" + calName + " ,id:" + calID);
                        calendarIdTable.put(calName, calID);
                        cont++;
                    } while (managedCursor.moveToNext());
                    managedCursor.close();

                    return calendarIdTable;
                }

            }

            return null;

        }

        public static boolean haveCalendarReadWritePermissions(Activity caller) {
            int permissionCheck = ContextCompat.checkSelfPermission(caller,
                    Manifest.permission.READ_CALENDAR);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                permissionCheck = ContextCompat.checkSelfPermission(caller,
                        Manifest.permission.WRITE_CALENDAR);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }

            return false;
        }

    }
}

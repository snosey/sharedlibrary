package com.dinnova.sharedlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

public class GetRealFilePath {
    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String realPath;
        // SDK < API11
        if (Build.VERSION.SDK_INT < 11)
            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, contentURI);

            // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath = RealPathUtil.getRealPathFromURI_API11to18(context, contentURI);

            // SDK > 19 (Android 4.4)
        else
            realPath = RealPathUtil.getRealPathFromURI_API19(context, contentURI);

        return realPath;
    }

    static class RealPathUtil {

        @SuppressLint("NewApi")
        public static String getRealPathFromURI_API19(Context context, Uri uri) {
            String filePath = "";
            String wholeID = "";
            try {
                wholeID = DocumentsContract.getDocumentId(uri);

            } catch (Exception e) {
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    cursor = context.getContentResolver().query(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }

            String[] splits = wholeID.split(":");
            String id = wholeID.split(":")[splits.length - 1];


            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        }


        @SuppressLint("NewApi")
        private static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
            String[] proj = {MediaStore.Images.Media.DATA};
            String result = null;

            CursorLoader cursorLoader = new CursorLoader(
                    context,
                    contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
            return result;
        }

        private static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index
                    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    }
}

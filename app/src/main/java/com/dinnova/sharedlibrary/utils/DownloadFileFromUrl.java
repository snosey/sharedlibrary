package com.dinnova.sharedlibrary.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromUrl extends AsyncTask<String, String, String> {
    public static final int progress_bar_type = 0;
    private final String applicationName;
    FragmentActivity activity;
    private ProgressDialog pDialog;
    private String path;

    public DownloadFileFromUrl(FragmentActivity activity) {
        this.activity = activity;
        final PackageManager pm = activity.getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(activity.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        path = Environment
                .getExternalStorageDirectory().toString() + "/" + applicationName + "/My Contacts.csv";
    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        createDialog(progress_bar_type);
    }

    private void createDialog(int progress_bar_type) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            // Output stream
            File file = new File(path);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            OutputStream output = new FileOutputStream(file);

            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(String file_url) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Exporting is done").setMessage("You can view your file at " + "'/" + applicationName + "/My Contacts" +
                ". also you can share it now!").setPositiveButton("Share", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                File shareFile = new File(path);
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + shareFile.getAbsolutePath()));
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                activity.startActivity(Intent.createChooser(share, "Powered by Gather."));
            }
        }).setNegativeButton("Open", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/" + applicationName + "/");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(selectedUri, "resource/folder");

                if (intent.resolveActivityInfo(activity.getPackageManager(), 0) != null) {
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "Your file manager doesn't support 3rd party opening directory! Open directory manual from your file manager", Toast.LENGTH_LONG).show();
                }
            }
        }).show();
        pDialog.dismiss();
    }

}
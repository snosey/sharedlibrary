package com.dinnova.sharedlibrary.utils.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.dinnova.sharedlibrary.R;
import com.dinnova.sharedlibrary.utils.StaticTextAlerts;
import com.dinnova.sharedlibrary.webservice.WebService;

/**
 * Created by Snosey on 4/15/2018.
 */

public class CustomWebView extends Activity {
    android.webkit.WebView webview;


    private ValueCallback<Uri[]> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // manejo de seleccion de archivo
        if (requestCode == FILECHOOSER_RESULTCODE) {

            if (null == mUploadMessage || intent == null || resultCode != RESULT_OK) {
                return;
            }

            Uri[] result = null;
            String dataString = intent.getDataString();

            if (dataString != null) {
                result = new Uri[]{Uri.parse(dataString)};
            }

            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.webview);
        webview = findViewById(R.id.webView);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUpWebView();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setUpWebView() {
        String fullUrl = getIntent().getStringExtra(WebService.Data);
        webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);

        webview.setWebChromeClient(new WebChromeClient() {


            @Override
            public boolean onShowFileChooser(android.webkit.WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                // asegurar que no existan callbacks
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }

                mUploadMessage = filePathCallback;

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*"); // set MIME type to filter

                CustomWebView.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), CustomWebView.FILECHOOSER_RESULTCODE);

                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {

            }

            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setProgress(newProgress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if (newProgress == 100)
                    setTitle(StaticTextAlerts.APP_NAME);

            }


        });
        String appCachePath = getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);

        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setAppCacheEnabled(true);

        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);

        WebSettings websettings = webview.getSettings();
        websettings.setJavaScriptEnabled(true);

        webview.setHorizontalScrollBarEnabled(false);
        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }

        });
        android.webkit.WebView.setWebContentsDebuggingEnabled(true);
        webview.loadUrl(fullUrl);


    }

    public void back(View view) {
        onBackPressed();
    }
}


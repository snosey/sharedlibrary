package com.dinnova.sharedlibrary.webservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.dinnova.sharedlibrary.utils.StaticTextAlerts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;

/**
 * Created by Snosey on 4/3/2018.
 */

public class WebService extends Request<String> {
    public static final String Data = "Data";
    public static final String Status = "Status";
    public static final String Id = "Id";
    public static final String Message = "Message";
    @SuppressLint("StaticFieldLeak")
    private static FragmentActivity activity;
    public static String BASE_URL;
    private ProgressDialog progress = null;
    private JSONObject params;
    private Response.Listener<String> mListener;
    private boolean showLoading;
    private boolean messageAlert;
    private HashMap<String, File> fileList;
    private boolean isMultiPart;
    HttpEntity httpEntity;


    public static String getResponseData(String json) {
        try {
            return new JSONObject(json).getJSONObject(WebService.Data).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public WebService(final FragmentActivity activity, HashMap<String, File> fileList, boolean isFileArray, int method, final String url, UrlData urlData, final boolean showLoading,
                      boolean messageAlert, JSONObject params, Response.Listener<String> listener) {
        super(method, BASE_URL + url + urlData.get(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                String body;

                //get status code here
                //get response body and parse with appropriate encoding
                try {
                    Log.e("Error Url", url);
                    if (error.networkResponse.data != null) {
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                            Log.e("Error:", body);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        WebService.activity = activity;

        this.fileList = fileList;
        this.isMultiPart = isFileArray;
        mListener = listener;
        this.messageAlert = messageAlert;
        this.showLoading = showLoading;
        this.params = params;
        Log.e("API/URL", BASE_URL + url + urlData.get());
        this.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        progress = new ProgressDialog(activity);
        progress.setMessage(StaticTextAlerts.loadingAlert);
        progress.setCancelable(true); // disable dismiss by tapping outside of the dialog

        if (showLoading && !activity.isDestroyed()) {
            Log.e("ProgressShow", "true");
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.show();
                    }
                });
            } catch (Exception e) {

            }
        }

        MultipartEntityBuilder fileParams = MultipartEntityBuilder.create();
        if (this.isMultiPart && fileList != null && fileList.size() != 0) {
            Iterator it = fileList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                fileParams.addPart(pair.getKey() + "", new FileBody(Objects.requireNonNull(fileList.get(pair.getKey().toString()))));
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        Iterator<String> keys = params.keys();

        while (keys.hasNext() && isMultiPart) {
            String key = keys.next();
            try {
                Log.e("MULTIPART/", key + ":- " + params.get(key).toString());
                if (params.get(key) instanceof JSONObject || params.get(key) instanceof JSONArray) {
                    fileParams.addPart(key, new StringBody(params.get(key).toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                } else {
                    fileParams.addTextBody(key, params.get(key).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        httpEntity = fileParams.build();

        Volley.newRequestQueue(activity).add(this);
    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Server error, please check internet connection and try again", Toast.LENGTH_LONG).show();
            }
        });
        progress.dismiss();
        return volleyError;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually

        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }


        String modifiedResponse = Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response)).result;
        Log.e("modifiedResponse", modifiedResponse);
        try {
            progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }


    @Override
    protected void deliverResponse(String response) {
        progress.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (messageAlert && jsonObject.getJSONObject(WebService.Status).getInt(WebService.Id) == 0) {
                if (showLoading)
                    Toast.makeText(activity, jsonObject.getJSONObject(WebService.Status).getString(WebService.Message), Toast.LENGTH_LONG).show();
                else {
                    mListener.onResponse(response);
                }
            } else {
                mListener.onResponse(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBodyContentType() {
        if (this.isMultiPart || fileList != null) {
            Log.e("requestType", "multi/part");
            return httpEntity.getContentType().getValue();
        } else {
            Log.e("requestType", "application/json");
            return "application/json";
        }
    }

    @Override
    public byte[] getBody() {
        if (this.isMultiPart || fileList != null) {
            Log.e("params:", params.toString());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                httpEntity.writeTo(bos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bos.toByteArray();
        } else {
            Log.e("params:", params.toString());
            int length = params.toString().getBytes().length;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
            bos.write(params.toString().getBytes(), 0, length);
            return bos.toByteArray();
        }
    }

    public static String paramJson(String paramIn) {
        paramIn = paramIn.replaceAll("=", "\":\"");
        paramIn = paramIn.replaceAll("&", "\",\"");
        return "{\"" + paramIn + "\"}";
    }
}

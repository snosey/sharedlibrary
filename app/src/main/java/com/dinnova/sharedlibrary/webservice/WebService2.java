package com.dinnova.sharedlibrary.webservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.dinnova.sharedlibrary.utils.StaticTextAlerts;
import com.google.gson.annotations.Expose;

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

public class WebService2 extends Request<String> {
    @SuppressLint("StaticFieldLeak")
    private static Context activity;
    public static String BASE_URL;
    private ProgressDialog progress = null;
    private Object paramsObject;
    private Response.Listener<CustomResponse> mListener;
    private boolean showLoading;
    private boolean messageAlert;
    private HashMap<String, File> fileList;
    private boolean isMultiPart;
    private HttpEntity httpEntity;


    @SuppressLint("NewApi")
    public WebService2(final Context activity, HashMap<String, File> fileList, boolean isMultiPart, int method, final String url, UrlData urlData, final boolean showLoading,
                       boolean messageAlert, Object paramsObject, Response.Listener<CustomResponse> listener) {
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
        WebService2.activity = activity;
        this.fileList = fileList;
        this.isMultiPart = isMultiPart;
        mListener = listener;
        this.messageAlert = messageAlert;
        this.showLoading = showLoading;
        this.paramsObject = paramsObject;
        Log.e("API/URL", BASE_URL + url + urlData.get());
        this.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        if (activity != null && this.showLoading) {
            progress = new ProgressDialog(activity);
            progress.setMessage(StaticTextAlerts.loadingAlert);
            progress.setCancelable(true); // disable dismiss by tapping outside of the dialog
            Log.e("ProgressShow", "true");
            try {
                progress.show();
            } catch (Exception ignored) {

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


        if (this.isMultiPart) {
            JSONObject params = (JSONObject) this.paramsObject;
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    Log.e("MULTIPART/", key + ":- " + params.get(key).toString());
                    if (params.get(key) instanceof JSONObject || params.get(key) instanceof JSONArray) {
                        fileParams.addPart(key, new StringBody(params.get(key).toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
                    } else {
                        fileParams.addTextBody(key, params.get(key).toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        httpEntity = fileParams.build();

        Volley.newRequestQueue(activity).add(this);
    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (progress != null) progress.dismiss();
        return volleyError;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == 200) {
            String parsed;
            try {
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            }
            CustomResponse customResponse = new CustomResponse();
            customResponse.json = parsed;
            try {
                if (response.headers.containsKey("X-Pagination"))
                    customResponse.pagination = (Pagination) new Pagination().jsonToModel(response.headers.get("X-Pagination"));

                if (response.headers.containsKey("X-Status"))
                    customResponse.status = (Status) new Status().jsonToModel(response.headers.get("X-Status"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                return Response.success(customResponse.modelToJson().toString(), HttpHeaderParser.parseCacheHeaders(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "Server error, please try again later...", Toast.LENGTH_LONG).show();
            return Response.error(new ParseError(response));
        }
        return Response.error(new ParseError(response));

    }


    public class CustomResponse extends JsonConverter2 {
        @Expose
        public Pagination pagination;
        @Expose
        public Status status;
        @Expose
        public String json;
    }

    public class Pagination extends JsonConverter2 {
        @Expose
        public int CurrentPage;
        @Expose
        public int TotalPages;
        @Expose
        public int PageSize;
        @Expose
        public int TotalCount;
        @Expose
        public boolean HasPrevious;
        @Expose
        public boolean HasNext;
        @Expose
        public String PrevoisPageLink;
        @Expose
        public String NextPageLink;
    }

    public class Status extends JsonConverter2 {
        @Expose
        public boolean Success;
        @Expose
        public String ErrorMessage;
        @Expose
        public String ExceptionMessage;
    }

    @Override
    protected void deliverResponse(String response) {
        if (progress != null) progress.dismiss();
        try {
            CustomResponse customResponse = (CustomResponse) new CustomResponse().jsonToModel(response);
            if (customResponse.status.Success)
                mListener.onResponse(customResponse);
            else if (messageAlert) {
                Toast.makeText(activity, customResponse.status.ErrorMessage, Toast.LENGTH_LONG).show();
                Log.e("ErrorMsg", customResponse.status.ExceptionMessage);
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
            Log.e("paramsObject:", paramsObject.toString());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                httpEntity.writeTo(bos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bos.toByteArray();
        } else {
            Log.e("paramsObject:", paramsObject.toString());
            int length = paramsObject.toString().getBytes().length;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
            bos.write(paramsObject.toString().getBytes(), 0, length);
            return bos.toByteArray();
        }
    }

    public static String paramJson(String paramIn) {
        paramIn = paramIn.replaceAll("=", "\":\"");
        paramIn = paramIn.replaceAll("&", "\",\"");
        return "{\"" + paramIn + "\"}";
    }

    public static void showErrorMsg(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }
}

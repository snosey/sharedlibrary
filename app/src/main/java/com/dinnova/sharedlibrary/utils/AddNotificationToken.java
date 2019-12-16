package com.dinnova.sharedlibrary.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.dinnova.sharedlibrary.webservice.UrlData;
import com.dinnova.sharedlibrary.webservice.WebService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNotificationToken {
    public String APPLICATION_VERSION;
    public String URL;
    public int external_user_id;
    private FragmentActivity fragmentActivity;

    public AddNotificationToken(final FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public void sendToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.e("FCM_TOKEN", token);
                        UrlData urlData = new UrlData();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            JSONObject objectNotification = new JSONObject();
                            objectNotification.put("identifier", token);
                            objectNotification.put("device_os", Build.VERSION.RELEASE);
                            objectNotification.put("game_version", APPLICATION_VERSION);
                            objectNotification.put("device_type", 1);
                            objectNotification.put("device_model", Build.MODEL);
                            objectNotification.put("external_user_id", external_user_id);
                            jsonObject.put("NotificationDeviceModel", objectNotification);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new WebService(fragmentActivity, null, false, Request.Method.POST, URL,
                                urlData, false, false, jsonObject, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        });

                    }
                });
    }
}

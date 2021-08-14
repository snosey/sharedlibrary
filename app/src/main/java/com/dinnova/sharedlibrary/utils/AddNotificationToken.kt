package com.dinnova.sharedlibrary.utils

import android.os.Build
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.dinnova.sharedlibrary.webservice.UrlData
import com.dinnova.sharedlibrary.webservice.WebService
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONException
import org.json.JSONObject

class AddNotificationToken(
    fragmentActivity: FragmentActivity,
    APPLICATION_VERSION: String,
    external_user_id: Int
) {
    private val APPLICATION_VERSION: String
    private val external_user_id: Int
    private val fragmentActivity: FragmentActivity
    fun sendToken(urlData: UrlData?, URL: String?) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                val token = it.result.toString()
                Log.e("FCM_TOKEN", token)
                val objectNotification = JSONObject()
                try {
                    objectNotification.put("identifier", token)
                    objectNotification.put("device_os", Build.VERSION.RELEASE)
                    objectNotification.put("game_version", APPLICATION_VERSION)
                    objectNotification.put("device_type", 1)
                    objectNotification.put("device_model", Build.MODEL)
                    objectNotification.put("external_user_id", external_user_id)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                WebService(fragmentActivity,
                    null,
                    false,
                    Request.Method.POST,
                    URL,
                    urlData,
                    false,
                    false,
                    objectNotification,
                    true,
                    Response.Listener<String?> { })
            }
        }
    }

    init {
        this.fragmentActivity = fragmentActivity
        this.APPLICATION_VERSION = APPLICATION_VERSION
        this.external_user_id = external_user_id
    }
}
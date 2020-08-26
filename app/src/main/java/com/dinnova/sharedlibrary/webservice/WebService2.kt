package com.dinnova.sharedlibrary.webservice

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.entity.ContentType
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder
import cz.msebera.android.httpclient.entity.mime.content.FileBody
import cz.msebera.android.httpclient.entity.mime.content.StringBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException

/**
 * Created by Snosey on 4/3/2018.
 */

class WebService2(private val activity: Context,
                  fileList: HashMap<String, File> = HashMap(),
                  private val isMultiPart: Boolean = false,
                  method: Int,
                  url: String, urlData: UrlData = UrlData(),
                  private val indicator: Indicator,
                  private val messageAlert: Boolean = false,
                  private val paramsObject: Any = JSONObject(),
                  private val mListener: Response.Listener<WebServiceHelper.CustomResponse>) :
        Request<String>(method, WebServiceSingleton.getBaseURL() + url + urlData.get(), Response.ErrorListener { error ->
            try {
                if (indicator.isVisible())
                    indicator.hideIndicator()
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        Log.e("Error:", String(error.networkResponse.data))
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                } else {
                    mListener.onResponse(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })


//class start
{
    private lateinit var httpEntity: HttpEntity


    init {
        Log.e("API/URL", WebServiceSingleton.getBaseURL() + url + urlData.get())
        this.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        val fileParams = MultipartEntityBuilder.create()
        if (isMultiPart) {
            //insert files in multipart
            for ((key, value) in fileList) fileParams.addPart(key, FileBody(value))

            //insert objects
            val params = this.paramsObject as JSONObject
            val keys = params.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                try {
                    Log.e("MULTIPART/", key + ":- " + params.get(key).toString())
                    if (params.get(key) is JSONObject || params.get(key) is JSONArray) {
                        fileParams.addPart(key, StringBody(params.get(key).toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")))
                    } else {
                        fileParams.addTextBody(key, params.get(key).toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8"))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            httpEntity = fileParams.build()
        }

        if (indicator.isVisible())
            indicator.showIndicator()

        Volley.newRequestQueue(activity).add(this)
    }


    override fun parseNetworkError(volleyError: VolleyError): VolleyError {
        return volleyError
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
        if (indicator.isVisible())
            indicator.hideIndicator()

        if (response.statusCode == 200) {
            val parsed = String(response.data)
            val customResponse = WebServiceHelper().CustomResponse()
            customResponse.json = parsed
            try {
                if (response.headers.containsKey("X-Pagination"))
                    customResponse.pagination = WebServiceHelper().Pagination().jsonToModel(response.headers["X-Pagination"]) as WebServiceHelper.Pagination?

                if (response.headers.containsKey("X-Status"))
                    customResponse.status = WebServiceHelper().Status().jsonToModel(response.headers["X-Status"]) as WebServiceHelper.Status?

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            try {
                return Response.success(customResponse.modelToJson().toString(), HttpHeaderParser.parseCacheHeaders(response))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        } else {
            Toast.makeText(activity, "Server error, please try again later...", Toast.LENGTH_LONG).show()
            return Response.error(ParseError(response))
        }
        return Response.error(ParseError(response))

    }

    override fun deliverResponse(response: String) {
        try {
            val customResponse = WebServiceHelper().CustomResponse().jsonToModel(response) as WebServiceHelper.CustomResponse
            if (customResponse.status!!.Success)
                mListener.onResponse(customResponse)
            else if (messageAlert) {
                Toast.makeText(activity, customResponse.status!!.ErrorMessage, Toast.LENGTH_LONG).show()
                Log.e("ErrorMsg", customResponse.status!!.ExceptionMessage)
            }
            else{
                mListener.onResponse(customResponse)
                Log.e("ErrorMsg", customResponse.status!!.ExceptionMessage)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun getBodyContentType(): String {
        return if (isMultiPart) {
            Log.e("requestType", "multi/part")
            httpEntity.contentType.value
        } else {
            Log.e("requestType", "application/json")
            "application/json"
        }
    }

    override fun getBody(): ByteArray {
        return if (isMultiPart) {
            Log.e("paramsObject:", paramsObject.toString())
            val bos = ByteArrayOutputStream()
            try {
                httpEntity.writeTo(bos)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            bos.toByteArray()
        } else {
            Log.e("paramsObject:", paramsObject.toString())
            val length = paramsObject.toString().toByteArray().size
            val bos = ByteArrayOutputStream(length)
            bos.write(paramsObject.toString().toByteArray(), 0, length)
            bos.toByteArray()
        }
    }


}

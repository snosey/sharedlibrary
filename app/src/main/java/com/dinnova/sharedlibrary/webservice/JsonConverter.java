package com.dinnova.sharedlibrary.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class JsonConverter implements Serializable {

    public JSONObject modelToJson() throws JSONException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return new JSONObject(gson.toJson(this));
    }

    public Object jsonToModel(String jsonObject) throws JSONException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(new JSONObject(jsonObject).getJSONObject(WebService.Data).toString(), super.getClass());
    }

    public Object jsonToListModel(String jsonObject) throws JSONException {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Object[] array = (Object[]) java.lang.reflect.Array.newInstance(super.getClass(), 0);
        return Arrays.asList(gson.fromJson(new JSONObject(jsonObject).getJSONArray(WebService.Data).toString(), array.getClass()));
    }
}

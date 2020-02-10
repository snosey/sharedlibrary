package com.dinnova.sharedlibrary.webservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonConverter implements Serializable {
    public JSONObject modelToJson() throws JSONException {
        return new JSONObject(new Gson().toJson(this));
    }

    public Object jsonToModel(String jsonObject) throws JSONException {
        return new Gson().fromJson(new JSONObject(jsonObject).getJSONObject(WebService.Data).toString(), super.getClass());
    }

    public Object[] jsonToListModel(String jsonObject) throws JSONException {
        Object [] array = (Object[])java.lang.reflect.Array.newInstance(super.getClass(), 0);
        return new Gson().fromJson(new JSONObject(jsonObject).getJSONArray(WebService.Data).toString(),array.getClass());
    }
}

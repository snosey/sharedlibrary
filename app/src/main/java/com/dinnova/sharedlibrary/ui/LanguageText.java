package com.dinnova.sharedlibrary.ui;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.google.gson.annotations.Expose;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageText {
    private static Map<String, String> languageMap = new HashMap<>();


    public static void set(String response) {
        try {
            languageMap.clear();
            for (LanguageModel languageModel : (List<LanguageModel>) new LanguageModel().jsonToListModel(response)) {
                languageMap.put(languageModel.Key, languageModel.Value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (languageMap.containsKey(key))
            return languageMap.get(key);
        else
            return "";
    }

    private static class LanguageModel extends JsonConverter {
        @Expose
        public String Key;
        @Expose
        public String Value;
    }

}

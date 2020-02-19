package com.dinnova.sharedlibrary.ui;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.google.gson.annotations.Expose;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageText {
    private static Map<String, String> languageMap;

    public static void set(String response) {
        languageMap = new HashMap<>();
        try {
            for (LanguageModel languageModel : (List<LanguageModel>) new LanguageModel().jsonToListModel(response)) {
                languageMap.put(languageModel.key, languageModel.value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return languageMap.get(key);
    }

    public static class LanguageModel extends JsonConverter {
        @Expose
        public String key;
        @Expose
        public String value;
    }

}

package com.dinnova.sharedlibrary.ui;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageText {
    private static Map<String, String> languageMap;

    public static void set(List<LanguageModel> languageModels) {
        languageMap = new HashMap<>();
        for (LanguageModel languageModel : languageModels) {
            languageMap.put(languageModel.key, languageModel.value);
        }
    }

    public static String get(String key) {
        return languageMap.get(key);
    }

    public class LanguageModel extends JsonConverter {
        @Expose
        public String key;
        @Expose
        public String value;
    }

}

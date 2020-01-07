package com.dinnova.sharedlibrary.utils;

import com.dinnova.sharedlibrary.ui.CustomEditText;
import com.dinnova.sharedlibrary.webservice.WebService;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public static boolean isValidModel(String response) {
        boolean isValid = false;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (jsonObject.getJSONObject("Status").getInt(WebService.Id) == 1)
                isValid = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static String getMsg(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            return jsonObject.getJSONObject("Status").getString(WebService.Message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkEmail(CustomEditText editText) {
        String email = editText.getText().toString();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        Boolean b = matcher.matches();
        if (!b)
            editText.setError(StaticTextAlerts.emailAlert);
        return b;
    }


    public static boolean checkPhone(CustomEditText editText, String code) {
        CountryCodePicker globalPhoneNumber = new CountryCodePicker(editText.getContext());
        globalPhoneNumber.setFullNumber(code + editText.getText().toString());

        Boolean b = globalPhoneNumber.isValidFullNumber();
        if (!b)
            editText.setError(StaticTextAlerts.phoneAlert);
        return b;
    }

    public static boolean checkPassword(CustomEditText editText) {
        String password = editText.getText().toString();
   /*  Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,16}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);*/


        Boolean b = password.length() >= 6 && password.length() <= 16;
        if (!b)
            editText.setError(StaticTextAlerts.passwordAlert);
        return b;
        //return matcher.matches();
    }

    public static boolean checkLength(CustomEditText editText) {
        String name = editText.getText().toString();


        Boolean b = name.trim().length() != 0;
        if (!b)
            editText.setError(StaticTextAlerts.lengthAlert);
        return b;
    }
}

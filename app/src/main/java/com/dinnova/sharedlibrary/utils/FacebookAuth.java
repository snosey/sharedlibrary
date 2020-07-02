package com.dinnova.sharedlibrary.utils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
/*

import com.android.volley.Response;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class FacebookAuth {

    public class FacebookModel implements Serializable {
        public String id;

        public String email;

        public String first_name;

        public String last_name;

        public String getLogo() {
            return "https://graph.facebook.com/" + id + "/picture?type=large";
        }

        public JSONObject modelToJson() throws JSONException {
            return new JSONObject(new Gson().toJson(this));
        }

        public Object jsonToModel(String jsonObject) throws JSONException {
            return new Gson().fromJson(jsonObject, super.getClass());
        }

    }

    private static CallbackManager callbackManager;

    public void setFacebookListener(final FragmentActivity activity, final Response.Listener<FacebookModel> facebookListener) {
        callbackManager = CallbackManager.Factory.create();
        LoginManager login = LoginManager.getInstance();
        login.logInWithReadPermissions(activity, (Arrays.asList("public_profile", "email")));
        login.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        {
                            GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            try {
                                                facebookListener.onResponse((FacebookModel) new FacebookModel().jsonToModel(object.toString()));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,email,first_name,last_name,picture");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(activity, "You canceled facebookLogin with facebook!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                    }
                });
 //       LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends"));
    }

    public void activityResult(int requestCode, int resultCode, Intent data) {
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/

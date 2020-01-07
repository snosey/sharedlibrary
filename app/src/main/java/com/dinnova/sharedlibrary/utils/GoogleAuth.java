package com.dinnova.sharedlibrary.utils;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Response;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

public class GoogleAuth {
    private final GoogleSignInClient googleApiClient;
    private FragmentActivity activity;

    public GoogleAuth(FragmentActivity activity) {
        this.activity = activity;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = GoogleSignIn.getClient(activity, gso);
    }

    public void onClick(Fragment fragment) {
        Intent signInIntent = googleApiClient.getSignInIntent();
        fragment.startActivityForResult(signInIntent, SHARED_KEY_REQUESTS.GOOGLE_SIGN_REQUEST);
    }

    public void getAccount(int requestCode, int resultCode, Intent data, Response.Listener<GoogleSignInAccount> listener) {
        if (requestCode == SHARED_KEY_REQUESTS.GOOGLE_SIGN_REQUEST) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            listener.onResponse(task.getResult());
        }
    }
}


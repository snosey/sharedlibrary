package com.dinnova.sharedlibrary.utils.views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dinnova.sharedlibrary.utils.views.CustomActivity;

public class CustomFragment extends Fragment {
    public CustomActivity fragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CustomActivity){
            fragmentActivity =(CustomActivity) context;
        }
    }

}

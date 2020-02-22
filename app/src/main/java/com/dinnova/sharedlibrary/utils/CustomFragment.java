package com.dinnova.sharedlibrary.utils;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class CustomFragment extends Fragment {


    public CustomActivity fragmentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CustomActivity){
            fragmentActivity =(CustomActivity) context;
        }
    }

}

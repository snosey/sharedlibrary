package com.dinnova.sharedlibrary.image;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.dinnova.kidzgram.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


/**
 * Created by Snosey on 12/17/2017.
 */

public class FullScreen extends Dialog {
    public FullScreen(@NonNull final FragmentActivity activity, String url) {
        super(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.show();
        this.setContentView(R.layout.image_full_screen);
        PhotoView imageView = this.findViewById(R.id.image);
        ImageView back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreen.this.dismiss();
            }
        });
        Picasso.get().load(url).into(imageView);
    }
}

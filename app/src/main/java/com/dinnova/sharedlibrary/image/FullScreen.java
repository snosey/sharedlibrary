package com.dinnova.sharedlibrary.image;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.dinnova.sharedlibrary.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

/**
 * Created by Snosey on 12/17/2017.
 */

public class FullScreen extends Dialog {
    public FullScreen(@NonNull final Context activity, String url) {
        super(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        show();
        setContentView(R.layout.image_full_screen);
        PhotoView imageView = findViewById(R.id.image);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreen.this.dismiss();
            }
        });
        Picasso.get().load(url).into(imageView);
    }
}

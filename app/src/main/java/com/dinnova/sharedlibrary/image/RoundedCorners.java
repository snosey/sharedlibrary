package com.dinnova.sharedlibrary.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

public class RoundedCorners implements com.squareup.picasso.Transformation {
    private final int radius = 70;
    private final int margin = 10;


    @Override
    public Bitmap transform(Bitmap bitmap) {
        final Bitmap source = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 1.8), (int) (bitmap.getHeight() * 1.8), true);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);

        try {
            if (bitmap != output) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
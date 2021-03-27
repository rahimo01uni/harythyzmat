package com.eccomerce.com.main;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class CustomWatermark extends BitmapTransformation {

    public CustomWatermark(Context context) {
        super(context);
    }

    public CustomWatermark(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}

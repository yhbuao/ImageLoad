package com.yhbuao.image.picasso;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.yhbuao.image.ImageFactory;
import com.yhbuao.image.R;

public final class PicassoFactory implements ImageFactory<PicassoStrategy> {

    @Override
    public PicassoStrategy createImageStrategy() {
        return new PicassoStrategy();
    }

    @Override
    public Drawable createPlaceholder(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.image_loading);
    }

    @Override
    public Drawable createError(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.image_load_err);
    }

    @Override
    public void clearMemoryCache(Context context) {
        // 清除内存缓存（必须在主线程）
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 清除本地缓存（必须在子线程）
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }
}
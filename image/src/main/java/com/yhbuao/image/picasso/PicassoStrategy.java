package com.yhbuao.image.picasso;

import android.annotation.SuppressLint;
import android.content.Context;

import com.squareup.picasso.Picasso;
import com.yhbuao.image.ImageLoader;
import com.yhbuao.image.ImageStrategy;

final class PicassoStrategy implements ImageStrategy {

    @SuppressLint("CheckResult")
    @Override
    public void load(ImageLoader loader) {
        Picasso picasso = getRequestManager((Context) loader.getContext());
        picasso.load(loader.getUrl()).into(loader.getView());
    }

    /**
     * 获取一个 Glide 的请求对象
     */
    private Picasso getRequestManager(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        } else {
            return Picasso.with(context);
        }
    }
}
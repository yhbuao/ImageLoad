package com.yhbuao.image.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yhbuao.image.ImageLoader;
import com.yhbuao.image.ImageStrategy;

final class GlideStrategy implements ImageStrategy {

    @SuppressLint("CheckResult")
    @Override
    public void load(ImageLoader loader) {
        RequestManager manager = getRequestManager(loader.getContext());

        if (loader.isGif()) {
            manager.asGif();
        }

        final RequestBuilder<Drawable> builder;
        if (loader.getUrl() != null && !"".equals(loader.getUrl())) {
            builder = manager.load(loader.getUrl().trim());
        } else if (loader.getResourceId() != 0) {
            builder = manager.load(loader.getResourceId());
        } else {
            builder = manager.load(loader.getError());
        }

        if (loader.getPlaceholder() != null) {
            final RequestOptions options = new RequestOptions().centerCrop().errorOf(loader.getError()).placeholder(loader.getPlaceholder());
            if (loader.getCircle() != 0) {
                if (loader.getCircle() == Integer.MAX_VALUE) {
                    // 裁剪成圆形
                    options.circleCrop();
                } else {
                    // 圆角裁剪
                    options.transform(new RoundedCorners(loader.getCircle()));
                }
            }

            builder.apply(options);
        }

        if (loader.getWidth() != 0 && loader.getHeight() != 0) {
            builder.override(loader.getWidth(), loader.getHeight());
        }

        builder.into(loader.getView());
    }

    /**
     * 获取一个 Glide 的请求对象
     */
    private RequestManager getRequestManager(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        } else if (object instanceof Context) {
            if (object instanceof FragmentActivity) {
                return GlideApp.with((FragmentActivity) object);
            } else if (object instanceof Activity) {
                return GlideApp.with((Activity) object);
            } else {
                return GlideApp.with((Context) object);
            }
        } else if (object instanceof Fragment) {
            return GlideApp.with((Fragment) object);
        } else if (object instanceof androidx.fragment.app.Fragment) {
            return GlideApp.with((androidx.fragment.app.Fragment) object);
        }
        // 如果不是上面这几种类型就直接抛出异常
        throw new IllegalArgumentException("This object is illegal");
    }
}
package com.yhbuao.image.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.Locale;

/**
 * Glide 图片加载监听
 *
 * @author yuhaibo
 * @date 2017/6/11
 * @time 上午2:42
 */

public class GlideLoggingListener<R> implements RequestListener<R> {

    private static final String TAG = "GLIDE";
    /**
     * 图片加载的标识
     */
    private final String name;
    /**
     * 自定义加载监听
     */
    private final RequestListener<R> delegate;

    public GlideLoggingListener() {
        this("");
    }

    public GlideLoggingListener(@NonNull String name) {
        this(name, null);
    }

    public GlideLoggingListener(RequestListener<R> delegate) {
        this("", delegate);
    }

    public GlideLoggingListener(@NonNull String name, RequestListener<R> delegate) {
        this.name = name;
        this.delegate = delegate == null ? NoOpRequestListener.<R>get() : delegate;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target,
                                boolean isFirstResource) {

//        Logger.t(TAG).e(String.format(Locale.ROOT,
//                "%s.onLoadFailed(%s, %s, %s, %s)\n%s",
//                name, e, model, strip(target), isFirst(isFirstResource), Log.getStackTraceString(e)));
        return delegate.onLoadFailed(e, model, target, isFirstResource);
    }

    @Override
    public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource,
                                   boolean isFirstResource) {
        String resourceString = strip(getResourceDescription(resource));
        String targetString = strip(getTargetDescription(target));
//        Logger.t(TAG).d(String.format(Locale.ROOT,
//                "%s.onResourceReady(%s, %s, %s, %s, %s)",
//                name, resourceString, model, targetString, dataSource, isFirst(isFirstResource)));
        return delegate.onResourceReady(resource, model, target, dataSource, isFirstResource);
    }

    private String isFirst(boolean isFirstResource) {
        return isFirstResource ? "first" : "not first";
    }

    private String getTargetDescription(Target<R> target) {
        String result;
        if (target instanceof ViewTarget) {
            View v = ((ViewTarget) target).getView();
            LayoutParams p = v.getLayoutParams();
            result = String.format(Locale.ROOT,
                    "%s(params=%dx%d->size=%dx%d)", target, p.width, p.height, v.getWidth(), v.getHeight());
        } else {
            result = String.valueOf(target);
        }
        return result;
    }

    private String getResourceDescription(R resource) {
        String result;
        if (resource instanceof Bitmap) {
            Bitmap bm = (Bitmap) resource;
            result = String.format(Locale.ROOT,
                    "%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
        } else if (resource instanceof BitmapDrawable) {
            Bitmap bm = ((BitmapDrawable) resource).getBitmap();
            result = String.format(Locale.ROOT,
                    "%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
        } else if (resource instanceof Drawable) {
            Drawable d = (Drawable) resource;
            result = String.format(Locale.ROOT,
                    "%s(%dx%d)", resource, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        } else {
            result = String.valueOf(resource);
        }
        return result;
    }

    private static String strip(Object text) {
        return String.valueOf(text).replaceAll("(com|android|net|org)(\\.[a-z]+)+\\.", "");
    }
}

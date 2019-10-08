package com.yhbuao.image;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.yhbuao.image.glide.GlideFactory;

import java.io.File;

public final class ImageLoader {

    // 图片缓存最大容量，300M
    public static final int GLIDE_CATCH_SIZE = 300 * 1000 * 1000;

    // 图片缓存子目录
    public static final String GLIDE_CARCH_DIR = "image_catch";

    /**
     * 图片生产工厂
     */
    private static ImageFactory sImageFactory;
    /**
     * 图片加载策略
     */
    private static ImageStrategy sImageStrategy;

    /**
     * 加载中占位图
     */
    private static Drawable sPlaceholder;
    /**
     * 加载出错占位图
     */
    private static Drawable sError;

    public static void init(Application application) {
        // 使用 Glide 进行初始化图片加载器
        init(application, new GlideFactory());
    }

    /**
     * 使用指定的图片加载器进行初始化
     *
     * @param application 上下文对象
     * @param factory     图片加载器生成对象
     */
    public static void init(@NonNull Application application, @NonNull ImageFactory factory) {
        sImageFactory = factory;
        sImageStrategy = factory.createImageStrategy();
        sPlaceholder = factory.createPlaceholder(application);
        sError = factory.createError(application);
    }

    /**
     * 清除图片缓存
     */
    public static void clear(Context context) {
        clearMemoryCache(context);
        clearDiskCache(context);
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache(Context context) {
        sImageFactory.clearMemoryCache(context);
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(final Context context) {
        sImageFactory.clearDiskCache(context);
    }

    public final Object context;
    public int circle;
    public String url;
    @DrawableRes
    public int resourceId;
    public boolean isGif;

    public Drawable placeholder = sPlaceholder;
    public Drawable error = sError;

    public int width;
    public int height;

    public ImageView view;

    public static Drawable getsPlaceholder() {
        return sPlaceholder;
    }

    public static Drawable getsError() {
        return sError;
    }

    public Object getContext() {
        return context;
    }

    public int getCircle() {
        return circle;
    }

    public String getUrl() {
        return url;
    }

    public int getResourceId() {
        return resourceId;
    }

    public boolean isGif() {
        return isGif;
    }

    public Drawable getPlaceholder() {
        return placeholder;
    }

    public Drawable getError() {
        return error;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImageView getView() {
        return view;
    }

    public ImageLoader(Object context) {
        this.context = context;
    }

    public static ImageLoader with(Context context) {
        return new ImageLoader(context);
    }

    public static ImageLoader with(Fragment fragment) {
        return new ImageLoader(fragment);
    }

    public static ImageLoader with(androidx.fragment.app.Fragment fragment) {
        return new ImageLoader(fragment);
    }

    public ImageLoader gif() {
        this.isGif = true;
        return this;
    }

    public ImageLoader circle() {
        return circle(Integer.MAX_VALUE);
    }

    public ImageLoader circle(int circle) {
        this.circle = circle;
        return this;
    }

    public ImageLoader load(String url) {
        this.url = url;
        return this;
    }

    public ImageLoader load(File file) {
        this.url = Uri.fromFile(file).toString();
        return this;
    }

    public ImageLoader load(@DrawableRes int id) {
        this.resourceId = id;
        return this;
    }

    public ImageLoader placeholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public ImageLoader error(Drawable error) {
        this.error = error;
        return this;
    }

    public ImageLoader override(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public void into(ImageView view) {
        this.view = view;
        sImageStrategy.load(this);
    }
}
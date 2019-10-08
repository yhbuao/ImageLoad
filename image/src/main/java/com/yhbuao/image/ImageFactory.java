package com.yhbuao.image;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface ImageFactory<T extends ImageStrategy> {

    /**
     * 创建一个图片加载策略
     */
    T createImageStrategy();

    /**
     * 创建加载占位图
     */
    Drawable createPlaceholder(Context context);

    /**
     * 创建加载错误占位图
     */
    Drawable createError(Context context);

    /**
     * 清除内存缓存
     */
    void clearMemoryCache(Context context);

    /**
     * 清除磁盘缓存
     */
    void clearDiskCache(Context context);
}
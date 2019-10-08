package com.yhbuao.imageload;

import android.app.Application;

import com.yhbuao.image.ImageLoader;
import com.yhbuao.image.picasso.PicassoFactory;

/**
 * @author: yuhaibo
 * @time: 2019-09-27 16:12.
 * projectName: ImageLoad.
 * Description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this,new PicassoFactory());
    }
}

package com.yhbuao.image.glide;

import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.yhbuao.image.ImageLoader.GLIDE_CARCH_DIR;
import static com.yhbuao.image.ImageLoader.GLIDE_CATCH_SIZE;

@GlideModule
public class GlideAppModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, GLIDE_CARCH_DIR, GLIDE_CATCH_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);

        glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);

        glide.getRegistry().prepend(File.class, BitmapFactory.Options.class, new BitmapSizeDecoder());
    }

    /**
     * 清单解析的开启
     * <p>
     * 这里不开启，避免添加相同的modules两次
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }


    class BitmapSizeDecoder implements ResourceDecoder<File, BitmapFactory.Options> {
        @Override
        public boolean handles(File source, Options options) throws IOException {
            return true;
        }

        @Nullable
        @Override
        public Resource<BitmapFactory.Options> decode(File source, int width, int height, Options options) throws IOException {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(source.getAbsolutePath(), opt);
            return new SimpleResource<>(opt);
        }
    }
}

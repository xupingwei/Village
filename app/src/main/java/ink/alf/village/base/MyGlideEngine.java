package ink.alf.village.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

import ink.alf.village.R;

/**
 * @author 13793
 */
public class MyGlideEngine implements ImageEngine {
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {

        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(applyOptions(resize, resize))
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(applyOptions(resize, resize))
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(applyOptions(resizeX, resizeY))
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(applyOptions(resizeX, resizeY))
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return false;
    }

    private RequestOptions applyOptions(int resizeX, int resizeY) {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_image_default)//这里可自己添加占位图
                .error(R.mipmap.icon_image_default)
                .override(resizeX, resizeY);
    }
}

package ink.alf.village.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;

/**
 * @author 13793
 */
public class ImageAdapter extends BaseAdapter {

    private String[] images;
    private Context mContext;
    private Fragment fragment;
    private RequestOptions options;


    @SuppressLint("CheckResult")
    public ImageAdapter(String[] images, Context mContext, Fragment fragment) {

        this.images = images;
        this.fragment = fragment;
        this.mContext = mContext;
        options = new RequestOptions();
        options.centerCrop().placeholder(R.mipmap.icon_image_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == holder) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image_add, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(fragment).load(images[position]).apply(options).into(holder.ivImage);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_add)
        ImageView ivImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

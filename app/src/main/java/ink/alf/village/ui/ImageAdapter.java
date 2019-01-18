package ink.alf.village.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;

/**
 * @author 13793
 */
public class ImageAdapter extends BaseAdapter {

    private String[] images;
    private Context mContext;


    public ImageAdapter(String[] images, Context mContext) {
        this.images = images;
        this.mContext = mContext;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image_add, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.d("ImageAdapter", "getView: " + images[position]);
        Glide.with(mContext).asBitmap().load(images[position]).into(holder.ivImage);
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

package ink.alf.village.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.view.SWImageView;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.widget.MyGridView;

/**
 * @author 13793
 */
public class ContentAdapter extends BaseAdapter {

    private static final String TAG = "ContentAdapter";

    private Context mContext;
    private List<ActivitiBean> activitiBeans;
    private Fragment fragment;

    public ContentAdapter(Context mContext, List<ActivitiBean> activitiBeans, Fragment fragment) {
        this.mContext = mContext;
        this.activitiBeans = activitiBeans;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return activitiBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return activitiBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == holder) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ActivitiBean bean = activitiBeans.get(position);
        holder.tvUserName.setText(bean.getPushNickName());
        holder.tvPushTime.setText(bean.getCreateTime() + "");
        holder.tvCatagory.setText(bean.getCatagory());
        Glide.with(mContext).asBitmap().load(bean.getHeadUrl()).into(holder.ivUserAvatar);
        holder.tvContent.setText(bean.getContent());
        holder.tvFollow.setText(bean.getFollow() + "");
        holder.tvCollect.setText(bean.getCollect() + "");
        String[] images = bean.getContentImages().split(",");
        ImageAdapter imageAdapter = new ImageAdapter(images, mContext, fragment);
        holder.gvImages.setAdapter(imageAdapter);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_user_avatar)
        SWImageView ivUserAvatar;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.iv_time)
        ImageView ivTime;
        @BindView(R.id.tv_push_time)
        TextView tvPushTime;
        @BindView(R.id.tv_catagory)
        TextView tvCatagory;
        @BindView(R.id.lv_images)
        MyGridView gvImages;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_follow)
        TextView tvFollow;
        @BindView(R.id.tv_collect)
        TextView tvCollect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

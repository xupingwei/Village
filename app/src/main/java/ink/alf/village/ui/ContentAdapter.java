package ink.alf.village.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.view.SWImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.ui.activity.ImagePagerActivity;
import ink.alf.village.widget.MyGridView;

/**
 * @author 13793
 */
public class ContentAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ContentAdapter";

    private Context mContext;
    private List<ActivitiBean> activitiBeans = new ArrayList<>();
    private Fragment fragment;

    public ContentAdapter(Context mContext, Fragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
    }

    public void reset(List<ActivitiBean> activitiBeans) {
        this.activitiBeans.clear();
        this.activitiBeans.addAll(activitiBeans);
        this.notifyDataSetChanged();
    }

    public void addDatas(List<ActivitiBean> beans) {
        this.activitiBeans.addAll(beans);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home, viewGroup, false);
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder holder = (ViewHolder) viewHolder;
        ActivitiBean bean = activitiBeans.get(i);
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
        holder.gvImages.setOnItemClickListener((parent1, view, position1, id) -> {
            ArrayList<String> imageUrls = new ArrayList<>(Arrays.asList(images));
            imageBrower(position1, imageUrls);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return activitiBeans.size();
    }

    protected void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

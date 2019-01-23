package ink.alf.village.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.angel.view.SWImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.listener.IOperationOnClickListener;
import ink.alf.village.ui.activity.ImagePagerActivity;
import ink.alf.village.utils.DateUtils;
import ink.alf.village.widget.IconfontTextView;
import ink.alf.village.widget.MyGridView;

/**
 * @author 13793
 */
public class ContentAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ContentAdapter";

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    private Context mContext;
    private List<ActivitiBean> activitiBeans = new ArrayList<>();
    private Fragment fragment;
    private String userId;
    private IOperationOnClickListener iOperationOnClickListener;

    public ContentAdapter(Context mContext, String userId, Fragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
        this.userId = userId;
    }

    public List<ActivitiBean> getActivitiBeans() {
        return activitiBeans;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM) {
            View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home, viewGroup, false);
            ViewHolder holder = new ViewHolder(convertView);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_refresh_footer, viewGroup, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            ActivitiBean bean = activitiBeans.get(i);
            holder.tvUserName.setText(bean.getPushNickName());
            holder.tvPushTime.setText(DateUtils.dateToString(bean.getCreateTime(), DateUtils.DATATIME_FORMMATER));
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

            List<String> followIds = JSON.parseArray(bean.getFollowUserIds(), String.class);
            List<String> collectIds = JSON.parseArray(bean.getCollectUserIds(), String.class);

            if (null != followIds && followIds.contains(userId)) {
                holder.itFollow.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                holder.itFollow.setTextColor(mContext.getResources().getColor(R.color.colorDarkGrey));
            }

            if (null != collectIds && collectIds.contains(userId)) {
                holder.itCollect.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                holder.itCollect.setTextColor(mContext.getResources().getColor(R.color.colorDarkGrey));
            }

            holder.itFollow.setOnClickListener(v -> {
                if (null != iOperationOnClickListener) {
                    iOperationOnClickListener.onFollowClickListener(followIds, userId, i);
                }
            });
            holder.itCollect.setOnClickListener(v -> {
                if (null != iOperationOnClickListener) {
                    iOperationOnClickListener.onCollectClickListener(collectIds, userId, i);
                }
            });
        } else if (viewHolder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return activitiBeans.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
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
        @BindView(R.id.item_it_follow)
        IconfontTextView itFollow;
        @BindView(R.id.item_it_collect)
        IconfontTextView itCollect;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOperationOnClickListener(IOperationOnClickListener iOperationOnClickListener) {
        this.iOperationOnClickListener = iOperationOnClickListener;
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}

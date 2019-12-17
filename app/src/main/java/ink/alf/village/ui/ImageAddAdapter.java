package ink.alf.village.ui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;

/**
 * @author 13793
 */
public class ImageAddAdapter extends RecyclerView.Adapter {

    private static final int TYPE_IMAGE_ADD = 1;
    private static final int TYPE_IMAGE = 2;

    private Context mContext;
    private List<Uri> imageLists = new ArrayList<>();
    private OnItemClickListener listener;

    public ImageAddAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<Uri> getImageLists() {
        return imageLists;
    }

    public void reset(List<Uri> imageLists) {
        this.imageLists.clear();
        this.imageLists.addAll(imageLists);
        this.notifyDataSetChanged();
    }

    public void addDatas(List<Uri> imageLists) {
        this.imageLists.addAll(imageLists);
        if (this.imageLists.size() > 9) {
            this.imageLists = this.imageLists.subList(0, 9);
        }
        this.notifyDataSetChanged();
    }

    public void deleteData(int position) {
        imageLists.remove(position);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder;
        if (viewType == TYPE_IMAGE_ADD) {
            View v = mInflater.inflate(R.layout.item_image_add, viewGroup, false);
            viewHolder = new ImageAddHolder(v);
        } else {
            View v = mInflater.inflate(R.layout.item_image_recyclerview, viewGroup, false);
            viewHolder = new ImageHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ImageAddHolder) {
            ImageAddHolder holder = (ImageAddHolder) viewHolder;
            holder.ivAdd.setOnClickListener(v -> listener.choosePicture());
        } else if (viewHolder instanceof ImageHolder) {
            ImageHolder holder = (ImageHolder) viewHolder;
            Glide.with(mContext).asBitmap().load(imageLists.get(position)).into(holder.iv);
            holder.iv.setOnClickListener(v -> listener.zoomOut(imageLists.get(position)));
            holder.ivCancel.setOnClickListener(v -> {
                listener.deletePickture(position);
            });
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (position == imageLists.size()) {
            return TYPE_IMAGE_ADD;
        }
        return TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        return imageLists.size() + 1;
    }

    class ImageAddHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_add)
        ImageView ivAdd;

        public ImageAddHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView iv;
        @BindView(R.id.iv_cancel)
        ImageView ivCancel;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public interface OnItemClickListener {
        /**
         * 选择图片
         */
        void choosePicture();

        /**
         * 放大图片
         *
         * @param imageLocalUri 图片的本地路径
         */
        void zoomOut(Uri imageLocalUri);

        /**
         * 删除图片
         *
         * @param position
         */
        void deletePickture(int position);
    }
}

package ink.alf.village.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import ink.alf.village.R;
import ink.alf.village.utils.ToastUtils;

/**
 * @author 13793
 */
public class FragmentImageDetail extends Fragment {

    private String mImageUrl;
    private PhotoView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;

    public static FragmentImageDetail newInstance(String imageUrl) {
        final FragmentImageDetail f = new FragmentImageDetail();
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);
        return f;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url")
                : null;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.icon_image_default)
                .fitCenter()
                .error(R.mipmap.icon_image_default)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setZoomable(true);
        mAttacher.setOnClickListener((view) -> getActivity().finish());
        progressBar = v.findViewById(R.id.loading);
        return v;
    }

    private RequestOptions requestOptions;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(mImageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ToastUtils.showToast(getActivity(), e.getMessage());
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        mAttacher.update();
                        return false;
                    }
                })
                .into(mImageView);

    }
}

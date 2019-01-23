package ink.alf.village.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angel.view.SWImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;
import ink.alf.village.base.BaseFragment;
import ink.alf.village.bean.vo.UserInfo;
import ink.alf.village.utils.BlurUtils;

/**
 * @author 13793
 */
public class FragmentMe extends BaseFragment {

    @BindView(R.id.ll_top)
    LinearLayout llHdearLayout;

    @BindView(R.id.rl_nickname_layout)
    RelativeLayout rlNicknameLayout;
    @BindView(R.id.im_logo)
    SWImageView imLogo;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_auth_wx)
    TextView tvAuthWx;
    @BindView(R.id.tv_auth_alipay)
    TextView tvAuthAlipay;
    @BindView(R.id.tv_auth_phone)
    TextView tvAuthPhone;
    @BindView(R.id.tv_upgrade)
    TextView tvUpgrade;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_bg);
        llHdearLayout.setBackground(new BitmapDrawable(BlurUtils.doBlur(bitmap, 10, 10)));

        UserInfo info = getUserInfo();
        Glide.with(this)
                .asBitmap()
                .load(info.getHeadUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_avatar_default)
                        .error(R.mipmap.ic_avatar_default)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imLogo);
        tvNickname.setText(info.getNickName());
        tvGender.setText(info.getGender() == null ? "未知" : info.getGender());
        if (StringUtils.isEmpty(info.getOpenId())) {
            tvAuthWx.setText("未认证");
            tvAuthWx.setTextColor(getActivity().getResources().getColor(R.color.colorGray));

        } else {
            tvAuthWx.setText("已认证");
            tvAuthWx.setTextColor(getActivity().getResources().getColor(R.color.colorOrange));
        }
        if (StringUtils.isEmpty(info.getAlipayAcount())) {
            tvAuthAlipay.setText("未认证");
            tvAuthAlipay.setTextColor(getActivity().getResources().getColor(R.color.colorGray));

        } else {
            tvAuthAlipay.setText("已认证");
            tvAuthAlipay.setTextColor(getActivity().getResources().getColor(R.color.colorOrange));
        }
        tvAuthPhone.setText(info.getPhone());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

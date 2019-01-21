package ink.alf.village.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;
import ink.alf.village.common.MainConstants;
import ink.alf.village.mvp.presenter.HomePresenter;
import ink.alf.village.ui.HomePagerAdapter;
import ink.alf.village.ui.activity.SelectAreaActivity;
import ink.alf.village.utils.PermissionsUtils;
import ink.alf.village.utils.SharedPreferencesHelper;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.mvp.view.IHomeView;

/**
 * @author 13793
 */
@SuppressLint("ValidFragment")
public class FragmentHome extends Fragment implements IHomeView {

    private static final String TAG = "FragmentHome";

    @BindView(R.id.ll_layout_location)
    LinearLayout llLayoutLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_hot)
    TextView tvHot;

    @BindView(R.id.ivbtn_more)
    ImageButton ivBtnMore;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.nearby_viewpager)
    ViewPager tabViewPager;

    private Unbinder unbinder;

    private Map<String, String> tabs = MainConstants.TABS;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    private HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authPermissions();
        for (Map.Entry<String, String> entry : tabs.entrySet()) {
            titles.add(entry.getValue());
            fragments.add(FragmentContent.newInstance(entry.getKey()));
        }
        tabViewPager.setAdapter(new HomePagerAdapter(getFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(tabViewPager);

        homePresenter = new HomePresenter(getActivity(), this);
        homePresenter.location();

        ivBtnMore.setOnClickListener(v -> ToastUtils.showToast(getActivity(), "MORE"));
        llLayoutLocation.setOnClickListener(v -> startActivity(new Intent(getActivity(), SelectAreaActivity.class)));
    }

    @Override
    public void onDestroyView() {
        titles.clear();
        fragments.clear();
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 授权
     */
    private void authPermissions() {
        PermissionsUtils.getInstance().chekPermissions(getActivity(), permissions,
                new PermissionsUtils.IPermissionsResult() {
                    @Override
                    public void passPermissons() {
                        Log.d(TAG, "passPermissons: 权限通过");

                    }

                    @Override
                    public void forbitPermissons() {
                        ToastUtils.showToast(getActivity(), "权限已禁用");

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }


    @Override
    public void locationSuccess(BDLocation location) {
        SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(getActivity());
        preferencesHelper.put("location", location.getCity());
        preferencesHelper.put("address", location.getAddrStr());

    }

    @Override
    public void locationFailed(String msg, int code) {

    }
}
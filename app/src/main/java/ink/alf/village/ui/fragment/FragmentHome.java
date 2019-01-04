package ink.alf.village.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.presenter.HomePresenter;
import ink.alf.village.ui.HomeAdapter;
import ink.alf.village.utils.PermissionsUtils;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.view.IHomeView;

/**
 * @author 13793
 */
public class FragmentHome extends Fragment implements IHomeView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FragmentHome";

    private Unbinder unbinder;
    private HomePresenter homePresenter;

    @BindView(R.id.ll_layout_location)
    LinearLayout llLayoutLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_hot)
    TextView tvHot;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.lv_home)
    ListView lvHome;

    //
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authPermissions();
        homePresenter = new HomePresenter(getActivity(), this);
        homePresenter.location();

        refreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        refreshLayout.setRefreshing(true);
        homePresenter.loadMainData();
        refreshLayout.setOnRefreshListener(this);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
        homePresenter.loadMainData();
    }

    private List<ActivitiBean> mainActivitiBeans = new ArrayList<>();
    @Override
    public void loadMainDataSuccess(List<ActivitiBean> activitiBeans) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        mainActivitiBeans.addAll(activitiBeans);
        HomeAdapter homeAdapter = new HomeAdapter(getActivity(), mainActivitiBeans);
        lvHome.setAdapter(homeAdapter);
        lvHome.setOnItemClickListener((parent, view, position, id) ->
                ToastUtils.showToast(getActivity(), "position = " + position));
    }

    @Override
    public void loadMainDataFailed(String msg, int code) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void locationSuccess(BDLocation location) {

    }

    @Override
    public void locationFailed(String msg, int code) {

    }

}

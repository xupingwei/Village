package ink.alf.village.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.presenter.HomePresenter;
import ink.alf.village.view.IHomeView;

/**
 * @author 13793
 */
public class FragmentHome extends Fragment implements IHomeView {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter = new HomePresenter(getActivity(), this);
        homePresenter.location();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadMainDataSuccess(List<ActivitiBean> activitiBeans) {

    }

    @Override
    public void loadMainDataFailed(String msg, int code) {

    }

    @Override
    public void locationSuccess(BDLocation location) {

    }

    @Override
    public void locationFailed(String msg, int code) {

    }
}

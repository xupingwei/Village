package ink.alf.village.ui.fragment;

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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.presenter.ContentPresenter;
import ink.alf.village.ui.HomeAdapter;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.view.IContentView;

/**
 * @author 13793
 */
public class FragmentContent extends Fragment implements IContentView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FragmentContent";
    private Unbinder unbinder;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.lv_home)
    ListView lvHome;

    private ContentPresenter contentPresenter;

    private String reqKey = "";

    public static FragmentContent newInstance(String reqKey) {
        Bundle args = new Bundle();
        args.putString("reqKey", reqKey);
        FragmentContent fragment = new FragmentContent();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            reqKey = bundle.getString("reqKey");
        }
        ToastUtils.showToast(getActivity(), reqKey);
        Log.d(TAG, "onViewCreated: reqKey = " + reqKey);
        contentPresenter = new ContentPresenter(getActivity(), this);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        refreshLayout.setRefreshing(true);
        contentPresenter.loadMainData();
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        contentPresenter.loadMainData();
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
}

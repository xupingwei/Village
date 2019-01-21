package ink.alf.village.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import ink.alf.village.base.BaseFragment;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.bean.vo.ActivitiPagerInfo;
import ink.alf.village.presenter.ContentPresenter;
import ink.alf.village.ui.ContentAdapter;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.view.IContentView;

/**
 * @author 13793
 */
public class FragmentContent extends BaseFragment implements IContentView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FragmentContent";
    private Unbinder unbinder;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.lv_home)
    ListView lvHome;

    private ContentPresenter contentPresenter;


    private List<ActivitiBean> mainActivitiBeans = new ArrayList<>();
    private ContentAdapter contentAdapter;
    private int currPage = 0;
    private int pageCount = 10;

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
        Log.d(TAG, "onViewCreated: reqKey = " + reqKey);
        contentPresenter = new ContentPresenter(getActivity(), this);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        refreshLayout.setRefreshing(true);
        contentPresenter.loadNewerData(getToken(), currPage, pageCount);
        refreshLayout.setOnRefreshListener(this);

        //
        contentAdapter = new ContentAdapter(getActivity(), mainActivitiBeans, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        currPage = 0;
        contentPresenter.loadNewerData(getToken(), currPage, 10);
    }


    @Override
    public void loadMainDataSuccess(ActivitiPagerInfo pagerInfo) {
        if (null != refreshLayout && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (currPage == 0) {
            mainActivitiBeans.clear();
            mainActivitiBeans.addAll(pagerInfo.getLists());
        } else {
            mainActivitiBeans.addAll(pagerInfo.getLists());
        }
        contentAdapter.notifyDataSetChanged();
        if (null != lvHome) {
            lvHome.setAdapter(contentAdapter);
            lvHome.setOnItemClickListener((parent, view, position, id) ->
                    ToastUtils.showToast(getActivity(), "position = " + position));
        }
        currPage = pagerInfo.getPage() + 1;
    }

    @Override
    public void loadMainDataFailed(String msg, int code) {
        if (null != refreshLayout && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

    }
}

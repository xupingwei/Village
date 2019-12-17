package ink.alf.village.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.bean.vo.ActivitiPagerInfo;
import ink.alf.village.listener.EndlessRecyclerOnScrollListener;
import ink.alf.village.listener.IOperationOnClickListener;
import ink.alf.village.mvp.presenter.ContentPresenter;
import ink.alf.village.mvp.view.IContentView;
import ink.alf.village.ui.ContentAdapter;

/**
 * @author 13793
 */
public class FragmentContent extends BaseLazyLoadFragment implements IContentView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FragmentContent";
    private Unbinder unbinder;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ContentPresenter contentPresenter;
    private String userId;


    private List<ActivitiBean> mainActivitiBeans = new ArrayList<>();
    private ContentAdapter contentAdapter;
    private int currPage = 0;
    private int pageCount = 10;
    private boolean isLoadMore = true;

    private String reqKey = "";

    public static FragmentContent newInstance(String reqKey) {
        Bundle args = new Bundle();
        args.putString("reqKey", reqKey);
        FragmentContent fragment = new FragmentContent();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void onLazyLoad() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            reqKey = bundle.getString("reqKey");
        }
        currPage = 0;
        Log.d(TAG, "onViewCreated: reqKey = " + reqKey);
        contentPresenter = new ContentPresenter(getActivity(), this);
        //refreshLayout
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        refreshLayout.setRefreshing(true);
        //請求數據
        contentPresenter.listCatagory(getToken(), reqKey, currPage, pageCount);
        //獲取userId
        userId = getUserInfo().getId();
        //LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        contentAdapter = new ContentAdapter(getActivity(), userId, this);
        recyclerView.setAdapter(contentAdapter);

        //事件
        contentAdapter.setOperationOnClickListener(new IOperationOnClickListener() {
            @Override
            public void onFollowClickListener(List<String> followIds, String userId, int position) {
                List<ActivitiBean> beans = contentAdapter.getActivitiBeans();
                if (followIds == null) {
                    followIds = new ArrayList<>();
                }
                int f = beans.get(position).getFollow();
                int follow = 0;
                if (followIds.contains(userId)) {
                    followIds.remove(userId);
                    beans.get(position).setFollow((f - 1) < 0 ? 0 : f - 1);
                    follow = 0;
                } else {
                    followIds.add(userId);
                    beans.get(position).setFollow(f + 1);
                    follow = 1;
                }
                beans.get(position).setFollowUserIds(JSON.toJSONString(followIds));
                Log.d(TAG, "onFollowClickListener: " + JSON.toJSONString(beans));
                contentAdapter.notifyDataSetChanged();
                contentPresenter.follow(userId, beans.get(position).getId(), follow);
            }

            @Override
            public void onCollectClickListener(List<String> collectIds, String userId, int position) {
                List<ActivitiBean> beans = contentAdapter.getActivitiBeans();
                if (null == collectIds) {
                    collectIds = new ArrayList<>();
                }
                int c = beans.get(position).getCollect();
                int collect = 0;
                if (collectIds.contains(userId)) {
                    collectIds.remove(userId);
                    beans.get(position).setCollect((c - 1) < 0 ? 0 : c - 1);
                    collect = 0;
                } else {
                    collectIds.add(userId);
                    beans.get(position).setCollect(c + 1);
                    collect = 1;
                }
                beans.get(position).setCollectUserIds(JSON.toJSONString(collectIds));
                contentAdapter.notifyDataSetChanged();
                contentPresenter.collect(userId, beans.get(position).getId(), collect);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoadMore) {
                    contentAdapter.setLoadState(contentAdapter.LOADING);
                    Log.d(TAG, "loadMainDataSuccess: currPage = " + currPage);
                    contentPresenter.listCatagory(getToken(), reqKey, currPage, pageCount);
                }
            }
        });
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
        currPage = 0;
        isLoadMore = true;
        contentPresenter.listCatagory(getToken(), reqKey, currPage, 10);
    }


    @Override
    public void loadMainDataSuccess(ActivitiPagerInfo pagerInfo) {
        if (null != refreshLayout && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (currPage == 0) {
            contentAdapter.reset(pagerInfo.getLists());
        } else {
            contentAdapter.addDatas(pagerInfo.getLists());
        }
        if (currPage == pagerInfo.getTotalPages()) {
            // 显示加载到底的提示
            contentAdapter.setLoadState(contentAdapter.LOADING_END);
            isLoadMore = false;
        } else {
            contentAdapter.setLoadState(contentAdapter.LOADING_COMPLETE);
            currPage = pagerInfo.getPage() + 1;
        }
    }

    @Override
    public void loadMainDataFailed(String msg, int code) {
        if (null != refreshLayout && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void followSuccess(String msg) {
        Log.d(TAG, "followSuccess: " + msg);
    }

    @Override
    public void collectSuccess(String msg) {
        Log.d(TAG, "collectSuccess: " + msg);
    }
}

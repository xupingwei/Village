package ink.alf.village.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ink.alf.village.base.BaseFragment;

/**
 * @author 13793
 */
public abstract class BaseLazyLoadFragment extends BaseFragment {

    private boolean isFirstLoad = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container);//让子类实现初始化视图
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isFirstLoad = true;//视图创建完成，将变量置为true
        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            onLazyLoad();
            isFirstLoad = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;//视图销毁将变量置为false
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {//视图变为可见并且是第一次加载
            onLazyLoad();
            isFirstLoad = false;
        }
    }

    /**
     * 数据加载接口，留给子类实现
     */
    protected abstract void onLazyLoad();

    /**
     * 初始化视图接口，子类必须实现
     *
     * @param inflater
     * @param container
     * @return
     */
    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

}

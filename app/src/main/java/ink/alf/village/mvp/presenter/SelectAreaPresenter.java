package ink.alf.village.mvp.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import ink.alf.village.bean.Region;
import ink.alf.village.mvp.service.IMainService;
import ink.alf.village.mvp.view.ISelectAreaView;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;

public class SelectAreaPresenter {

    private Context mContext;
    private ISelectAreaView iSelectAreaView;

    public SelectAreaPresenter(Context mContext, ISelectAreaView iSelectAreaView) {
        this.mContext = mContext;
        this.iSelectAreaView = iSelectAreaView;
    }


    public void refreshLocation() {

    }


    public void findAllRegions() {
        RetrofitClient.getRetrofit().create(IMainService.class).listRegions()
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {
                        iSelectAreaView.loadRegionsSuccess(JSON.parseArray(data, Region.class));
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        iSelectAreaView.loadRegionsFailure(msg, errorCode);
                    }
                });
    }
}

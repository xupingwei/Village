package ink.alf.village.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import ink.alf.village.bean.vo.ActivitiPagerInfo;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;
import ink.alf.village.service.IMainService;
import ink.alf.village.view.IContentView;

/**
 * @author 13793
 */
public class ContentPresenter {

    private Context mContext;
    private IContentView iContentView;

    public ContentPresenter(Context mContext, IContentView iContentView) {
        this.mContext = mContext;
        this.iContentView = iContentView;
    }

    public void loadNewerData(String token, int page, int pageCount) {
        Map<String, Object> mapValus = new HashMap<>();
        mapValus.put("token", token);
        mapValus.put("page", page);
        mapValus.put("pageCount", pageCount);
        RetrofitClient.getRetrofit().create(IMainService.class).listNewer(mapValus)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {
                        ActivitiPagerInfo info = JSON.parseObject(data, ActivitiPagerInfo.class);
                        iContentView.loadMainDataSuccess(info);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        iContentView.loadMainDataFailed(msg, errorCode);
                    }
                });
    }

}

package ink.alf.village.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import ink.alf.village.bean.vo.ActivitiPagerInfo;
import ink.alf.village.mvp.service.IActivitiService;
import ink.alf.village.mvp.view.IContentView;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;

/**
 * @author 13793
 */
public class ContentPresenter {

    private Context mContext;
    private IContentView iContentView;

    private static final String TAG = "ContentPresenter";

    public ContentPresenter(Context mContext, IContentView iContentView) {
        this.mContext = mContext;
        this.iContentView = iContentView;
    }

    /**
     * 加載活動數據
     *
     * @param token     用戶登錄標識
     * @param page      當前頁數
     * @param pageCount 每頁顯示的數量
     */
    public void loadNewerData(String token, int page, int pageCount) {
        Map<String, Object> mapValus = new HashMap<>();
        mapValus.put("token", token);
        mapValus.put("page", page);
        mapValus.put("pageCount", pageCount);
        RetrofitClient.getRetrofit().create(IActivitiService.class).listNewer(mapValus)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext, false) {
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

    /**
     * 分類查詢
     *
     * @param token     用戶登錄標識
     * @param salt      分類類別
     * @param page      當前頁碼
     * @param pageCount 每頁顯示的數量
     */
    public void listCatagory(String token, String salt, int page, int pageCount) {
        Map<String, Object> mapValus = new HashMap<>();
        mapValus.put("token", token);
        mapValus.put("page", page);
        mapValus.put("pageCount", pageCount);
        mapValus.put("salt", salt);
        RetrofitClient.getRetrofit().create(IActivitiService.class).listCatagory(mapValus)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext, false) {
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

    /**
     * @param userId 點讚用戶的id
     * @param acId   被點讚的活動id
     * @param follow 1:點讚  0：取消點讚
     */
    public void follow(String userId, String acId, int follow) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("aid", acId);
        params.put("follow", follow);
        RetrofitClient.getRetrofit().create(IActivitiService.class).follow(params)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext, false) {
                    @Override
                    public void onSuccess(String data) {
                        iContentView.followSuccess("點讚成功");
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        Log.e(TAG, String.format("{errorCode = %1$s,msg = %2$s}", errorCode, msg));
                    }
                });

    }


    /**
     * @param userId  點讚用戶的id
     * @param acId    被點讚的活動id
     * @param collect 1:收藏  0：取消收藏
     */
    public void collect(String userId, String acId, int collect) {

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("aid", acId);
        params.put("collect", collect);
        RetrofitClient.getRetrofit().create(IActivitiService.class).collect(params)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext, false) {
                    @Override
                    public void onSuccess(String data) {
                        iContentView.collectSuccess("收藏成功");
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        Log.e(TAG, String.format("{errorCode = %1$s,msg = %2$s}", errorCode, msg));
                    }
                });

    }

}

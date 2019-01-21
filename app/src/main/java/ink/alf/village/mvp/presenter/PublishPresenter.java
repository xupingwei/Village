package ink.alf.village.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.listener.UploadMutliListener;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;
import ink.alf.village.mvp.service.IActivitiService;
import ink.alf.village.utils.QiniuUploadHelper;
import ink.alf.village.mvp.view.IPublishView;

/**
 * @author 13793
 */
public class PublishPresenter {

    private Context mContext;
    private IPublishView iPublishView;

    public PublishPresenter(Context mContext, IPublishView iPublishView) {
        this.mContext = mContext;
        this.iPublishView = iPublishView;
    }


    public void uploadImage(List<String> filesPath) {
        QiniuUploadHelper.getInstance()
                .uploadMutliFiles(filesPath, new UploadMutliListener() {
                    @Override
                    public void onUploadMutliSuccess(String urls) {
                        Log.i("uploadImage", "onUploadMutliSuccess: " + urls);
                        iPublishView.uploadImageSuccess(urls);
                    }

                    @Override
                    public void onUploadMutliFail(Error error) {
                        Log.i("uploadImage", "onUploadMutliFail: " + error);
                        iPublishView.uploadImageFailure(error.getMessage(), 1000);
                    }
                });
    }


    /**
     * 创建活动
     *
     * @param token token
     * @param bean  bean
     */
    public void createActiviti(String token, ActivitiBean bean) {

        Map<String, Object> mapValus = new HashMap<>();
        mapValus.put("catagory", bean.getCatagory());
        mapValus.put("content", bean.getContent());
        mapValus.put("images", bean.getContentImages());
        mapValus.put("salt", bean.getSalt());
        mapValus.put("address", bean.getAddress());
        RetrofitClient.getRetrofit().create(IActivitiService.class).createActiviti(token, mapValus)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {
                        ActivitiBean activitiBean = JSON.parseObject(data, ActivitiBean.class);
                        Log.i("createActiviti", "onSuccess: " + activitiBean.toString());
                        iPublishView.insertActivitiSuccess();
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        iPublishView.insertActivitiFailure(msg, errorCode);
                    }
                });

    }
}

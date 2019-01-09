package ink.alf.village.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.listener.UploadMutliListener;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;
import ink.alf.village.service.IMainService;
import ink.alf.village.utils.DialogUtils;
import ink.alf.village.utils.QiniuUploadHelper;
import ink.alf.village.view.IPublishView;

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
        DialogUtils.getInstance(mContext).show();
        QiniuUploadHelper.getInstance()
                .uploadMutliFiles(filesPath, new UploadMutliListener() {
                    @Override
                    public void onUploadMutliSuccess(String urls) {
                        Log.i("uploadImage", "onUploadMutliSuccess: " + urls);
                        DialogUtils.getInstance(mContext).dismiss();
                    }

                    @Override
                    public void onUploadMutliFail(Error error) {
                        DialogUtils.getInstance(mContext).dismiss();
                        Log.i("uploadImage", "onUploadMutliFail: " + error);
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

        RetrofitClient.getRetrofit().create(IMainService.class).createActiviti(token, bean)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {

                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {

                    }
                });

    }
}

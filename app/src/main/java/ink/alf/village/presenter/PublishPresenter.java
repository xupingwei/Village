package ink.alf.village.presenter;

import android.content.Context;
import android.util.Log;

import ink.alf.village.base.BaseApplication;
import ink.alf.village.common.QiniuConsts;
import ink.alf.village.utils.QiniuUtils;
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


    public void uploadImage(String filePath) {
        String upToken = QiniuUtils.getQiniuToken();
        String fileKey = String.valueOf(System.currentTimeMillis());
        BaseApplication.uploadManager.put(filePath, fileKey, upToken, (key1, info, response) -> {

            Log.i("qiniu", key1 + ",\r\n " + info + ",\r\n " + response);
            if (info.isOK()) {
                iPublishView.uploadImageSuccess(
                        QiniuUtils.privateDownloadUrl(QiniuConsts.BASE_IMAGE_URL + key1));

            } else {
                //failure
                Log.i("qiniu", "uploadImage: failure");

            }
        }, null);
    }
}

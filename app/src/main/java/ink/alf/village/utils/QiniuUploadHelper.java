package ink.alf.village.utils;

import android.os.Handler;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ink.alf.village.base.BaseApplication;
import ink.alf.village.common.QiniuConsts;
import ink.alf.village.listener.UploadListener;
import ink.alf.village.listener.UploadMutliListener;

public class QiniuUploadHelper {

    private static final String TAG = "QiniuUploadHelper";


    private ExecutorService singleService = Executors.newSingleThreadExecutor();
    private UploadManager uploadManager;

    private static QiniuUploadHelper uploadHelper = new QiniuUploadHelper();

    private QiniuUploadHelper() {

    }

    public static synchronized QiniuUploadHelper getInstance() {
        return uploadHelper;
    }


    /**
     * 上传多张图片
     *
     * @param filesUrls           待上传的图片路径
     * @param uploadMutliListener
     */
    public void uploadMutliFiles(final List<String> filesUrls,
                                 final UploadMutliListener uploadMutliListener) {
        final int[] index = {0};
        StringBuilder urls = new StringBuilder();
        final String token = QiniuUtils.getQiniuToken();
        if (filesUrls != null && filesUrls.size() > 0) {
            final String url = filesUrls.get(index[0]);
            uploadFile(url, token, new UploadListener() {
                @Override
                public void onUploadSuccess(String key) {
                    String imageRealUrl = QiniuUtils.privateDownloadUrl(QiniuConsts.BASE_IMAGE_URL + key);
                    if (index[0] == 0) {
                        urls.append(imageRealUrl);
                    } else {
                        urls.append(",").append(imageRealUrl);
                    }
                    final UploadListener uploadListener = this;
                    Log.d(TAG, "第" + (index[0] + 1) + "张:" + url + "\t上传成功!");
                    index[0]++;
                    //递归边界条件
                    if (index[0] < filesUrls.size()) {
                        //七牛后台对上传的文件名是以时间戳来命名，以秒为单位，如果文件上传过快，两张图片就会重名而上传失败，所以间隔1秒，保证上传成功（具体会不会失败呢？自己试一下看看）
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadFile(filesUrls.get(index[0]), token, uploadListener);
                            }
                        }, 1000);
                    } else {
                        uploadMutliListener.onUploadMutliSuccess(urls.toString());
                    }
                }

                @Override
                public void onUploadFail(Error error) {
                    Log.i(TAG, "onUploadFail: 第" + (index[0] + 1) + "张上传失败!" + filesUrls.get(index[0]));
                    uploadMutliListener.onUploadMutliFail(error);
                }
            });

        }
    }

    /**
     * 上传单个文件
     *
     * @param filePath       文件地址
     * @param uploadListener 回调
     */
    public void uploadFile(final String filePath, String upToken, final UploadListener uploadListener) {
        if (uploadManager == null) {
            uploadManager = BaseApplication.uploadManager;
        }
        if (filePath == null) {
            return;
        }
        singleService.submit(new Runnable() {
            @Override
            public void run() {
                uploadManager.put(filePath, MD5.getMD5(filePath), upToken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo respInfo, JSONObject jsonData) {

                                if (respInfo.isOK()) {
                                    Log.i(TAG, "complete: " + jsonData.toString());
                                    uploadListener.onUploadSuccess(key);

                                } else {
                                    uploadListener.onUploadFail(new Error("上传失败" + respInfo.error));
                                }
                            }
                        }, null);
            }
        });
    }
}

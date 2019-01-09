//package ink.alf.village.utils;
//
//import android.os.Handler;
//import android.util.Log;
//
//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;
//
//import org.json.JSONObject;
//
//import java.util.List;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
//import ink.alf.village.base.BaseApplication;
//import ink.alf.village.listener.UploadListener;
//import ink.alf.village.listener.UploadMutliListener;
//
//public class QiniuUploadUtils {
//
//    //上传多张图片
//    public void uploadMutliFiles(final List<String> filesUrls,
//                                 final UploadMutliListener uploadMutliListener) {
//        if (filesUrls != null && filesUrls.size() > 0) {
//            final String url = filesUrls.get(i[0]);
//            uploadFile(url, new UploadListener() {
//                @Override
//                public void onUploadSuccess() {
//                    final UploadListener uploadListener = this;
//                    Log.d("QiniuUploadUtils", "第" + (i[0] + 1) + "张:" + url + "\t上传成功!");
//                    i[0]++;
//                    //递归边界条件
//                    if (i[0] < filesUrls.size()) {
//                        //七牛后台对上传的文件名是以时间戳来命名，以秒为单位，如果文件上传过快，两张图片就会重名而上传失败，所以间隔1秒，保证上传成功（具体会不会失败呢？自己试一下看看）
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                uploadFile(filesUrls.get(i[0]), uploadListener);
//                            }
//                        }, 1000);
//                    } else {
//                        uploadMutliListener.onUploadMutliSuccess();
//                    }
//                }
//
//                @Override
//                public void onUploadFail(Error error) {
//                    print("第" + (i[0] + 1) + "张上传失败!" + filesUrls.get(i[0]));
//                    uploadMutliListener.onUploadMutliFail(error);
//                }
//            });
//
//        }
//    }
//
//
//    private static ThreadPoolExecutor executor = new ThreadPoolExecutor();
//
//    /**
//     * 上传单个文件
//     *
//     * @param filePath       文件地址
//     * @param uploadListener 回调
//     */
//    public void uploadFile(final String filePath, final UploadListener uploadListener) {
//        UploadManager uploadManager = BaseApplication.uploadManager;
//        if (filePath == null) {
//            return;
//        }
//
//
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                uploadManager.put(filePath, null, upToken,
//                        new UpCompletionHandler() {
//                            @Override
//                            public void complete(String key, ResponseInfo respInfo,
//                                                 JSONObject jsonData) {
//
//                                if (respInfo.isOK()) {
//                                    print(jsonData.toString());
//                                    uploadListener.onUploadSuccess();
//
//                                } else {
//                                    uploadListener.onUploadFail(new Error("上传失败" + respInfo.error));
//                                }
//                            }
//
//                        }, null);
//            }
//        }).start();
//    }
//}

package ink.alf.village.base;

import android.app.Application;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import ink.alf.village.common.MainConstants;
import ink.alf.village.utils.BaiduLocUtils;


/**
 * @author 13793
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    public static IWXAPI iwxapi;
    public static UploadManager uploadManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        iwxapi = WXAPIFactory.createWXAPI(this, MainConstants.WX_APPID, false);
        iwxapi.registerApp(MainConstants.WX_APPID);
        BaiduLocUtils.getInstance().init(this);
        initQiniu();
    }

    private void initQiniu() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new UploadManager(config);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}

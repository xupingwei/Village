package ink.alf.village.base;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import ink.alf.village.MainConstants;


/**
 * @author 13793
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    public static IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        iwxapi = WXAPIFactory.createWXAPI(this, MainConstants.WX_APPID, false);
        iwxapi.registerApp(MainConstants.WX_APPID);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}

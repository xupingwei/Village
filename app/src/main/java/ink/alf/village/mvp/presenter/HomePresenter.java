package ink.alf.village.mvp.presenter;

import android.content.Context;
import android.util.Log;

import ink.alf.village.utils.BaiduLocUtils;
import ink.alf.village.mvp.view.IHomeView;

/**
 * @author 13793
 */
public class HomePresenter {

    private static final String TAG = "HomePresenter";

    private Context mContext;
    private IHomeView iHomeView;

    public HomePresenter(Context mContext, IHomeView iHomeView) {
        this.mContext = mContext;
        this.iHomeView = iHomeView;
    }

    /**
     * 定位
     */
    public void location() {
        BaiduLocUtils.getInstance().start();
        BaiduLocUtils.getInstance().setBaiduLocationListener(location -> {

            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();

            Log.d(TAG, "onReceiveLocation: latitude=" + latitude + ",longitude=" + longitude + "," +
                    "radius=" + radius + ",coorType=" + coorType + ",errorCode=" + errorCode);
            Log.d(TAG, "addrStr: " + location.getAddrStr() + ",province:" + location.getProvince() +
                    ",city:" + location.getCity() + ",District:" + location.getDistrict()
                    + ",Street:" + location.getStreet());
            iHomeView.locationSuccess(location);
        });
    }

}

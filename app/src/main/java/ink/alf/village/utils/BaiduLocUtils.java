package ink.alf.village.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import ink.alf.village.listener.BaiduLocationListener;

/**
 * @author 13793
 */
public class BaiduLocUtils {

    @SuppressLint("StaticFieldLeak")
    private static LocationClient locationClient;
    private static LocationClientOption locationClientOption;
    private static BaiduLocationListener locationListener;

    public static void init(Context context) {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClientOption = new LocationClientOption();

        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (null != locationListener) {
                    locationListener.onReceiveLocation(bdLocation);
                }
            }
        });

        //可选，默认高精度。高精度，低功耗，仅设备
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //返回的结果坐标系，默认gcj02,如果配合百度地图使用，建议设置为bd09ll
        locationClientOption.setCoorType("bd09ll");
        //默认0仅定位一次。设置连续定位请求时间间隔大于1000ms才有效
        locationClientOption.setScanSpan(1000);
        //设置是否需要地址信息，默认不需要
        locationClientOption.setIsNeedAddress(true);
        //设置是否需要地址描述
        locationClientOption.setIsNeedLocationDescribe(true);
        //设置是否需要设备方向结果
        locationClientOption.setIsNeedLocationDescribe(false);
        //设置是否当GPS有效时，按照频率1s1次输出GPS结果
        locationClientOption.setLocationNotify(true);
        //默认不杀死进程
        locationClientOption.setIgnoreKillProcess(true);
        //设置是否需要未知定义化结果，结构类似于“在北京天安门附近”
        locationClientOption.setIsNeedLocationDescribe(true);
        //设置是否需要POI结果
        locationClientOption.setIsNeedLocationPoiList(true);
        //是否收集crash信息，默认收集
        locationClientOption.SetIgnoreCacheException(false);
        locationClientOption.setOpenGps(true);
        //设置是否需要海拔信息，默认不需要
        locationClientOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式
        locationClientOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationClientOption.setOpenAutoNotifyMode(3000, 1,
                LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //设置option
        locationClient.setLocOption(locationClientOption);

    }

    public static void start() {
        if (null == locationClient) {
            throw new RuntimeException("LocationClient is not initial");
        }
        if (!locationClient.isStarted()) {
            locationClient.start();
        }
    }

    /**
     * 调起前台定位
     *
     * @param id
     * @param notification
     */
    public static void enableLocInForeground(int id, Notification notification) {

        if (null == locationClient) {
            throw new RuntimeException("LocationClient is not initial");
        }

        // 调起前台定位
        locationClient.enableLocInForeground(1001, notification);
    }

    /**
     * 关闭前台定位，同时移除通知栏
     *
     * @param b
     */
    public static void disableLocInForeground(boolean b) {
        if (null == locationClient) {
            throw new RuntimeException("LocationClient is not initial");
        }
        locationClient.disableLocInForeground(b);
    }


    public static void setBaiduLocationListener(BaiduLocationListener listener) {
        locationListener = listener;
    }
}
package ink.alf.village.presenter;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;

import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.listener.BaiduLocationListener;
import ink.alf.village.utils.BaiduLocUtils;
import ink.alf.village.view.IHomeView;

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
        BaiduLocUtils.start();
        BaiduLocUtils.setBaiduLocationListener(new BaiduLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {

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
                iHomeView.locationSuccess(location);
            }
        });
    }

    public void loadMainData() {

        List<ActivitiBean> beans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ActivitiBean bean = new ActivitiBean();
            bean.setPushName("张三" + i);
            beans.add(bean);
        }
        iHomeView.loadMainDataSuccess(beans);
    }
}

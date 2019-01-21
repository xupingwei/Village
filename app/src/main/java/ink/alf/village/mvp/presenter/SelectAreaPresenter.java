package ink.alf.village.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import ink.alf.village.bean.Region;
import ink.alf.village.mvp.service.IMainService;
import ink.alf.village.mvp.view.ISelectAreaView;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;
import ink.alf.village.utils.BaiduLocUtils;

public class SelectAreaPresenter {

    private Context mContext;
    private ISelectAreaView iSelectAreaView;

    public SelectAreaPresenter(Context mContext, ISelectAreaView iSelectAreaView) {
        this.mContext = mContext;
        this.iSelectAreaView = iSelectAreaView;
    }


    public void refreshLocation() {
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

            Log.d("SelectAreaPresenter", "onReceiveLocation: latitude=" + latitude + ",longitude=" + longitude + "," +
                    "radius=" + radius + ",coorType=" + coorType + ",errorCode=" + errorCode);
            Log.d("SelectAreaPresenter", "addrStr: " + location.getAddrStr() + ",province:" + location.getProvince() +
                    ",city:" + location.getCity() + ",District:" + location.getDistrict()
                    + ",Street:" + location.getStreet());
            iSelectAreaView.refreshLocationSuccess(location);
        });
    }


    public void findAllRegions() {
        RetrofitClient.getRetrofit().create(IMainService.class).listRegions()
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {
                        iSelectAreaView.loadRegionsSuccess(JSON.parseArray(data, Region.class));
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        iSelectAreaView.loadRegionsFailure(msg, errorCode);
                    }
                });
    }
}

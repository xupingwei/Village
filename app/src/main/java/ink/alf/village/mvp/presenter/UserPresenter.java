package ink.alf.village.mvp.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import ink.alf.village.bean.vo.UserInfo;
import ink.alf.village.mvp.service.IUserService;
import ink.alf.village.mvp.view.IUser2View;
import ink.alf.village.retrofit.RetrofitClient;
import ink.alf.village.retrofit.subscriber.ApiCallback;
import ink.alf.village.retrofit.subscriber.SchedulersCompat;

public class UserPresenter {
    private Context mContext;
    private IUser2View iUser2View;

    public UserPresenter(Context mContext, IUser2View iUser2View) {
        this.mContext = mContext;
        this.iUser2View = iUser2View;
    }

    /**
     * 注册
     *
     * @param phone 手机号码
     * @param pass  密码
     * @param code  验证码
     */

    public void register(String phone, String pass, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("pass", pass);
        params.put("phone", phone);
        params.put("code", code);
        RetrofitClient.getRetrofit().create(IUserService.class).register(params)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {
                        UserInfo info = JSON.parseObject(data, UserInfo.class);
                        iUser2View.onSuccess(info);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        iUser2View.onFailure(msg, errorCode);
                    }
                });
    }

    /**
     * @param phone 手机号
     * @param pass  密码
     */

    public void loginWithPhone(String phone, String pass) {
        Map<String, Object> params = new HashMap<>();
        params.put("pass", pass);
        params.put("phone", phone);
        RetrofitClient.getRetrofit().create(IUserService.class).loginWithPhone(params)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(new ApiCallback(mContext) {
                    @Override
                    public void onSuccess(String data) {
                        UserInfo info = JSON.parseObject(data, UserInfo.class);
                        iUser2View.onSuccess(info);
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        iUser2View.onFailure(msg, errorCode);
                    }
                });
    }
}

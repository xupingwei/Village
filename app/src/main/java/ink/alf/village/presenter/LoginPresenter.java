package ink.alf.village.presenter;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ink.alf.village.base.BaseApplication;
import ink.alf.village.bean.MessageEvent;
import ink.alf.village.utils.DialogUtils;
import ink.alf.village.view.ILoginView;

/**
 * @author 13793
 */
public class LoginPresenter {

    private Context mContext;
    private ILoginView iLoginView;


    public LoginPresenter(Context mContext, ILoginView iLoginView) {
        this.mContext = mContext;
        this.iLoginView = iLoginView;
    }


    public void wxLogin() {

        DialogUtils.getInstance(mContext).show();

        if (!BaseApplication.iwxapi.isWXAppInstalled()) {
            iLoginView.wxLoginFailed("您还未安装微信客户端", -40011);
            DialogUtils.dimiss();
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        BaseApplication.iwxapi.sendReq(req);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEvent event) {
        DialogUtils.dimiss();
        if (event.getErrCode() != 0) {
            iLoginView.wxLoginFailed(event.getMsg(), event.getErrCode());
            return;
        }
        //拿到code，二次网络请求获取access_token和用户信息
        String code = event.getCode();
        iLoginView.wxLoginSuccess(code);
    }

}

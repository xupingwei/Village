package ink.alf.village.wxapi;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import ink.alf.village.common.MainConstants;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.base.BaseApplication;
import ink.alf.village.bean.MessageEvent;

/**
 * @author 13793
 */
@SuppressLint("Registered")
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    //登录
    private static final int MSG_TYPE_LOGIN = 1;
    //分享
    private static final int MSG_TYPE_SHARE = 2;

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //判断是分享还是登录
        int type = baseResp.getType();
        MessageEvent event = new MessageEvent();
        event.setAction(MainConstants.ACTION_LOGIN);
        switch (baseResp.errCode) {
            //拒绝授权
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                event.setErrCode(BaseResp.ErrCode.ERR_AUTH_DENIED);
                event.setMsg(getString(R.string.errcode_deny));
                break;
            //取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                event.setErrCode(BaseResp.ErrCode.ERR_USER_CANCEL);
                event.setMsg(getString(R.string.errcode_cancel));
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                event.setErrCode(BaseResp.ErrCode.ERR_UNSUPPORT);
                event.setMsg(getString(R.string.errcode_unsupported));
                break;
            //成功
            case BaseResp.ErrCode.ERR_OK:
                event.setErrCode(BaseResp.ErrCode.ERR_OK);
                if (type == MSG_TYPE_LOGIN) {
                    event.setCode(((SendAuth.Resp) baseResp).code);
                } else if (type == MSG_TYPE_SHARE) {
                    //分享成功
                    event.setAction(MainConstants.ACTION_SHARE);
                }

                break;
            default:
                event.setErrCode(-7);
                event.setMsg(getString(R.string.errcode_unknown));
                break;
        }
        EventBus.getDefault().post(event);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_entry;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        BaseApplication.iwxapi.handleIntent(getIntent(), this);
    }
}

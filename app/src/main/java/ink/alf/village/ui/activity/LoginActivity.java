package ink.alf.village.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.presenter.LoginPresenter;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.view.ILoginView;
import ink.alf.village.widget.IconfontTextView;

/**
 * @author 13793
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.iftv_wx)
    public IconfontTextView iftvWx;

    private LoginPresenter loginPresenter;

    private static final String TAG = "LoginActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loginPresenter = new LoginPresenter(this, this);
    }

    @OnClick(R.id.iftv_wx)
    public void onWxLogin(View view) {
        Log.d(TAG, "onWxLogin: 开始微信登录");
        loginPresenter.wxLogin();
    }


    @Override
    public void wxLoginSuccess(String uuid) {
        ToastUtils.showToast(this, uuid);

    }

    @Override
    public void wxLoginFailed(String msg, int code) {
        Log.d(TAG, "wxLoginFailed: 登录失败" + "{code=" + code + ",msg=" + msg + "}");
        ToastUtils.showToast(this, "{code=" + code + ",msg=" + msg + "}");
    }
}

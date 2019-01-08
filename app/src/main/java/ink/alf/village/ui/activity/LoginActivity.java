package ink.alf.village.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.presenter.LoginPresenter;
import ink.alf.village.utils.BlurUtils;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.view.ILoginView;
import ink.alf.village.widget.IconfontTextView;

/**
 * @author 13793
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.iftv_wx)
    public IconfontTextView iftvWx;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;

    private LoginPresenter loginPresenter;

    private static final String TAG = "LoginActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_bg);
        rlLayout.setBackground(new BitmapDrawable(BlurUtils.doBlur(bitmap, 10, 10)));
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

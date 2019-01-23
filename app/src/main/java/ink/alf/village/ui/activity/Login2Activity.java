package ink.alf.village.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import butterknife.BindView;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.bean.vo.UserInfo;
import ink.alf.village.common.MainConstants;
import ink.alf.village.mvp.presenter.UserPresenter;
import ink.alf.village.mvp.view.IUser2View;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.widget.IconfontTextView;

/**
 * @author 13793
 */
public class Login2Activity extends BaseActivity implements IUser2View {
    @BindView(R.id.it_back_login)
    IconfontTextView itBackLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_pass)
    EditText etUserPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.it_wx_login)
    IconfontTextView itWxLogin;


    private UserPresenter userPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_phone;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userPresenter = new UserPresenter(this, this);
        itBackLogin.setOnClickListener(v -> this.finish());
        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        btnLogin.setOnClickListener(v -> login());
    }

    /**
     * 登录
     */
    private void login() {
        String phone = etUserPhone.getText().toString();
        String pass = etUserPass.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pass)) {
            ToastUtils.showToast(this, "用户名或密码不能为空");
            return;
        }
        userPresenter.loginWithPhone(phone, pass);
    }

    @Override
    public void onSuccess(UserInfo info) {
        preferencesHelper.put(MainConstants.IS_LOGIN, true);
        preferencesHelper.put(MainConstants.TOKEN, info.getToken());
        preferencesHelper.put(MainConstants.USER_ID, info.getId());
        preferencesHelper.put(MainConstants.USER_INFO, JSON.toJSONString(info));
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onFailure(String message, int code) {
        ToastUtils.showToast(this, message);
    }
}

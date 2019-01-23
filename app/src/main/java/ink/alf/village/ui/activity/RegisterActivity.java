package ink.alf.village.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.bean.vo.UserInfo;
import ink.alf.village.mvp.presenter.UserPresenter;
import ink.alf.village.mvp.view.IUser2View;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.widget.IconfontTextView;

/**
 * @author 13793
 */
public class RegisterActivity extends BaseActivity implements IUser2View {
    @BindView(R.id.it_back_register)
    IconfontTextView itBackRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_reg_phone)
    EditText etRegPhone;
    @BindView(R.id.btn_send_code)
    Button btnSendCode;
    @BindView(R.id.et_reg_code)
    EditText etRegCode;
    @BindView(R.id.et_reg_pass)
    EditText etRegPass;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private UserPresenter userPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        userPresenter = new UserPresenter(this, this);
        itBackRegister.setOnClickListener(v -> this.finish());
        tvLogin.setOnClickListener(v -> this.finish());
        btnRegister.setOnClickListener(v -> register());
        btnSendCode.setOnClickListener(v -> etRegCode.setText("10086"));

    }

    /**
     * 注册
     */
    private void register() {
        String phone = etRegPhone.getText().toString();
        String pass = etRegPass.getText().toString();
        String code = etRegCode.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pass)) {
            ToastUtils.showToast(this, "用户名或密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast(this, "请输入验证码");
            return;
        }
        userPresenter.register(phone, pass, code);

    }

    @Override
    public void onSuccess(UserInfo info) {
        ToastUtils.showToast(this, "注册成功");
        this.finish();
    }

    @Override
    public void onFailure(String message, int code) {
        ToastUtils.showToast(this, message);
    }
}

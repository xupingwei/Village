package ink.alf.village.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.widget.IconfontTextView;

/**
 * @author 13793
 */
public class Login2Activity extends BaseActivity {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_phone;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        itBackLogin.setOnClickListener(v -> this.finish());
        tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        btnLogin.setOnClickListener(v -> login());
    }

    /**
     * 登录
     */
    private void login() {
        preferencesHelper.put("login", true);
        preferencesHelper.put("token", "40289ecc67cfcfb10167cfd8ca370000");
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}

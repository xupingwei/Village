package ink.alf.village.ui.activity;

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
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.it_back_register)
    IconfontTextView itBackRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.et_reg_phone)
    EditText etRegPhone;
    @BindView(R.id.et_reg_code)
    EditText etRegCode;
    @BindView(R.id.et_reg_pass)
    EditText etRegPass;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        itBackRegister.setOnClickListener(v -> this.finish());
    }

}

package ink.alf.village.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import ink.alf.village.R;
import ink.alf.village.bean.vo.UserInfo;
import ink.alf.village.utils.SharedPreferencesHelper;


/**
 * @author 13793
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        preferencesHelper = new SharedPreferencesHelper(this);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.themeColor), 0);
        initView(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    protected String getToken() {
        return (String) preferencesHelper.getValueForKey("token", "token");
    }


    protected UserInfo getUserInfo() {
        return new UserInfo("40289ecc67cfcfb10167cfd8ca370000",
                "40289ecc67cfcfb10167cfd8ca370000", "Jone");
    }

    /**
     * 判断是否已经登录
     *
     * @return true：已经登录  false：没有登录
     */
    protected boolean isLogin() {
        return (boolean) preferencesHelper.getValueForKey("login", false);
    }

    /**
     * 获取布局的Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     *
     * @param savedInstanceState
     */

    protected abstract void initView(Bundle savedInstanceState);

}

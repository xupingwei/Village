package ink.alf.village.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import ink.alf.village.R;
import ink.alf.village.bean.vo.UserInfo;


/**
 * @author 13793
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.themeColor), 0);
        initView(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    protected String getToken() {
        return "40289ecc67cfcfb10167cfd8ca370000";
    }


    protected UserInfo getUserInfo() {
        return new UserInfo("40289ecc67cfcfb10167cfd8ca370000",
                "40289ecc67cfcfb10167cfd8ca370000", "Jone");
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

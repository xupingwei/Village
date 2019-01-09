package ink.alf.village.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import ink.alf.village.R;


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


    private String getToken() {
        return "";
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

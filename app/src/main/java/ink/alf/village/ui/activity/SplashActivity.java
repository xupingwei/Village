package ink.alf.village.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;

/**
 * @author 13793
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        new Handler().postDelayed(() -> {
            if (isLogin()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, Login2Activity.class));
            }
            SplashActivity.this.finish();
        }, 2000);

    }
}

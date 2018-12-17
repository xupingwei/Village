package ink.alf.village;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.ui.ViewPagerAdapter;
import ink.alf.village.ui.fragment.FragmentHome;
import ink.alf.village.ui.fragment.FragmentMe;
import ink.alf.village.ui.fragment.FragmentNearby;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.widget.CustomViewPager;

/**
 * @author 13793
 */
@SuppressLint("Registered")
public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.bottomNavigationView)
    public BottomNavigationView navigationView;
    @BindView(R.id.viewpager)
    public CustomViewPager viewPager;

    private MenuItem menuItem;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        navigationView.setOnNavigationItemSelectedListener(this);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setScanScroll(false);
        List<Fragment> list = new ArrayList<>();
        list.add(new FragmentHome());
        list.add(new FragmentNearby());
        list.add(new FragmentMe());
        viewPagerAdapter.setList(list);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        menuItem = item;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_publish:
                ToastUtils.showToast(this, "发帖");
                return false;
            case R.id.navigation_nearby:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_me:
                viewPager.setCurrentItem(2);
                return true;

        }
        return false;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

        Log.i(TAG, "onPageScrolled: i = " + i);
    }

    @Override
    public void onPageSelected(int i) {
        Log.d(TAG, "onPageSelected: i = " + 1);
//        if (i == 1) {
//            return;
//        }
//        if (null != menuItem) {
//            menuItem.setChecked(false);
//        } else {
//            navigationView.getMenu().getItem(0).setChecked(false);
//        }
//
//        menuItem = navigationView.getMenu().getItem(i);
//        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        Log.e(TAG, "onPageScrollStateChanged: " + i);
    }
}
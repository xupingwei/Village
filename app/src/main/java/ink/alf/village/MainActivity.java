package ink.alf.village;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.ui.ViewPagerAdapter;
import ink.alf.village.ui.fragment.TestFragment;

/**
 * @author 13793
 */
@SuppressLint("Registered")
public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.bottomNavigationView)
    public BottomNavigationView navigationView;
    @BindView(R.id.viewpager)
    public ViewPager viewPager;

    private MenuItem menuItem;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        navigationView.setOnNavigationItemSelectedListener(this);
//        BottomNavigationViewHelper.disableShiftMode(navigationView);

        //
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        list.add(TestFragment.newInstance("首页"));
        list.add(TestFragment.newInstance("发帖"));
        list.add(TestFragment.newInstance("附近"));
        list.add(TestFragment.newInstance("我"));
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
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_nearby:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.navigation_me:
                viewPager.setCurrentItem(3);
                return true;

        }
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

        if (null != menuItem) {
            menuItem.setChecked(false);
        } else {
            navigationView.getMenu().getItem(0).setChecked(false);
        }

        menuItem = navigationView.getMenu().getItem(i);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

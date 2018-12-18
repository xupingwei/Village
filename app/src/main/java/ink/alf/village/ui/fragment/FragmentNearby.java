package ink.alf.village.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.MainConstants;
import ink.alf.village.R;
import ink.alf.village.ui.NearbyPagerAdapter;

/**
 * @author 13793
 */
@SuppressLint("ValidFragment")
public class FragmentNearby extends Fragment {

    private static final String TAG = "FragmentNearby";


    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.nearby_viewpager)
    ViewPager tabViewPager;

    private Unbinder unbinder;

    private Map<String, String> tabs = MainConstants.TABS;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (Map.Entry<String, String> entry : tabs.entrySet()) {
            titles.add(entry.getValue());
            fragments.add(FragmentNearbyContent.newInstance(entry.getKey()));
        }
        tabViewPager.setAdapter(new NearbyPagerAdapter(getFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(tabViewPager);
    }

    @Override
    public void onDestroyView() {
        titles.clear();
        fragments.clear();
        super.onDestroyView();
        unbinder.unbind();
    }
}
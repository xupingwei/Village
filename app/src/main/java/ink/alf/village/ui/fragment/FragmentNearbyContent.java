package ink.alf.village.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ink.alf.village.R;

/**
 * @author 13793
 */
public class FragmentNearbyContent extends Fragment {

    private static final String TAG = "FragmentNearbyContent";
    private Unbinder unbinder;
    @BindView(R.id.tv_text)
    TextView tv;

    private String reqKey = "";

    public static FragmentNearbyContent newInstance(String reqKey) {
        Bundle args = new Bundle();
        args.putString("reqKey", reqKey);
        FragmentNearbyContent fragment = new FragmentNearbyContent();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            reqKey = bundle.getString("reqKey");
        }
        Log.d(TAG, "onViewCreated: reqKey = " + reqKey);
        tv.setText(reqKey);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

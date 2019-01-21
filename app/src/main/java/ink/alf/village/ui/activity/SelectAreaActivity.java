package ink.alf.village.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.common.MainConstants;
import ink.alf.village.ui.AreaAdapter;
import ink.alf.village.utils.ToastUtils;

/**
 * @author 13793
 */
public class SelectAreaActivity extends BaseActivity {

    @BindView(R.id.iv_area_back)
    ImageView ivBack;
    @BindView(R.id.btn_location_city)
    Button btnLocationCity;
    @BindView(R.id.tv_refresh_location)
    TextView tvRefreshLocation;
    @BindView(R.id.gv_area)
    GridView gvArea;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivBack.setOnClickListener(v -> this.finish());
        tvRefreshLocation.setOnClickListener(v -> ToastUtils.showToast(this, "重新定位"));
//        gvArea.setAdapter(new AreaAdapter(this, MainConstants.CITIES));
//        gvArea.setOnItemClickListener((parent, view, position, id) ->
//                ToastUtils.showToast(this, MainConstants.CITIES[position]));
    }

}

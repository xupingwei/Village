package ink.alf.village.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.List;

import butterknife.BindView;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.bean.Region;
import ink.alf.village.mvp.presenter.SelectAreaPresenter;
import ink.alf.village.mvp.view.ISelectAreaView;
import ink.alf.village.ui.AreaAdapter;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.widget.IconfontTextView;

/**
 * @author 13793
 */
public class SelectAreaActivity extends BaseActivity implements ISelectAreaView {

    @BindView(R.id.it_back)
    IconfontTextView itBack;
    @BindView(R.id.btn_location_city)
    Button btnLocationCity;
    @BindView(R.id.tv_refresh_location)
    TextView tvRefreshLocation;
    @BindView(R.id.gv_area)
    GridView gvArea;

    //
    private SelectAreaPresenter selectAreaPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        itBack.setOnClickListener(v -> this.finish());
        //
        selectAreaPresenter = new SelectAreaPresenter(this, this);
        tvRefreshLocation.setOnClickListener(v -> selectAreaPresenter.refreshLocation());
        //开始请求
        selectAreaPresenter.findAllRegions();

    }

    @Override
    public void loadRegionsSuccess(List<Region> regions) {
        gvArea.setAdapter(new AreaAdapter(this, regions));
        gvArea.setOnItemClickListener((parent, view, position, id) ->
                ToastUtils.showToast(SelectAreaActivity.this, regions.get(position).getDistrict()));

    }

    @Override
    public void loadRegionsFailure(String msg, int code) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void refreshLocationSuccess(BDLocation location) {
        btnLocationCity.setText(location.getDistrict());
    }

    @Override
    public void refreshLocationFailure(String msg, int code) {

    }
}

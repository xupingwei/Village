package ink.alf.village.mvp.view;

import com.baidu.location.BDLocation;

import java.util.List;

import ink.alf.village.bean.Region;

public interface ISelectAreaView {
    void loadRegionsSuccess(List<Region> regions);

    void loadRegionsFailure(String msg, int code);

    void refreshLocationSuccess(BDLocation location);

    void refreshLocationFailure(String msg, int code);
}

package ink.alf.village.mvp.view;

import com.baidu.location.BDLocation;

public interface IHomeView {

    void locationSuccess(BDLocation location);

    void locationFailed(String msg, int code);
}

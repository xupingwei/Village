package ink.alf.village.view;

import com.baidu.location.BDLocation;

import java.util.List;

import ink.alf.village.bean.ActivitiBean;

public interface IHomeView {

    void loadMainDataSuccess(List<ActivitiBean> activitiBeans);

    void loadMainDataFailed(String msg, int code);

    void locationSuccess(BDLocation location);

    void locationFailed(String msg, int code);
}

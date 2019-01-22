package ink.alf.village.mvp.view;

import ink.alf.village.bean.vo.ActivitiPagerInfo;

public interface IContentView {

    void loadMainDataSuccess(ActivitiPagerInfo activitiBeans);

    void loadMainDataFailed(String msg, int code);

    void followSuccess(String msg);

    void collectSuccess(String msg);
}

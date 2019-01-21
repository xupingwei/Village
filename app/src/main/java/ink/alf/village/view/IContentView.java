package ink.alf.village.view;

import ink.alf.village.bean.vo.ActivitiPagerInfo;

public interface IContentView {

    void loadMainDataSuccess(ActivitiPagerInfo activitiBeans);

    void loadMainDataFailed(String msg, int code);
}

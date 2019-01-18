package ink.alf.village.view;

import java.util.List;

import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.bean.ActivitiPagerInfo;

public interface IContentView {

    void loadMainDataSuccess(ActivitiPagerInfo activitiBeans);

    void loadMainDataFailed(String msg, int code);
}

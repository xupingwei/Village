package ink.alf.village.view;

import java.util.List;

import ink.alf.village.bean.ActivitiBean;

public interface IContentView {

    void loadMainDataSuccess(List<ActivitiBean> activitiBeans);

    void loadMainDataFailed(String msg, int code);
}

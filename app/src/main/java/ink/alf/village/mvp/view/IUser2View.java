package ink.alf.village.mvp.view;

import ink.alf.village.bean.vo.UserInfo;

public interface IUser2View {

    void onSuccess(UserInfo info);

    void onFailure(String message, int code);
}

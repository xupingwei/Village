package ink.alf.village.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.fastjson.JSON;

import ink.alf.village.bean.vo.UserInfo;
import ink.alf.village.common.MainConstants;
import ink.alf.village.utils.SharedPreferencesHelper;

/**
 * @author 13793
 */
public class BaseFragment extends Fragment {

    protected static SharedPreferencesHelper preferencesHelper;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferencesHelper = new SharedPreferencesHelper(getActivity());
    }

    protected String getToken() {
        return (String) preferencesHelper.getValueForKey(MainConstants.TOKEN, "token");
    }


    protected UserInfo getUserInfo() {
        return JSON.parseObject((String) preferencesHelper.getValueForKey(MainConstants.USER_INFO,
                ""), UserInfo.class);
    }

}

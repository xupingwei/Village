package ink.alf.village.base;

import android.support.v4.app.Fragment;

import ink.alf.village.bean.vo.UserInfo;

/**
 * @author 13793
 */
public class BaseFragment extends Fragment {

    protected String getToken() {
        return "40289ecc67cfcfb10167cfd8ca370000";
    }

    protected UserInfo getUserInfo() {
        return new UserInfo("40289ecc67cfcfb10167cfd8ca370000",
                "40289ecc67cfcfb10167cfd8ca370000", "Jone");
    }
}

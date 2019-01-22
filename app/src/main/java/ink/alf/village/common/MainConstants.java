package ink.alf.village.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 13793
 */
public class MainConstants {

    public static final String WX_APPID = "";
    public static final String WX_SECRET = "";
    public static final String ACTION_LOGIN = "action_login";
    public static final String ACTION_SHARE = "action_share";


    public static Map<String, String> TABS = new HashMap<>();

    static {
        TABS.put("RECOMMEND", "推荐");
        TABS.put("NEW", "最新");
        TABS.put("VEHICLE", "交通工具");
        TABS.put("PERSON", "村里人");
        TABS.put("THING", "村里事");
    }
}

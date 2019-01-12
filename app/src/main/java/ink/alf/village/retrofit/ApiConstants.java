package ink.alf.village.retrofit;


import ink.alf.village.BuildConfig;

/**
 * @author 13793
 */
public class ApiConstants {

    //dev
    private final static String DEV_BASE_URL = "http://192.168.31.84:8080";

    //release
    private final static String REL_BASE_URL = "http://192.168.31.84:8080";


    public static final String BASR_URL;


    static {
        BASR_URL = BuildConfig.DEBUG ? DEV_BASE_URL : REL_BASE_URL;
    }
}

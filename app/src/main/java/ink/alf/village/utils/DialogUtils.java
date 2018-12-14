package ink.alf.village.utils;

import android.content.Context;

import com.android.tu.loadingdialog.LoadingDailog;

public class DialogUtils {


    private static LoadingDailog loadingDailog;

    private DialogUtils() {
    }

    public static synchronized LoadingDailog getInstance(Context context) {

        if (loadingDailog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(context)
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(true);
            loadingDailog = loadBuilder.create();
        }
        return loadingDailog;
    }

    public static void dimiss() {
        if (null != loadingDailog) {
            loadingDailog.dismiss();
        }
    }
}

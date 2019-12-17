package ink.alf.village.utils;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogUtils {


    private static SweetAlertDialog sweetAlertDialog;

    private DialogUtils() {
    }

    public static void dimiss() {
        if (null != sweetAlertDialog || sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
    }

    public static void showLoadingDialog(Context context,String titleText) {

        if (null == sweetAlertDialog) {
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            sweetAlertDialog.setTitleText(titleText);
            sweetAlertDialog.setCancelable(false);
        }
        if (!sweetAlertDialog.isShowing()) {
            sweetAlertDialog.show();
        }
    }
}

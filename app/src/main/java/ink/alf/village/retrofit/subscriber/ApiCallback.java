/**
 * Copyright (C), 2014-2018, 大连华信计算机技术股份有限公司
 * FileName: ApiCallback
 * Author: 152843
 * Date: 2018/12/3 16:50
 * Description: 返回值处理
 * History:
 */
package ink.alf.village.retrofit.subscriber;


import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.ConnectException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ink.alf.village.retrofit.ResultBean;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @ClassName: ApiCallback
 * @Description: java类作用描述
 * @Author: 152843
 * @Date: 2018/12/3 16:50
 */
public abstract class ApiCallback implements Observer<ResponseBody> {
    private static final String TAG = "ApiCallback";

    protected Context mContext;
    private SweetAlertDialog dialog;
    private String msg;
    private boolean isShow = false;
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int BAD_REQUEST = 400;
    private static final int PARSE_ERROR_CODE = 103;

    //
    private static final String REQUEST_ERROR = "请求异常";
    private static final String CONNECT_ERROR = "网络连接异常";
    private static final String UNKNOWN_ERROR = "未知异常";
    private static final String PARSE_ERROR = "解析异常";

    public ApiCallback(Context mContext, String msg, boolean isShow) {
        this.mContext = mContext;
        this.isShow = isShow;
        this.msg = msg;
    }

    public ApiCallback(Context context) {
        this(context, "正在加载", true);
    }

    @Override
    public void onSubscribe(final Disposable d) {
        if (isShow) {
            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            //点击取消的时候取消订阅
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //取消订阅
                    d.dispose();
                }
            });
            dialog.show();
        }
    }


    @Override
    public void onNext(ResponseBody r) {
        if (isShow) {
            dialog.dismiss();
        }
        try {
            String body = r.string();
            Log.i(TAG, "onNext: body = " + body);
            ResultBean bean = JSON.parseObject(body, ResultBean.class);
            if (bean.success()) {
                onSuccess(bean.getData());
            } else {
                onFailure(bean.getCode(), bean.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(PARSE_ERROR_CODE, PARSE_ERROR);
        }

    }

    @Override
    public void onError(Throwable t) {
        Log.e(TAG, "onError: ", t);
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            int code = httpException.code();
            String msg = httpException.getMessage();
            Log.i(TAG, "onError: " + msg);
            switch (code) {
                case UNAUTHORIZED:
                    onFailure(UNAUTHORIZED, msg);
                    break;
                case FORBIDDEN:
                    onFailure(FORBIDDEN, msg);
                    break;
                case NOT_FOUND:
                    onFailure(NOT_FOUND, msg);
                    break;
                case REQUEST_TIMEOUT:
                    onFailure(REQUEST_TIMEOUT, msg);
                    break;
                case GATEWAY_TIMEOUT:
                    onFailure(GATEWAY_TIMEOUT, msg);
                    break;
                case INTERNAL_SERVER_ERROR:
                    onFailure(INTERNAL_SERVER_ERROR, msg);
                    break;
                case BAD_GATEWAY:
                    onFailure(BAD_GATEWAY, msg);
                    break;
                case SERVICE_UNAVAILABLE:
                    onFailure(SERVICE_UNAVAILABLE, msg);
                    break;
                case BAD_REQUEST:
                    onFailure(BAD_REQUEST, msg);
                    break;
                default:
                    msg = REQUEST_ERROR;
                    onFailure(100, msg);
                    break;
            }

        } else if (t instanceof ConnectException) {
            msg = CONNECT_ERROR;
            onFailure(101, msg);
        } else {
            msg = UNKNOWN_ERROR;
            onFailure(102, msg);
        }
        if (isShow) {
            dialog.dismiss();
        }
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete: done");
    }

    public abstract void onSuccess(String data);

    public abstract void onFailure(int errorCode, String msg);
}

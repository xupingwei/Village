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
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.ConnectException;

import ink.alf.village.retrofit.ResultBean;
import ink.alf.village.utils.DialogUtils;
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
    private String errorMsg;
    private boolean isShow = true;
    private String titleText;
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
    //
    private static final String REQUEST_ERROR = "request failed";
    private static final int REQUEST_ERROR_CODE = 100;
    //
    private static final String CONNECT_ERROR = "connet failed";
    private static final int CONNECT_ERROR_CODE = 101;
    //
    private static final String PARSE_ERROR = "parse failed";
    private static final int PARSE_ERROR_CODE = 102;
    //
    private static final String UNKNOWN_ERROR = "disconnect failed";
    private static final int UNKNOWN_ERROR_CODE = 103;

    public ApiCallback(Context mContext, String titleText, boolean isShow) {
        this.mContext = mContext;
        this.isShow = isShow;
        this.titleText = titleText;
    }

    public ApiCallback(Context context) {
        this(context, "Loading...", true);
    }

    public ApiCallback(Context context, boolean isShow) {
        this(context, "Loading...", isShow);
    }

    @Override
    public void onSubscribe(final Disposable d) {
        if (isShow) {
            DialogUtils.showLoadingDialog(mContext,titleText);
        }
    }


    @Override
    public void onNext(ResponseBody r) {
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
            Log.i(TAG, "onNext: json parse error.");
            onFailure(PARSE_ERROR_CODE, PARSE_ERROR);
        }

    }

    @Override
    public void onError(Throwable t) {
        Log.e(TAG, "onError: ", t);
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            int code = httpException.code();
            errorMsg = httpException.getMessage();
            Log.i(TAG, "onError: " + errorMsg);
            switch (code) {
                case UNAUTHORIZED:
                    onFailure(UNAUTHORIZED, errorMsg);
                    break;
                case FORBIDDEN:
                    onFailure(FORBIDDEN, errorMsg);
                    break;
                case NOT_FOUND:
                    onFailure(NOT_FOUND, errorMsg);
                    break;
                case REQUEST_TIMEOUT:
                    onFailure(REQUEST_TIMEOUT, errorMsg);
                    break;
                case GATEWAY_TIMEOUT:
                    onFailure(GATEWAY_TIMEOUT, errorMsg);
                    break;
                case INTERNAL_SERVER_ERROR:
                    onFailure(INTERNAL_SERVER_ERROR, errorMsg);
                    break;
                case BAD_GATEWAY:
                    onFailure(BAD_GATEWAY, errorMsg);
                    break;
                case SERVICE_UNAVAILABLE:
                    onFailure(SERVICE_UNAVAILABLE, errorMsg);
                    break;
                case BAD_REQUEST:
                    onFailure(BAD_REQUEST, errorMsg);
                    break;
                default:
                    errorMsg = REQUEST_ERROR;
                    onFailure(REQUEST_ERROR_CODE, errorMsg);
                    break;
            }

        } else if (t instanceof ConnectException) {
            errorMsg = CONNECT_ERROR;
            onFailure(CONNECT_ERROR_CODE, errorMsg);
        } else {
            errorMsg = UNKNOWN_ERROR;
            onFailure(UNKNOWN_ERROR_CODE, errorMsg);
        }
        //关闭对话框
        DialogUtils.dimiss();
    }

    @Override
    public void onComplete() {
        if (isShow) DialogUtils.dimiss();
        Log.i(TAG, "onComplete: done");
    }

    public abstract void onSuccess(String data);

    public abstract void onFailure(int errorCode, String errorMsg);
}

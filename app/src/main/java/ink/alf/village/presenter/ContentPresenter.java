package ink.alf.village.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.view.IContentView;

/**
 * @author 13793
 */
public class ContentPresenter {

    private Context mContext;
    private IContentView iContentView;

    public ContentPresenter(Context mContext, IContentView iContentView) {
        this.mContext = mContext;
        this.iContentView = iContentView;
    }


    public void loadMainData() {

        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    List<ActivitiBean> beans = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        ActivitiBean bean = new ActivitiBean();
                        bean.setPushName("张三" + i);
                        beans.add(bean);
                    }
                    iContentView.loadMainDataSuccess(beans);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(5000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }
}

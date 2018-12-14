/**
 * Copyright (C), 2014-2018, 大连华信计算机技术股份有限公司
 * FileName: SchedulersCompat
 * Author: 152843
 * Date: 2018/12/3 16:13
 * Description: 管理scheduler线程
 * History:
 */
package ink.alf.village.retrofit.subscriber;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: SchedulersCompat
 * @Description: 管理scheduler线程
 * @Author: 152843
 * @Date: 2018/12/3 16:13
 */
public class SchedulersCompat {

    private static final ObservableTransformer ioTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    };

    public static <T> ObservableTransformer<T, T> applyIoSchedulers() {
        return ioTransformer;
    }

}

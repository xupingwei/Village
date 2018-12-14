package ink.alf.village.retrofit;

import android.util.Log;


import java.util.concurrent.TimeUnit;

import ink.alf.village.retrofit.converter.FastJsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author 152843
 */
public class RetrofitClient {
    /**
     * 超时时间5s
     */
    private static final int DEFAULT_TIME_OUT = 10;
    /**
     * 读取时间
     */
    private static final int DEFAULT_READ_TIME_OUT = 10;
    /**
     * 读取时间
     */
    private static final int DEFAULT_WRITE_TIME_OUT = 10;

    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("RxJava----------", message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    //失败重连
                    .retryOnConnectionFailure(true)
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(ApiConstants.BASR_URL)
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}

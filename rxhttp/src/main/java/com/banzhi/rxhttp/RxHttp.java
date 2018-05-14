package com.banzhi.rxhttp;

import android.os.Environment;

import com.banzhi.rxhttp.interceptor.CacheInterceptor;
import com.banzhi.rxhttp.interceptor.RequestInterceptor;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/14.
 * @desciption :
 * @version :
 * </pre>
 */

public class RxHttp {

    private static String mBaseUrl;
    private static Retrofit.Builder retrofitBuilder;
    private static Map<String, Object> mHeaderMaps = new TreeMap<>();

    private volatile static RxHttp mRxHttp = null;

    private RxHttp() {
    }

    public static RxHttp getInstance() {
        mBaseUrl = "";
        if (mRxHttp == null) {
            synchronized (RxHttp.class) {
                if (mRxHttp == null) {
                    mRxHttp = new RxHttp();
                }
            }
        }
        return mRxHttp;
    }

    public static RxHttp getInstance(String baseUrl) {
        mBaseUrl = baseUrl;
        mHeaderMaps.clear();
        if (mRxHttp == null) {
            synchronized (RxHttp.class) {
                if (mRxHttp == null) {
                    mRxHttp = new RxHttp();
                }
            }
        }
        return mRxHttp;
    }

    public RxHttp addHeader(Map<String, Object> headerMaps) {
        mHeaderMaps = headerMaps;
        return mRxHttp;
    }

    private static HttpLoggingInterceptor loggingInterceptor;

    private static RequestInterceptor requestInterceptor;

    private static CacheInterceptor cacheInterceptor;
    private static Cache cache;

    public static Retrofit getRetrofit() {
        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            cacheInterceptor = new CacheInterceptor();
        }
        if (mHeaderMaps != null) {
            requestInterceptor = new RequestInterceptor(mHeaderMaps);
        } else {
            requestInterceptor = new RequestInterceptor();
        }

        retrofitBuilder.baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create()).client(getClient());

        return retrofitBuilder.build();
    }
    /**
     * 初始化okHttpClient
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        if (cache==null){
            cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath() + "/rxHttpCacheData"), 1024 * 1024 * 100);
        }

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }
}

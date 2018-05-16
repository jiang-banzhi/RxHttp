package com.banzhi.rxhttp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.banzhi.rxhttp.interceptor.CacheInterceptor;
import com.banzhi.rxhttp.interceptor.RequestInterceptor;
import com.banzhi.rxhttp.interceptor.RetryInterceptor;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    private static final long DEF_CACHE_SIZE = 1024 * 1024 * 100;
    private static Context mContext;
    private static String mBaseUrl;
    private static Map<String, Object> mHeaderMaps = new TreeMap<>();
    private volatile static RxHttp mRxHttp = null;

    private Retrofit.Builder retrofitBuilder;

    private HttpLoggingInterceptor loggingInterceptor;
    private RequestInterceptor requestInterceptor;
    private CacheInterceptor cacheInterceptor;
    private RetryInterceptor retryInterceptor;
    int maxRetry;

    private Cache cache;
    private String cachePath = Environment.getExternalStorageDirectory().getPath() + "/rxHttpCacheData";
    long cacheSize = DEF_CACHE_SIZE;

    private long mTimeOut;
    private TimeUnit mTimeUnit = TimeUnit.SECONDS;
    private static Retrofit retrofit;

    private RxHttp(Context context) {
        mContext = context;
    }


    public static RxHttp getInstance(Context context, String baseUrl) {
        mBaseUrl = baseUrl;
        mHeaderMaps.clear();
        if (mRxHttp == null) {
            synchronized (RxHttp.class) {
                if (mRxHttp == null) {
                    mRxHttp = new RxHttp(context);
                }
            }
        }
        return mRxHttp;
    }

    /**
     * 添加头信息
     *
     * @param headerMaps
     * @return
     */
    public RxHttp addHeader(Map<String, Object> headerMaps) {
        mHeaderMaps = headerMaps;
        return mRxHttp;
    }


    /**
     * 设置超时时间 默认秒
     *
     * @param timeout 超时时间
     * @return
     */
    public RxHttp setTimeout(long timeout) {
        return setTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置超时时间 默认秒
     *
     * @param timeout  超时时间
     * @param timeUnit 时间格式
     * @return
     */
    public RxHttp setTimeout(long timeout, TimeUnit timeUnit) {
        this.mTimeOut = timeout;
        mTimeUnit = timeUnit;
        return mRxHttp;
    }

    /**
     * 设置缓存
     *
     * @param cachePath 缓存路径
     * @param cacheSize 缓存大小
     * @return
     */
    public RxHttp setCache(String cachePath, long cacheSize) {
        this.cachePath = cachePath;
        this.cacheSize = cacheSize < 0 ? DEF_CACHE_SIZE : cacheSize;
        return mRxHttp;
    }

    public RxHttp setCache(Cache cache) {
        this.cache = cache;
        return mRxHttp;
    }

    /**
     * 设置最大重试次数
     *
     * @param maxRetry
     */
    public void setRetryCount(int maxRetry) {
        this.maxRetry = maxRetry < 0 ? 0 : maxRetry;
    }


    private Retrofit createRetrofit() {
        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            cacheInterceptor = new CacheInterceptor(mContext);
            retryInterceptor = new RetryInterceptor(maxRetry);
        }
        if (mHeaderMaps != null) {
            requestInterceptor = new RequestInterceptor(mContext, mHeaderMaps);
        } else {
            requestInterceptor = new RequestInterceptor(mContext);
        }

        retrofitBuilder.baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create()).client(getClient());
        retrofit = retrofitBuilder.build();
        Log.i("result", retrofit.baseUrl().toString());
        return retrofit;
    }

    /**
     * 创建retrofit
     *
     * @return true 成功  false 失败
     */
    public boolean create() {
        return createRetrofit() != null;
    }

    public static void updateBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        retrofit= retrofit.newBuilder().baseUrl(mBaseUrl).build();
        Log.i("result", retrofit.baseUrl().toString());
    }

    /**
     * @return
     */
    public static Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 初始化okHttpClient
     *
     * @return OkHttpClient
     */
    public OkHttpClient getClient() {
        if (cache == null) {
            cache = new Cache(new File(cachePath), cacheSize);
        }

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(requestInterceptor)
                .addInterceptor(retryInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .readTimeout(mTimeOut, mTimeUnit)
                .writeTimeout(mTimeOut, mTimeUnit)
                .connectTimeout(mTimeOut, mTimeUnit)
                .build();

        return httpClient;
    }

    public static <T> T getService(Class<T> clz) {
        if (retrofit == null) {
            throw new NullPointerException("You need to initialize retrofit in application");
        }
        return retrofit.create(clz);
    }
}

package com.banzhi.rxhttp.interceptor;

import android.util.Log;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/15.
 * @desciption :请求重试拦截器
 * @version :
 * </pre>
 */

public class RetryInterceptor implements Interceptor{
    /**
     * 最大重试次数
     */
    public int maxRetry;
    /**假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）*/
    private int retryNum = 0;
    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.i("Retry","num:"+retryNum);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            Log.i("Retry","num:"+retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}

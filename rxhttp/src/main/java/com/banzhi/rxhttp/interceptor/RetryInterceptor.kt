package com.banzhi.rxhttp.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/15.
 * @desciption :请求重试拦截器
 * @version :
</pre> *
 */
class RetryInterceptor : Interceptor {
    /**
     * 最大重试次数
     */
    private var maxRetry: Int = 2

    /**
     * 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
     */
    private var retryNum = 0

    constructor(maxRetry: Int) {
        this.maxRetry = maxRetry
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response: Response = chain.proceed(request)
        Log.i("Retry", "num:$retryNum")
        while (!response.isSuccessful && retryNum < maxRetry) {
            retryNum++
            Log.i("Retry", "num:$retryNum")
            response = chain.proceed(request)
        }
        return response
    }
}
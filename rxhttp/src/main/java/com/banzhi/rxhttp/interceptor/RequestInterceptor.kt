package com.banzhi.rxhttp.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

/**
 * <pre>
 * @author :jy
 * @time : 2021/5/21.
 * @desciption :请求拦截器 便于统一添加header
 * @version :
</pre> *
 */
class RequestInterceptor(private val headerMaps: Map<String, String>?) : Interceptor {



    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request.Builder = chain.request().newBuilder()
        if (headerMaps.isNullOrEmpty()) {
            request
                .addHeader("Content-type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Terminal", "$0")
                .addHeader("User-Agent", System.getProperty("http.agent"))
        } else {
            for ((key, value) in headerMaps) {
                request.addHeader(key, value)
            }
        }
        return chain.proceed(request.build())
    }

}
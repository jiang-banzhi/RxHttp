package com.banzhi.rxhttp.interceptor

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/14.
 * @desciption : 缓存拦截器
 * @version :
</pre> *
 */
class CacheInterceptor(var mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        //如果没有网络，则启用 FORCE_CACHE
        if (!isNetworkConnected(mContext)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val originalResponse: Response = chain.proceed(request)
        return if (isNetworkConnected(mContext)) {
            //有网的时候读接口上的@Headers里的配置
            val cacheControl = request.cacheControl.toString()
            originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build()
        } else {
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                .removeHeader("Pragma")
                .build()
        }
    }

    companion object {
        /**
         * 判断是否有网络
         *
         * @return 返回值
         */
        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable
                }
            }
            return false
        }
    }
}
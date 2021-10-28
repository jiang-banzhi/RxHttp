package com.banzhi.rxhttpsample

import android.app.Application
import android.content.Context
import com.banzhi.rxhttp.RxHttp.Companion.getInstance
import com.banzhi.rxhttp.interceptor.CacheInterceptor
import com.banzhi.rxhttp.interceptor.RequestInterceptor
import com.banzhi.rxhttp.interceptor.RetryInterceptor
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import java.util.List
import java.util.concurrent.TimeUnit

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/16.
 * @desciption :
 * @version :
</pre> *
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        getInstance().baseUrl("https://github.com/jiang-banzhi/")
//            .create(60, externalCacheDir, null, arrayListOf(TokenInterecptor()))
        getInstance().baseUrl("https://github.com/jiang-banzhi/")
            .create {
                retryOnConnectionFailure(true)
                addInterceptor(TokenInterecptor())
                addInterceptor(CacheInterceptor(this@MyApplication.applicationContext))
                addInterceptor(RequestInterceptor(HashMap<String, String>()))
                addInterceptor(RetryInterceptor(3))
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logInterceptor)
                externalCacheDir?.let { cache(Cache(it, 1024 * 1024 * 100)) }
                readTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
            }
    }
}
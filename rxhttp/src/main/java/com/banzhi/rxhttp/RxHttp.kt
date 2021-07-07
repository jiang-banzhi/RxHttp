package com.banzhi.rxhttp

import android.annotation.SuppressLint
import android.content.Context
import com.banzhi.rxhttp.interceptor.RequestInterceptor
import kotlinx.coroutines.GlobalScope
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.Serializable
import java.util.concurrent.TimeUnit

/**
 *<pre>
 * @author : jiang
 * @time : 2021/5/20.
 * @desciption :
 * @version :
 *</pre>
 */
class RxHttp private constructor() : Serializable {

    companion object {
        fun getInstance() = Inner.INSTANCE

    }

    private object Inner {
        @SuppressLint("StaticFieldLeak")
        val INSTANCE: RxHttp = RxHttp()
    }

    private val DEF_CACHE_SIZE: Long = 1024 * 1024 * 100
    private var mRetrofit: Retrofit? = null
    private var BASEURL: String = ""

    fun baseUrl(baseUrl: String): RxHttp {
        BASEURL = baseUrl

        return this
    }

    fun create(builder: OkHttpClient.Builder.() -> Unit) {
        if (BASEURL.isEmpty()) {
            throw UninitializedPropertyAccessException("未设置baseurl")
        }
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().also(builder).build()).build()
    }

    fun create(
        timeOut: Long,
        cacheFile: File? = null,
        headers: Map<String, String>? = null,
        interceptors: MutableList<Interceptor>? = null
    ) {
        create {
            retryOnConnectionFailure(true)
            interceptors?.forEach {
                addInterceptor(it)
            }
            addInterceptor(RequestInterceptor(headers))
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logInterceptor)
            cacheFile?.let {
                cache(Cache(it, DEF_CACHE_SIZE))
            }
            readTimeout(timeOut, TimeUnit.SECONDS)
            writeTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
        }

    }

    fun updateBaseUrl(baseUrl: String) {
        BASEURL = baseUrl
        mRetrofit?.let {
            mRetrofit = it.newBuilder().baseUrl(BASEURL).build()
        }
    }

    fun getRetrofit(): Retrofit {
        return mRetrofit!!
    }

    fun <T> getService(clz: Class<T>?): T {
        if (mRetrofit == null) {
            throw NullPointerException("需要在application中调用create()创建retrofit对象")
        }
        return mRetrofit!!.create(clz)
    }


}
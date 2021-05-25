package com.banzhi.rxhttpsample

import android.app.Application
import android.content.Context
import com.banzhi.rxhttp.RxHttp.Companion.getInstance
import java.util.List

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
        getInstance().baseUrl("https://github.com/jiang-banzhi/")
            .create(60, externalCacheDir, null, arrayListOf(TokenInterecptor()))
    }
}
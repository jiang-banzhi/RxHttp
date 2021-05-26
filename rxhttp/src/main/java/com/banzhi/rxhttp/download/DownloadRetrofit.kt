package com.banzhi.rxhttp.download

import com.banzhi.rxhttp.RxHttp
import com.banzhi.rxhttp.utils.RxHelper
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call

/**
 * <pre>
 * @author :
 * @time : 2021/5/25.
 * @desciption :
 * @version :
</pre> *
 */
class DownloadRetrofit {
    companion object {
        /**
         * 文件下载 RxJava方式
         *
         * @param url 文件路径
         * @return
         */
        @JvmStatic
        fun downloadFile(url: String?): Observable<ResponseBody> {
            return getApiService()
                .downloadByRx(url)
                .compose(RxHelper.switchSchedulers())
        }

        fun downloadByRx(url: String?): Call<ResponseBody> {
            return getApiService()
                .downloadByCall(url)
        }

        suspend fun downloadByCoroutine(url: String?): ResponseBody {
            return getApiService().downloadByCoroutine(url)
        }

        private fun getApiService(): DownloadApiAddress {
            return RxHttp.getInstance().getService(DownloadApiAddress::class.java)
        }
    }
}
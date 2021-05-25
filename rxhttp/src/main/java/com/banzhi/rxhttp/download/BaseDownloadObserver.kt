package com.banzhi.rxhttp.download

import com.banzhi.rxhttp.exception.ApiException
import io.reactivex.Observer
import okhttp3.ResponseBody

/**
 * <pre>
 * @author :
 * @time : 2021/5/25.
 * @desciption :
 * @version :
</pre> *
 */
abstract class BaseDownloadObserver : Observer<ResponseBody?> {
    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract fun doOnError(errorMsg: String?)

    override fun onError(e: Throwable) {
        val error = ApiException.handleException(e).message
        setError(error)
    }

    private fun setError(errorMsg: String?) {
        doOnError(errorMsg)
    }
}
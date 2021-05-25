package com.banzhi.rxhttp.download;

import com.banzhi.rxhttp.exception.ApiException;
import com.banzhi.rxhttp.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/6/5.
 * @desciption :
 * @version :
 * </pre>
 */

public abstract class BaseDownloadObserver implements Observer<ResponseBody> {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void doOnError(String errorMsg);


    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    private void setError(String errorMsg) {
        ToastUtils.showShort(errorMsg);
        doOnError(errorMsg);
    }
}

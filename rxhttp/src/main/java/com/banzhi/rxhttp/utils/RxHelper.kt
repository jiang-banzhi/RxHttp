package com.banzhi.rxhttp.utils

import android.app.Dialog
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/14.
 * @desciption :
 * @version :
</pre> *
 */
class RxHelper {
    companion object {
        /**
         * @param <T> 泛型
         * @return 返回Observable
        </T> */
        fun <T> switchSchedulers(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe { }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

        /**
         * 带参数  显示loading对话框
         *
         * @param dialog loading
         * @param <T>    泛型
         * @return 返回Observable
        </T> */
        fun <T> switchSchedulers(dialog: Dialog?): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream ->
                upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe { dialog?.show() }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}
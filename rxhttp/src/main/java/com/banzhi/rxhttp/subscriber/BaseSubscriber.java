package com.banzhi.rxhttp.subscriber;

import android.content.Context;

import com.banzhi.rxhttp.exception.ApiException;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

/**
 * <pre>
 * @author :
 * @time : 2021/5/21.
 * @desciption :
 * @version :
 * </pre>
 */

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {
    WeakReference<Context> contextWeakReference;

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    public Context getContext() {
        return contextWeakReference.get();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(ApiException e);
}

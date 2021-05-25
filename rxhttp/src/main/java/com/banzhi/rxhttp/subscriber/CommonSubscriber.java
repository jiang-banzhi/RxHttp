package com.banzhi.rxhttp.subscriber;

import com.banzhi.rxhttp.exception.ApiException;

/**
 * <pre>
 * @author :
 * @time : 2021/5/25.
 * @desciption :
 * @version :
 * </pre>
 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    @Override
    public void onError(ApiException e) {

    }
}

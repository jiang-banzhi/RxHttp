package com.banzhi.rxhttp.subscriber;

import com.banzhi.rxhttp.exception.ApiException;
import com.banzhi.rxhttp.utils.ToastUtils;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/8/22.
 * @desciption :
 * @version :
 * </pre>
 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    @Override
    public void onError(ApiException e) {
        ToastUtils.showShort(e.getMessage());
    }
}

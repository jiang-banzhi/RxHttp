package com.banzhi.rxhttpsample;

import android.content.Context;

import com.banzhi.rxhttp.RxHttp;
import com.banzhi.rxhttp.base.BaseApplication;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/16.
 * @desciption :
 * @version :
 * </pre>
 */

public class MyApplication extends BaseApplication {
    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        RxHttp.init(mContext);
        RxHttp.getInstance("https://github.com/jiang-banzhi/")
//                .addInterceptor(new TokenInterecptor())
                .setRetryCount(2)
                .setTokenProxy(new MyTokenProxy())
                .create();
    }
}

package com.banzhi.rxhttp.base;

import android.app.Application;
import android.content.Context;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/14.
 * @desciption :
 * @version :
 * </pre>
 */

public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}

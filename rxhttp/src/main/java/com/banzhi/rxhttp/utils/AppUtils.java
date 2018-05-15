package com.banzhi.rxhttp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/14.
 * @desciption :
 * @version :
 * </pre>
 */

public class AppUtils {
    /**
     * 获取手机版本号
     *
     * @return 返回版本号
     */
    public static String getAppVersion(Context context) {
        PackageInfo pi;
        String versionNum;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionNum = pi.versionName;
        } catch (Exception e) {
            versionNum = "-1";
        }
        return versionNum;
    }
}

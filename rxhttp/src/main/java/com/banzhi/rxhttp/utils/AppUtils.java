package com.banzhi.rxhttp.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.banzhi.rxhttp.base.BaseApplication;

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
    public static String getAppVersion() {
        PackageInfo pi;
        String versionNum;
        try {
            PackageManager pm = BaseApplication.getContext().getPackageManager();
            pi = pm.getPackageInfo(BaseApplication.getContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionNum = pi.versionName;
        } catch (Exception e) {
            versionNum = "-1";
        }
        return versionNum;
    }
}

package com.banzhi.rxhttp.utils;

import android.widget.Toast;

import com.banzhi.rxhttp.RxHttp;

/**
 * <pre>
 *     desc  : 吐司相关工具类
 * </pre>
 */
public final class ToastUtils {

    private static Toast sToast;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 显示吐司
     *
     * @param msg 格式
     */
    public static void show(final String msg, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(RxHttp.getContext(), msg, duration);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    /**
     * 显示吐司
     *
     * @param msg 内容
     */
    public static void showShort(final String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示吐司
     *
     * @param msg 内容
     */
    public static void showLong(final String msg) {
        show(msg, Toast.LENGTH_LONG);
    }


    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}

package com.banzhi.rxhttp.download;

import com.banzhi.rxhttp.RxHttp;
import com.banzhi.rxhttp.utils.RxHelper;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/6/5.
 * @desciption :
 * @version :
 * </pre>
 */

public class DownloadRetrofit {
    /**
     * 文件下载
     *
     * @param url 文件路径
     * @return
     */
    public static Observable<ResponseBody> downloadFile(String url) {
        return RxHttp
                .getService(DownloadApiAddress.class)
                .downloadFile(url)
                .compose(RxHelper.<ResponseBody>switchSchedulers());
    }
}

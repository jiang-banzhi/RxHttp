package com.banzhi.rxhttp.interceptor;

import com.banzhi.rxhttp.base.BaseApplication;
import com.banzhi.rxhttp.utils.SPUtils;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/14.
 * @desciption :
 * @version :
 * </pre>
 */

public class RequestInterceptor implements Interceptor {

    private Map<String, Object> headerMaps = new TreeMap<>();

    public RequestInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    public RequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (headerMaps == null || headerMaps.size() == 0) {
            request
                    .addHeader("Authorization", getToken())
                    .addHeader("Content-type", "application/json")
                    .addHeader("Version", getAppVersion())
                    .addHeader("Terminal", 0 + "")
                    .addHeader("User-Agent", System.getProperty("http.agent"));
        } else {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }

        return chain.proceed(request.build());
    }


    private String getAppVersion() {
        return getAppVersion();
    }

    private String getToken() {
        return (String) SPUtils.get(BaseApplication.getContext(), "token", "");
    }

}

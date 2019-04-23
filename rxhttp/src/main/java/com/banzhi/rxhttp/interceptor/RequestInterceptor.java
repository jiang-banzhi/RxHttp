package com.banzhi.rxhttp.interceptor;

import android.content.Context;

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
 * @desciption :请求拦截器 便于统一添加header
 * @version :
 * </pre>
 */

public class RequestInterceptor implements Interceptor {
    Context mContext;
    private Map<String, Object> headerMaps = new TreeMap<>();

    public RequestInterceptor(Context context, Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
        this.mContext = context;
    }

    public RequestInterceptor(Context context) {
        this.mContext = context;
    }

    private static String TOKEN_KEY = "Authorization";

    public RequestInterceptor(Context context, String tokenKey) {
        this.mContext = context;
        TOKEN_KEY = tokenKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (headerMaps == null || headerMaps.size() == 0) {
            request
                    .addHeader(TOKEN_KEY, getToken())
                    .addHeader("Content-type", "application/json")
                    .addHeader("Accept", "*/*")
                    .addHeader("Terminal", 0 + "")
                    .addHeader("User-Agent", System.getProperty("http.agent"));
        } else {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }

        return chain.proceed(request.build());
    }

    private String getToken() {
        if (mContext != null) {
            return (String) SPUtils.get(mContext, "token", "");
        }
        return "";
    }


}

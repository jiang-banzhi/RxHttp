package com.banzhi.rxhttpsample;

import android.util.Log;

import com.banzhi.rxhttp.interceptor.RetryInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/11/27.
 * @desciption :
 * @version :
 * </pre>
 */

public class MyTokenProxy implements RetryInterceptor.TokenProxy {
    Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public boolean isTokenExpired(Response proceed) throws IOException {
        ResponseBody body = proceed.body();
        Log.i("result", "isTokenExpired: ");
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String result = buffer.clone().readString(charset);
        try {
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.optInt("Code") == -1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Request onNewRequest(Interceptor.Chain chain) throws IOException {
        String newToken = HttpUtils.getNewToken();
        Request newRequest = chain.request().newBuilder()
                .addHeader("token", newToken)
                .build();
        return newRequest;
    }
}
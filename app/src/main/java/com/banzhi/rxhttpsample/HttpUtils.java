package com.banzhi.rxhttpsample;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/11/27.
 * @desciption :
 * @version :
 * </pre>
 */

public class HttpUtils
{
    public static String getNewToken() throws IOException {
    String headerToken;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.210:8008/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    retrofit2.Response<BaseBean<Token>> response = retrofit.create(ApiServer.class)
            .login(new LoginRequest("王宇", "123456"))
            .execute();
    headerToken = response.body().getTarget().getToken().toString();
    return headerToken;
}
}

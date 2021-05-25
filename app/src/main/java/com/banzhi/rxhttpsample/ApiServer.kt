package com.banzhi.rxhttpsample;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/11/27.
 * @desciption :
 * @version :
 * </pre>
 */

public interface ApiServer {
    @POST("http://192.168.1.210:8008/v1/conferencesmanage/getconferencesgenrelist")
    Observable<BaseBean> loadType();

    @POST("/v1/home/userlogin")
    Call<BaseBean<Token>> login(@Body LoginRequest request);
}

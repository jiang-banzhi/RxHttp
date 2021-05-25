package com.banzhi.rxhttpsample

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * <pre>
 * @author :
 * @time : 2021/5/25.
 * @desciption :
 * @version :
</pre> *
 */
interface ApiServer {
    @POST("http://192.168.1.210:8008/v1/conferencesmanage/getconferencesgenrelist")
    fun loadType(): Observable<BaseBean<*>>

    @POST("/v1/home/userlogin")
    fun login(@Body request: LoginRequest?): Call<BaseBean<Token>>
}
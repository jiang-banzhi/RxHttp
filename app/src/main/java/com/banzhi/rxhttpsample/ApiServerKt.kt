package com.banzhi.rxhttpsample

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *<pre>
 * @author : jiang
 * @time : 2021/5/24.
 * @desciption :
 * @version :
 *</pre>
 */
interface ApiServerKt {
    @POST("http://192.168.1.209:8988/v1/home/Login")
    suspend fun login(@Body request: LoginRequest): BaseBean<Token>

    @POST("http://192.168.1.209:8988/v1/home/Login")
    fun loginCall(@Body request: LoginRequest): Call<BaseBean<Token>>

    @POST("http://192.168.1.209:8988/v1/home/Login")
    fun loginRx(@Body request: LoginRequest): Observable<BaseBean<Token>>

}
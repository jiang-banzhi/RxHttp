package com.banzhi.rxhttpsample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * <pre>
 * @author :
 * @time : 2021/5/25.
 * @desciption :
 * @version :
</pre> *
 */
object HttpUtils {
    @get:Throws(IOException::class)
    val newToken: String
        get() {
            val headerToken: String
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.210:8008/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val response = retrofit.create(
                ApiServer::class.java
            )
                .login(LoginRequest("王宇", "123456"))
                .execute()
            headerToken = response.body()!!.target.token.toString()
            return headerToken
        }
}
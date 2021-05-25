package com.banzhi.rxhttpsample

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

/**
 * <pre>
 * @author : No.1
 * @time : 2018/11/27.
 * @desciption :
 * @version :
</pre> *
 */
class TokenInterecptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val proceed: Response = chain.proceed(request.build())
        if (isTokenExpired(proceed)) {
            val newToken = HttpUtils.newToken
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("token", newToken)
                .build()
            return chain.proceed(newRequest)
        }
        return proceed
    }

    var UTF8 = Charset.forName("UTF-8")
    @Throws(IOException::class)
    private fun isTokenExpired(proceed: Response): Boolean {
        val body = proceed.body
        Log.i("result", "isTokenExpired: ")
        val source = body!!.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer()
        var charset = UTF8
        val contentType = body.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)
        }
        val result = buffer.clone().readString(charset!!)
        try {
            val jsonObject = JSONObject(result)
            return jsonObject.optInt("Code") == -1
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return false
    }
}
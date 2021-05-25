package com.banzhi.rxhttp.download

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * <pre>
 * @author :
 * @time : 2021/5/25.
 * @desciption : 文件下载
 * @version :
</pre> *
 */
interface DownloadApiAddress {
    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     *
     * @param fileUrl 地址
     * @return ResponseBody
     */
    @Streaming
    @GET
    fun downloadByRx(@Url fileUrl: String?): Observable<ResponseBody?>

    fun downloadByCall(@Url fileUrl: String?): Call<ResponseBody>

    suspend fun downloadByCoroutine(@Url fileUrl: String?): ResponseBody
}
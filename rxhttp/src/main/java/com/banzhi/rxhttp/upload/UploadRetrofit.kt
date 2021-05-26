package com.banzhi.rxhttp.upload

import com.banzhi.rxhttp.RxHttp
import com.banzhi.rxhttp.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Companion.FORM
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

/**
 * <pre>
 * @author : No.1
 * @time : 2021/5/21.
 * @desciption :
 * @version :
</pre> *
 */
object UploadRetrofit {
    private fun getUploadService(): UploadApiAddress = RxHttp.getInstance()
        .getService(UploadApiAddress::class.java)

    /**
     * 单文件上传
     *
     * @param uploadUrl 文那件上传路径
     * @param filePath  文件本地路径
     * @param key       后台接收图片流的参数名
     * @return
     */
    fun uploadFile(uploadUrl: String, key: String, filePath: String): Observable<ResponseBody> {
        val file = File(filePath)
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData(key, file.name, requestFile)
        return getUploadService()
            .uploadFile(uploadUrl, body)
            .compose(RxHelper.switchSchedulers())
    }

    /**
     * 多文件上传
     *
     * @param uploadUrl 文那件上传路径
     * @param filePaths 多个文件本地路径
     * * @param key       后台接收图片流的参数名
     * @return
     */
    fun uploadFiles(
        uploadUrl: String,
        key: String,
        filePaths: List<String>
    ): Observable<ResponseBody> {
        val builder: MultipartBody.Builder = MultipartBody.Builder().setType(FORM)
        for (i in filePaths.indices) {
            val file = File(filePaths[i])
            val fileBody: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            builder.addFormDataPart(key, file.name, fileBody)
        }
        val parts: List<MultipartBody.Part> = builder.build().parts
        return getUploadService()
            .uploadFiles(uploadUrl, parts)
            .compose(RxHelper.switchSchedulers())

    }
}
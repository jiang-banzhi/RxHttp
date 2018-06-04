package com.banzhi.rxhttp.upload;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * <pre>
 * @author : banzhi
 * @time : 2018/6/4.
 * @desciption : 文件上传
 * @version :
 * </pre>
 */
public interface UploadApiAddress {
    /**
     * 上传
     *
     * @param uploadUrl 地址
     * @param file      文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String uploadUrl, @Part MultipartBody.Part file);


    /**
     * 上传多个文件
     *
     * @param uploadUrl 地址
     * @param files     文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String uploadUrl, @Part List<MultipartBody.Part> files);

}

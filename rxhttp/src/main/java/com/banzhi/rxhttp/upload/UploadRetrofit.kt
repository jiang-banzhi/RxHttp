package com.banzhi.rxhttp.upload;

import com.banzhi.rxhttp.RxHttp;
import com.banzhi.rxhttp.utils.RxHelper;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * <pre>
 * @author : No.1
 * @time : 2021/5/21.
 * @desciption :
 * @version :
 * </pre>
 */

public class UploadRetrofit {

    private static UploadApiAddress getUploadService() {
        return RxHttp.Companion.getInstance()
                .getService(UploadApiAddress.class);
    }

    /**
     * 单文件上传
     *
     * @param uploadUrl 文那件上传路径
     * @param filePath  文件本地路径
     * @param key       后台接收图片流的参数名
     * @return
     */
    public static Observable<ResponseBody> uploadFile(String uploadUrl, String key, String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);

        return getUploadService()
                .uploadFile(uploadUrl, body)
                .compose(RxHelper.Companion.switchSchedulers());
    }



    /**
     * 多文件上传
     *
     * @param uploadUrl 文那件上传路径
     * @param filePaths 多个文件本地路径
     *                  * @param key       后台接收图片流的参数名
     * @return
     */
    public static Observable<ResponseBody> uploadFiles(String uploadUrl, String key, List<String> filePaths) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(key, file.getName(), fileBody);
        }

        List<MultipartBody.Part> parts = builder.build().parts();

        return getUploadService()
                .uploadFiles(uploadUrl, parts)
                .compose(RxHelper.Companion.switchSchedulers());
    }
}

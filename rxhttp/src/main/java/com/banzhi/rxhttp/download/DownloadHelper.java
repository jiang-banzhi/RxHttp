package com.banzhi.rxhttp.download;

import android.text.TextUtils;

import com.banzhi.rxhttp.RxHttp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/6/5.
 * @desciption :
 * @version :
 * </pre>
 */

public class DownloadHelper {

    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENTTYPE = "image/png";
    private static String JPG_CONTENTTYPE = "image/jpg";
    private static String fileSuffix = "";

    /**
     * 保存文件
     *
     * @param response ResponseBody
     * @param fileName 文件名
     * @return 返回
     * @throws IOException
     */
    public File saveFile(ResponseBody response, String fileName, ProgressListener progressListener) throws IOException {

        String destFileDir = RxHttp.getContext().getExternalFilesDir(null) + File.separator;

        long contentLength = response.contentLength();
        if (!TextUtils.isEmpty(fileName)) {
            String type;
            if (!fileName.contains(".")) {
                type = response.contentType().toString();
                if (type.equals(APK_CONTENTTYPE)) {
                    fileSuffix = ".apk";
                } else if (type.equals(PNG_CONTENTTYPE)) {
                    fileSuffix = ".png";
                } else if (type.equals(JPG_CONTENTTYPE)) {
                    fileSuffix = ".jpg";
                } else {
                    fileSuffix = "." + response.contentType().subtype();
                }
                fileName = fileName + fileSuffix;
            }
        } else {
            fileName = System.currentTimeMillis() + fileSuffix;
        }
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.byteStream();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                progressListener.onResponseProgress(finalSum, contentLength, (int) ((finalSum * 1.0f / contentLength) * 100), finalSum == contentLength, file.getAbsolutePath());
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }

        }
    }

}

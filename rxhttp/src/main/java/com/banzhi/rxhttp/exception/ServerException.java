package com.banzhi.rxhttp.exception;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/15.
 * @desciption :
 * @version :
 * </pre>
 */

public class ServerException extends RuntimeException {
    int code;
    String message;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

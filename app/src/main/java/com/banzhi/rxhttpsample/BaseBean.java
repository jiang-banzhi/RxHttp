package com.banzhi.rxhttpsample;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/8/22.
 * @desciption :
 * @version :
 * </pre>
 */

public class BaseBean<T> {
    int Code;
    String Message;
    T Target;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getTarget() {
        return Target;
    }

    public void setTarget(T target) {
        Target = target;
    }
}

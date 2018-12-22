package com.lichaobao.springsecurityjwt.http;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
public class ServiceVO<T> {
    private int code = 200;
    private String message = "success";

    private T result;

    public ServiceVO(T result) {
        this.result = result;
    }

    public ServiceVO() {
    }

    public ServiceVO(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ServiceVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceVO(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

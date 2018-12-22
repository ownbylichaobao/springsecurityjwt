package com.lichaobao.springsecurityjwt.http;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
public enum  ErrorCode {
    ACCESS_DENIED(403,"forbidden"),
    ERROR_CE(405,"incorrect ce or not login"),
    SYSTEM_ERROR(500,"system error")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

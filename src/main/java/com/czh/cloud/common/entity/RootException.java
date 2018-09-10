package com.czh.cloud.common.entity;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/6 11:51
 */
public class RootException extends RuntimeException {

    private String code;
    private String msg;

    public RootException(String code, String msg) {
        this(code, msg, (Throwable) null);
    }

    public RootException(String code, String msg, Throwable cause) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public RootException(RootResultCode picaResultCode, Throwable cause) {
        super(cause);
        this.code = picaResultCode.code();
        this.msg = picaResultCode.message();
    }

    public RootException(RootResultCode picaResultCode) {
        this.code = picaResultCode.code();
        this.msg = picaResultCode.message();
    }

    public RootException(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return "{code=" + this.code + ", message=" + this.msg + "}";
    }
}

package com.czh.cloud.common.entity;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/5 15:17
 */
public class RootResponse<T> {

    public RootResponse() {
        super();
    }

    public RootResponse(RootResultCode rootResultCode) {
        this.code = rootResultCode.code();
        this.message = rootResultCode.message();
    }

    private String code;

    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static RootResponse instance(){
        return new RootResponse(RootResultCode.SUCCESS);
    }

    public String toString() {
        return "{code=" + this.code + ", message=" + this.message + ", data=" + this.data + "}";
    }
}

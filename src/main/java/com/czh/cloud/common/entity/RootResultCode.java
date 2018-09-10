package com.czh.cloud.common.entity;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/6 11:46
 */
public enum RootResultCode {
    SUCCESS("000000", "成功"),
    SYSTEM_INNER_BUSY("000001", "系统繁忙，请稍后重试"),
    ARGS_CHECK_NO_ACCESS("000002", "参数校验不通过");

    private String code;
    private String message;

    RootResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}

package com.czh.cloud.common.entity;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/6 11:46
 */
public enum RootResultCode {
    SUCCESS("000000", "成功"),
    SYSTEM_INNER_BUSY("300001", "系统繁忙，请稍后重试"),
    PERMISSION_NO_ACCESS("600001", "访问权限不足");

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

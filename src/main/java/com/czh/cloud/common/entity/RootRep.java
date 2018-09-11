package com.czh.cloud.common.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description: 根返回体
 * @date: 2018/9/11 14:01
 */
public abstract class RootRep {
    @Override
    public String toString() {
        return JSONObject.toJSON(this).toString();
    }
}

package com.czh.cloud.common.exception;

import com.czh.cloud.common.entity.RootException;
import com.czh.cloud.common.entity.RootResponse;
import com.czh.cloud.common.entity.RootResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenzhehao
 * @version 1.0
 * @Title: GlobalExceptionHandler.java
 * @Description:
 * @Copyright: Copyright (c) 2018
 * @Company: www.chenzhehao.com
 * @date 2018年9月6日
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public RootResponse myException(final HttpServletRequest request, final HttpServletResponse response,
                                    final Exception ex) {
        logger.error("请求异常：", ex);
        RootResponse resp = RootResponse.instance(RootResultCode.SYSTEM_INNER_BUSY);
        if (ex instanceof RootException) {
            RootException e = (RootException) ex;
            resp.setRootResponse(e);
        }
        return resp;
    }
}

package com.czh.cloud.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.czh.cloud.common.entity.RootException;
import com.czh.cloud.common.entity.RootResultCode;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/9/10 10:23
 */
@Aspect
@Component
@Order(9000)
public class ControllerParamsAop {
    private static final Logger logger = LoggerFactory.getLogger(ControllerParamsAop.class);
    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();


    @Pointcut(value = "execution(* com.czh.cloud..*controller..*.*(..))")
    public void controllerOfLog() {
        //用于定位controller层，打印传入和返回参数
    }

    @Pointcut(value = "args(..,bindingResult)", argNames = "..,bindingResult")
    public void controllerOfCheck(BindingResult bindingResult) {
        //配合beforeOfLog一起使用，用于定位controller层，校验参数
    }


    /**
     * 可以使用@Pointcut定义，也可以直接定义,如下两种都可以
     * pointcut中只能用&& 不能用and
     * 参数注意：函数里都要有，可以混合使用
     *
     * @param bindingResult
     * @throws Exception
     * @Before(value = "pointcut1(param) && pointcut2(secure)"
     */
    @Before("controllerOfLog() && controllerOfCheck(bindingResult)")
//	@Before("execution(* com.czh.cloud..*controller..*.*(..)) and args(..,bindingResult)")
    public void checkParamsException(BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            StringBuffer messages = new StringBuffer();
            for (ObjectError error : bindingResult.getAllErrors()) {
                messages.append(error.getDefaultMessage().toString() + ",");
            }
            messages.deleteCharAt(messages.length() - 1);
            throw new RootException(RootResultCode.ARGS_CHECK_NO_ACCESS.code(), messages.toString());
        }
    }

    /**
     * 功能描述:
     *
     * @author: zhehao.chen
     * @version: V1.0
     * @date: 2018/9/10 11:29
     * @param: []
     * @return: void
     */
    @Around("controllerOfLog()")
    public Object logParams(ProceedingJoinPoint pjp) throws Exception, Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        this.request.set(request);
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String queryString = "";
        if ("GET".equalsIgnoreCase(method)) {
            queryString = request.getQueryString();
        } else if (!this.parseMultipart()) {//POST,文件上传等特殊类型暂未考虑
            queryString = getParamString();
        } else {
            queryString = "";
        }
        Long startTime = System.currentTimeMillis();
        logger.info("request start, controller params==>, url: {}, method: {}, headers: {}, params: {}", new Object[]{url, method, getHeaderString(), queryString});
        Object result = pjp.proceed();
        try {
            String res = JSONObject.toJSONString(result);
            if (this.parseMultipart()) {
                res = "upload/download";
            }
            logger.info("request  over，controller result ==> " + res + " request spent time milliSeconds ==>" + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            logger.error("controller返回信息解析打印异常（不影响业务逻辑）", e);
        }
        return result;
    }

    protected boolean parseMultipart() throws Exception {
        return ServletFileUpload.isMultipartContent((HttpServletRequest) this.request.get());
    }

    private String getHeaderString() {
        Enumeration<String> enums = this.request.get().getHeaderNames();
        Map map = new HashMap<String, Object>();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            map.put(key, this.request.get().getHeader(key));
        }
        return map.toString();
    }

    private String getParamString() {
        /**
         * 通过request.getParameter获取请求参数，然而这种方式只能获取POST方式中的Content-Type: application/x-www-form-urlencoded。
         * 通过request.getInputStream(或者getReader)读取请求数据流(能解析出content-Type为 application/x-www-form-urlencoded ，application/json , text/xml
         *     这三种提交方式的数据（注：multipart/form-data不行），但是！！！在后续controller接口中无法获取该数据流
         *     主要问题在于：不能在过滤器中读取一次二进制流（字符流），又在另外一个Servlet中读取一次，即一个InputSteam(BufferedReader)对象在被读取完成后，将无法再次被读取。
         *     使用BodyReaderHttpServletRequestWrapper替换，对流封装可以多次读取
         */
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = this.request.get().getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
            logger.error("获取参数params异常", e);
        } finally {
            return data.toString();
        }
    }


}

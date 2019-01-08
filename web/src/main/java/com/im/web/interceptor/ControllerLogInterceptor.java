package com.im.web.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * 自定义拦截器
 */
@Aspect
@Component
public class ControllerLogInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ControllerLogInterceptor.class);
    @Pointcut("@annotation(com.my.im.annotation.ControllerLogger)")
    public void logMethed(){

    }

    @Before("logMethed()")
    public void doBefore(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String ip = request.getRemoteHost();
        Date date = new Date(System.currentTimeMillis());
        log.info("来自{}的用户访问了{}接口，开始时间{}",ip,uri,date);
    }
    @After("logMethed()")
    public void doAfter(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String ip = request.getRemoteHost();
        Date date = new Date(System.currentTimeMillis());
        log.info("来自{}的用户离开了{}接口，离开时间{}",ip,uri,date);
    }


}

package com.im.web.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * 自定义拦截器
 */
@Aspect
@Component
public class ServiceLogInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ServiceLogInterceptor.class);

    @Pointcut("@annotation(com.my.im.annotation.ServiceLogger)")
    public void logMethed() {

    }

    @Before("logMethed()")
    public void doBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] aThis = joinPoint.getArgs();
        StringBuffer sb = new StringBuffer(aThis.length);
        Date date = new Date(System.currentTimeMillis());
        for (int i = 0; i < aThis.length; i++) {
            sb.append(aThis[i].toString()+" ");
        }
        log.info("开始调用{}方法，参数为{}，时间为{}",signature,sb,date);

    }

    @After("logMethed()")
    public void doAfter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] aThis = joinPoint.getArgs();
        StringBuffer sb = new StringBuffer(aThis.length);
        for (int i = 0; i < aThis.length; i++) {
            sb.append(aThis[i].toString()+" ");
        }
        Date date = new Date(System.currentTimeMillis());
        log.info("结束调用{}方法，参数为{}，时间为{}",signature,sb,date);
    }


}

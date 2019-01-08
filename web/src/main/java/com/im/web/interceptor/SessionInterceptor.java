/*
package com.im.web.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.user.IUserService;
import com.im.web.annotation.SessionCheck;
import com.im.api.dto.user.User;
import com.im.api.exception.TipException;
import com.im.redis.client.RedisClient;
import com.im.web.util.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * 自定义拦截器
 *//*

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserSession userSession;

    @Autowired
    private RedisClient redisClient;

    @Reference
    private IUserService userService;


    @Value("${spring.redis.timeout}")
    private int sessionTimeOut;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String value = "false";
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            SessionCheck methodAnnotation = h.getMethodAnnotation(SessionCheck.class);
            if (methodAnnotation == null) {
                return true;
            }
            value = methodAnnotation.value();
        }
        if ("true".equals(value) || "".equals(value)) {
            String token = userSession.getCookieUid(request);//首先从请求中获取token
            if (token == null) {
                throw new TipException("100002");
            }
            String user = redisClient.get(token);//查缓存
         //   User user = JSONObject.parseObject(jsonUser, User.class);
            if (null == user) {
                User user1 = userService.queryUserById(token);//查数据库
                if (user1 == null) {
                    throw new TipException("100002");
                }
            }
            redisClient.expire(token, sessionTimeOut);
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
*/

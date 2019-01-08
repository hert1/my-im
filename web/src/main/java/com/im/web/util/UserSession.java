package com.im.web.util;

import com.alibaba.fastjson.JSONObject;
import com.im.api.dto.user.User;
import com.im.api.util.Tools;
import com.im.api.util.WebConst;
import com.im.redis.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserSession {

    private int sessionTimeOut;

    @Autowired
    private RedisClient redisClient;



    /**
    * 登录/注册用户处理
    *
    */
    public void saveUserToToken(User user, HttpServletResponse resp, String token){
        setCookie(resp,token);
        String JsonUser = JSONObject.toJSONString(user);
        redisClient.setex(token,JsonUser,sessionTimeOut);
    }

    /**
     *User logout
     */
    public boolean logout(HttpServletRequest req, HttpServletResponse resp){
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);// 立即销毁cookie
        cookie.setPath("/");
        resp.addCookie(cookie);
        String cookieUid = getCookieUid(req);
        Long del = redisClient.del(cookieUid);
        if(del!=0){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 返回当前登录用户
     *
     * @return
     */

    public User getLoginUserByUid(HttpServletRequest request) {
        User user = null;
        String uid = getCookieUid(request);
        if (uid != null) {
            String jsonUser =  redisClient.get(String.valueOf(uid));
             user = JSONObject.parseObject(jsonUser, User.class);
        }
        return user;
    }


    /**
     * 获取cookie中的用户id
     *
     * @param request
     * @return
     */
    public String getCookieUid(HttpServletRequest request) {
        if (null != request) {
            Cookie cookie = cookieRaw(WebConst.USER_IN_COOKIE, request);
            if (cookie != null && cookie.getValue() != null) {
                try {
                    String uid = Tools.deAes(cookie.getValue(), WebConst.AES_SALT);
                    return uid;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 从cookies中获取指定cookie
     *
     * @param name    名称
     * @param request 请求
     * @return cookie
     */
    private Cookie cookieRaw(String name, HttpServletRequest request) {
        Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 设置token到客户端
     *
     * @param response
     * @param uid
     */
    public void setCookie(HttpServletResponse response, String uid) {
        try {
            String val = Tools.enAes(uid, WebConst.AES_SALT);
            boolean isSSL = false;
            Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, val);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 30);
            cookie.setSecure(isSSL);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

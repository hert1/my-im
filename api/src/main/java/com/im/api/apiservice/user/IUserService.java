package com.im.api.apiservice.user;


import com.im.api.dto.article.BlogInfoBean;
import com.im.api.dto.user.User;

/**
 * Created by BlueT on 2017/3/3.
 */
public interface IUserService {

    /**
     * 用戶登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
    /**
     * 用戶Blog信息
     * @return
     */
    BlogInfoBean getBlogInfo();


}

package com.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.im.api.dto.article.BlogInfoBean;
import com.im.redis.client.RedisClient;
import com.im.service.annotation.ServiceLogger;
import com.im.api.apiservice.user.IUserService;
import com.im.api.dto.user.User;
import com.im.api.exception.TipException;
import com.im.api.util.Tools;
import com.im.api.util.WebConst;
import com.im.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Created by BlueT on 2018/12/3.
 */
@Service(version = "1.0",timeout = 3000)
@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDao uaerDao;
    @Autowired
    RedisClient redisClient;
    @Value("${com.im.cache.time}")
    long time;

    @Override
    @ServiceLogger
    public User login( String uname,  String upwd) {
        String enAesPwd = null;
        try {
         //    enAesPwd = Tools.enAes(upwd, WebConst.AES_SALT);
        } catch (Exception e) {
          new TipException("100004");
        }
        User user = uaerDao.selectUserInfo(uname, upwd);
        return user;
    }

    @Override
    public BlogInfoBean getBlogInfo() {
        String blogInfo = redisClient.get("blogInfo");
        if (!StringUtils.isEmpty(blogInfo)) {
            return JSON.parseObject(blogInfo,BlogInfoBean.class);
        }
        BlogInfoBean bi = uaerDao.getBlogInfo();
        redisClient.setex("blogInfo",JSON.toJSONString(bi), (int) time);
        return bi;
    }


}

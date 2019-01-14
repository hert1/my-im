package com.im.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.im.api.apiservice.user.IAdminService;
import com.im.api.dto.user.AboutMeBean;
import com.im.api.dto.user.BlogConfigBean;
import com.im.redis.client.RedisClient;
import com.im.service.dao.AdminConfigDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */

@Slf4j
@Service
@Component
public class AdminServiceImpl implements IAdminService {

    @Autowired
    AdminConfigDao adminConfigDao;
    @Autowired
    RedisClient redisClient;


    @Override
    public void setWebConfig(BlogConfigBean blogConfigBean) {
        redisClient.del("blogInfo");
        adminConfigDao.setWebConfig(blogConfigBean);
    }

    @Override
    public void setAboutMe(AboutMeBean aboutMe) {
        redisClient.del("aboutMe");
        int i = adminConfigDao.setAboutMe(aboutMe);
        if (i == 0) {
            adminConfigDao.insertAboutMe(aboutMe);
        }
    }

    @Override
    public AboutMeBean getAboutMe() {
        String aboutMe = redisClient.get("aboutMe");
        if(!StringUtils.isEmpty(aboutMe)) {
            return JSON.parseObject(aboutMe, AboutMeBean.class);
        }
        AboutMeBean am = adminConfigDao.getAboutMe();
        redisClient.set("webConfig",JSON.toJSONString(am));
        return am;
    }
}

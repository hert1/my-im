package com.im.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.im.api.apiservice.user.IAdminService;
import com.im.api.dto.user.AboutMeBean;
import com.im.api.dto.user.BlogConfigBean;
import com.im.service.dao.AdminConfigDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */
@Component
@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    AdminConfigDao adminConfigDao;

    @Override
    public BlogConfigBean getWebConfig() {
        return adminConfigDao.getWebConfig();
    }

    @Override
    public void setWebConfig(BlogConfigBean blogConfigBean) {
        adminConfigDao.setWebConfig(blogConfigBean);
    }

    @Override
    public void setAboutMe(AboutMeBean aboutMe) {
        int i = adminConfigDao.setAboutMe(aboutMe);
        if (i == 0) {
            adminConfigDao.insertAboutMe(aboutMe);
        }
    }

    @Override
    public AboutMeBean getAboutMe() {
        return adminConfigDao.getAboutMe();
    }
}

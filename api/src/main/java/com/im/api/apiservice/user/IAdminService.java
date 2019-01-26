package com.im.api.apiservice.user;

import com.im.api.dto.article.BlogInfoBean;
import com.im.api.dto.user.AboutMeBean;
import com.im.api.dto.user.BlogConfigBean;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */
public interface IAdminService {

    /**
     * 更新配置
     * @return
     */
    void setWebConfig(BlogInfoBean blogConfigBean);
    /**
     * 更新关于我
     * @return
     */
    void setAboutMe(AboutMeBean aboutMe);
    /**
     * get关于我
     * @return
     */
    AboutMeBean getAboutMe();
}

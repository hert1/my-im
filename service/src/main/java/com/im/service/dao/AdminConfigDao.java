package com.im.service.dao;

import com.im.api.dto.user.AboutMeBean;
import com.im.api.dto.user.BlogConfigBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */
@Mapper
public interface AdminConfigDao {
    /**
     * 获取配置
     *
     * @return
     */
    @Select("select * from blog_config")
    BlogConfigBean getWebConfig();

    /**
     * 更新配置
     *
     * @return
     */
    @Update("UPDATE blog_config set alipay_qrcode=#{alipay_qrcode},avatar=#{avatar},blog_name=#{blog_name},github=#{github},salt=#{salt},sign=#{sign},view_password=#{view_password},wxpay_qrcode=#{wxpay_qrcode} ")
    void setWebConfig(BlogConfigBean blogConfigBean);

    /**
     * 更新关于我
     * @return
     */
    @Update("UPDATE pages set html = #{html},md = #{md},type = #{type}")
    int setAboutMe(AboutMeBean aboutMe);
    /**
     * 插入关于我
     * @return
     */
    @Insert("insert into pages (html,md,type)values (#{html},#{md},#{type})")
    void insertAboutMe(AboutMeBean aboutMe);
    /**
     * get关于我
     * @return
     */

    @Select("select * from pages")
    AboutMeBean getAboutMe();
}

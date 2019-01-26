package com.im.service.dao;


import com.im.api.dto.article.BlogInfoBean;
import com.im.api.dto.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UserDao {


    /**
     * 通过账号密码查询userinfo
     * @return
     */
    @Select("select * from admin where username=#{username} and password=#{password}")
    public User selectUserInfo(String username, String password);

    /**
     * bloginfo
     * @return
     */
    @Select("select * from blog_config")
    @Results( id="blog_config",value = {
            @Result(column = "blog_name", property = "blogName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "avatar", property = "avatar", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "sign", property = "sign", jdbcType = JdbcType.VARCHAR),
            @Result(column = "wxpay_qrcode", property = "wxpayQrcode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "alipay_qrcode", property = "alipayQrcode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "github", property = "github", jdbcType = JdbcType.VARCHAR),
            @Result(column = "view_password", property = "viewPassword", jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt", property = "salt", jdbcType = JdbcType.VARCHAR)
    }

    )
    public BlogInfoBean getBlogInfo();
}

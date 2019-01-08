package com.im.service.dao;


import com.im.api.dto.article.BlogInfoBean;
import com.im.api.dto.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    public BlogInfoBean getBlogInfo();
}

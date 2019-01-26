package com.im.service.dao;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CommentBean;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author viruser
 * @create 2018/12/25
 * @since 1.0.0
 */
@Mapper
public interface ArticleDao {
    /**\
     *通过id查文章
     * @return
     */
    @Select("select * from article where aid=#{id}")
    @Results(id = "article", value = {
            @Result(column = "aid", property = "aid", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.DATE),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.DATE),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.DATE),
            @Result(column = "publish_time", property = "publishTime", jdbcType = JdbcType.DATE),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "category_id", property = "categoryId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "html_content", property = "htmlContent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cover", property = "cover", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sub_message", property = "subMessage", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pageview", property = "pageview", jdbcType = JdbcType.INTEGER),
            @Result(column = "isEncrypt", property = "isEncrypt", jdbcType = JdbcType.INTEGER),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR)
    }
    )
    public ArticleBean getContentsByNum(String id);

    /**
     *通过fid,rid查回复
     * @param article_id
     * @return
     */
    @Select("select * from comments where article_id=#{article_id} and reply_id = #{reply_id}")
    @Results( id = "comments",value = {
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.INTEGER),
            @Result(column = "reply_id", property = "replyId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "source_content", property = "sourceContent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.DATE),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.DATE),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "is_author", property = "isAuthor", jdbcType = JdbcType.INTEGER),
    })
    public List<CommentBean> getCommentsByAid(String article_id,int reply_id);
    /**
     *通过fid ,id查回复
     * @param article_id
     * @return
     */
    @Select("select * from comments where article_id=#{article_id} and id = #{id}")
    @ResultMap(value = "comments")
    public List<CommentBean> getCommentsByrid(int article_id,int id);

    /**
     * 回复
     * @param commentBean
     * @return
     */
    @Select("INSERT INTO comments (article_id,content,create_time,email,name,reply_id,source_content)" +
            " VALUES(#{articleId},#{content},#{createTime},#{email},#{name},#{replyId},#{sourceContent})")
    public void comment(CommentBean commentBean);
}

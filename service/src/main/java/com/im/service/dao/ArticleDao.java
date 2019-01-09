package com.im.service.dao;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CommentBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("select * from article where id=#{id}")
    public ArticleBean getContentsByNum(String id);

    /**
     *通过fid,rid查回复
     * @param article_id
     * @return
     */
    @Select("select * from comments where article_id=#{article_id} and reply_id = #{reply_id}")
    public List<CommentBean> getCommentsByAid(String article_id,int reply_id);
    /**
     *通过fid ,id查回复
     * @param article_id
     * @return
     */
    @Select("select * from comments where article_id=#{article_id} and id = #{id}")
    public List<CommentBean> getCommentsByrid(int article_id,int id);

    /**
     * 回复
     * @param commentBean
     * @return
     */
    @Select("INSERT INTO comments (article_id,content,create_time,email,name,reply_id,source_content) VALUES(#{article_id},#{content},#{create_time},#{email},#{name},#{reply_id},#{source_content})")
    public void comment(CommentBean commentBean);
}

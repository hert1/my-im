package com.im.service.dao;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.BaseResponse;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
@Mapper
public interface ContentDao {
    /**\
     *
     * @return
     */
    @Select("select * from article where status=#{status}")
    public List<ArticleBean> getContentsByNumAndSize(int status);

    /**
     * 条数
     * @return
     */
    @Select("select count(*) from article where status=#{status}")
    public Integer getContentsNum(int status);

    /**
     * 查询b分类数
     * @return
     */
    @Select("select count(*) from category")
    public int getCategoryNum();
    /**
     * 查询b标签数
     * @return
     */
    @Select("select count(*) from tag")
    public int getTagNum();
    /**
     * 查询b回复数
     * @return
     */
    @Select("select count(*) from comments")
    public int getCommentsCount();
    /**
     * 添加分类
     * @return
     */
    @Insert("insert into category (id,name,create_time) values(#{id},#{name},#{create_time})")
    public void addCategory(CategoryBean category);
    /**
     * 添加标签
     * @return
     */
    @Insert("insert into tag (id,name,create_time) values(#{id},#{name},#{create_time})")
    public void addTag(Tag tag);

    /**
     * 获取标签列表
     * @return
     */
    @Select("select * from tag")
    public List<Tag> getTagList();
    /**
     * 获取分类列表
     * @return
     */
    @Select("select * from category")
    public List<CategoryBean> getCategoryList();
    /**
     * 删除标签，回收站
     */
    @Delete("delete  from  tag  WHERE id = #{tagId} ")
    public void deleteTag( String tagId) ;

    /**
     * 删除分类，回收站
     */
    @Delete("delete  from  category  WHERE id =#{categoryId}")
    public void deleteCategory( String categoryId);

    /**
     * 修改分类
     */
    @Update("update  category set name = #{categoryName} WHERE id =#{categoryId}")
    public void modifyCategory(String categoryId, String categoryName);
    /**
     * 修改标签
     */
    @Update("update  tag set name = #{tagName} WHERE id = #{tagId} ")
    public void modifyTag(  String tagId,String tagName);
    /**
     * 发布文章
     */
    @Insert("insert into article (id,title,createTime,categoryId,content,htmlContent,cover,subMessage) values(#{id},#{title},#{createTime},#{categoryId},#{content},#{htmlContent},#{cover},#{subMessage})")
    public void publArticle( ArticleBean articleBean);
    /**
     * 绑定标和文章
     */
    @Insert("insert into article_tag_mapper (article_id,tag_id,create_time) values(#{aid},#{tid},#{date})")
    public void bindArticleAndTag(String aid, String tid, Date date);
    /**
     * 删除标签和文章关联关系
     */
    @Delete("delete from article_tag_mapper where tag_id = #{tid}")
    public void deleteArticleAndTag(String tid);
    /**
     * 删除分类和文章关联关系
     */
    @Update("update  article set categoryId = '' WHERE categoryId =#{categoryId}")
    public void deleteArticleAndCateory(String categoryId);
}

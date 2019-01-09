package com.im.service.dao;

import com.im.api.dto.article.*;
import jdk.nashorn.internal.objects.annotations.Setter;
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
     *通过id查文章
     * @return
     */
    @Select("select * from article where id=#{id}")
    public ArticleBean getContentsByNum(String id);
    /**
     *通过状态查文章
     * @return
     */
    @Select("select * from article where status=#{status}")
    public List<ArticleBean> getContentsByNumAndSize(int status);
    /**
     *通过状态以及归类差文章
     * @return
     */
    @Select("select * from article where status=#{status} and categoryId = #{categoryId}")
    public List<ArticleBean> getContentsByCateory(int status,String categoryId);
    /**查询文章ID通过tagid
     * @return
     */

    @Select("select article_id from article_tag_mapper where tag_id=#{tagId} ")
    public List<String> getArticleIdByTag(String tagId);
    /**
     * @return
     */
    @Select("<script>"
            + "SELECT * FROM article WHERE id IN "
            + "<foreach item='item' index='index' collection='aid' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    public List<ArticleBean> getContents(@Param("aid")List aid);

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
     * save文章
     */
    @Insert("insert into article (id,title,createTime,categoryId,content,htmlContent,cover,subMessage,status) values(#{id},#{title},#{createTime},#{categoryId},#{content},#{htmlContent},#{cover},#{subMessage},#{status})")
    public void saveArticle( ArticleBean articleBean);
    /**
     * edit文章
     */
    @Update("update article set title = #{title},updateTime = #{updateTime},categoryId = #{categoryId},content = #{content},htmlContent = #{htmlContent},cover = #{cover},subMessage = #{subMessage} where id = #{id}")
    public void modifyArticle( ArticleBean articleBean);
    /**
     * remove文章
     */
    @Update("update article set status = 1 where id = #{id}")
    public void removeArticle(String id);
    /**
     * delete文章
     */
    @Delete("delete from article where id = #{id}")
    public void deleteArticle(String id);
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
    /**
     * 获取分类
     */
    @Select("select * from category where id = #{categoryId}")
    public CategoryBean getCategory( String categoryId);
    /**
     * 获取标签
     */
    @Select("select * from tag where id = #{tagId}")
    public Tag getTag( String tagId);
    /**
     * 通过articleID获取tag
     */
    @Select("SELECT tag.* FROM tag ,article,article_tag_mapper WHERE article_tag_mapper.article_id = article_tag_mapper.tag_id and article.id = #{articleId} ")
    public List<Tag> getTagByArticleId( String articleId);
    /**
     * select friends
     */
    @Select("SELECT * from friends ")
    public List<FriendsBean> getFriendsList();
    /**
     * select friends type
     */
    @Select("SELECT * from friends_type ")
    public List<FriendTypeList> getFriendTypeList();
    /**
     *添加friends
     */
    @Insert("insert into friends (name,url,friend_id,create_time,type_id) values(#{name},#{url},#{friend_id},#{create_time},#{type_id})")
    public void addFriends(FriendsBean bean);
    /**
     *删除friends
     */
    @Delete("delete from friends where friend_id = #{fid}")
    public void deleteFriend(String fid);
}

package com.im.service.dao;

import com.im.api.dto.article.*;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.bind.annotation.PathVariable;

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
    @Select("select * from article where aid=#{id}")
    public ArticleBean getContentsByNum( String id);
    /**\
     *通过关键字查文章
     * @return
     */
    @Select("select id,title,categoryId,createTime,deleteTime,updateTime,publishTime,status,cover,subMessage,pageview,isEncrypt from article where title like '%#{id}%' ")
    public List<ArticleBean> searchArticle( String id);
    /**
     *通过状态查文章
     * @return
     */
    @Select("select aid,title,category_id,create_time,delete_time,update_time,publish_time,status,cover,sub_message,pageview,isEncrypt " +
            "from article where status=#{status}")
    @ResultMap(value ="article" )
    public List<ArticleBean> getContentsByNumAndSize(int status);
    /**
     *通过状态以及归类差文章
     * @return
     */
    @Select("select aid,title,category_id,create_time,delete_time,update_time,publish_time,status,cover,sub_message,pageview,isEncrypt " +
            "from article where status=#{status} and category_id = #{categoryId}")
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
            + "select aid,title,category_id,create_time,delete_time,update_time,publish_time,status,cover,sub_message,pageview,isEncrypt " +
            "FROM article WHERE aid IN "
            + "<foreach item='item' index='index' collection='aid' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    @ResultMap(value ="article" )
    public List<ArticleBean> getContents(@Param("aid")List aid);


    /**\
     *获取最新文章
     * @return
     */
    @Select("select aid,title from article order by id desc limit 0,10 ")
    public List<ArticleBean> getNewArticle();
    /**\
     *获取最新评论
     * @return
     */
    @Select("select article_id,source_content from comments order by id desc limit 0,10 ")
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
    public List<CommentBean> getNewComment();
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
    @Insert("insert into category (aid,name,create_time) values(#{aid},#{name},#{createTime})")
    public void addCategory(CategoryBean category);
    /**
     * 添加标签
     * @return
     */
    @Insert("insert into tag (aid,name,create_time) values(#{aid},#{name},#{createTime})")
    public void addTag(Tag tag);

    /**
     * 获取标签列表
     * @return
     */
    @Select("select aid,name,article_count from tag")
    @Results( id = "tag",value = {
            @Result(column = "aid", property = "aid", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.DATE),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.DATE),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_count", property = "articleCount", jdbcType = JdbcType.INTEGER),
    }
    )
    public List<Tag> getTagList();
    /**
     * 获取分类列表
     * @return
     */
    @Select("select aid,name,create_time,article_count,can_del from category")
    @Results(id = "category", value = {
            @Result(column = "aid", property = "aid", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.DATE),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.DATE),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "article_count", property = "articleCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "can_del", property = "canDel", jdbcType = JdbcType.INTEGER)
    }
    )
    public List<CategoryBean> getCategoryList();
    /**
     * 删除标签，回收站
     */
    @Delete("delete  from  tag  WHERE aid = #{tagId} ")
    public void deleteTag( String tagId) ;

    /**
     * 删除分类，回收站
     */
    @Delete("delete  from  category  WHERE aid =#{categoryId}")
    public void deleteCategory( String categoryId);

    /**
     * 修改分类
     */
    @Update("update  category set name = #{categoryName} WHERE aid =#{categoryId}")
    public void modifyCategory(String categoryId, String categoryName);
    /**
     * 修改标签
     */
    @Update("update  tag set name = #{tagName} WHERE aid = #{tagId} ")
    public void modifyTag(  String tagId,String tagName);
    /**
     * 发布文章
     */
    @Insert("<script>" +
            "update category SET article_count = article_count +1  WHERE aid = #{categoryId};" +
            "insert into article (aid,title,create_time,category_id,content,html_content,cover,sub_message) " +
            "values(#{aid},#{title},#{createTime},#{categoryId},#{content},#{htmlContent},#{cover},#{subMessage});" +
            "</script>")
    public void publArticle( ArticleBean articleBean);
    /**
     * save文章
     */
    @Insert("insert into article (aid,title,create_time,category_id,content,html_content,cover,sub_message,status) " +
            "values(#{aid},#{title},#{createTime},#{categoryId},#{content},#{htmlContent},#{cover},#{subMessage},#{status})")
    public void saveArticle( ArticleBean articleBean);
    /**
     * edit文章
     */
    @Update("<script>" +
            "update category SET article_count = article_count +1  WHERE aid = #{categoryId};" +
            "update article set title = #{title},update_time = #{updateTime},category_id = #{categoryId}," +
            "content = #{content},html_content = #{htmlContent},cover = #{cover},sub_message = #{subMessage} where aid = #{aid}" +
            "</script>")
    public void modifyArticle( ArticleBean articleBean);
    /**
     * remove文章
     */
    @Update("update article set status = 1 where aid = #{id}")
    public void removeArticle(String id);
    /**
     * delete文章
     */
    @Delete("delete from article where aid = #{id}")
    public void deleteArticle(String id);
    /**
     * 绑定标和文章
     */
    @Insert("insert into article_tag_mapper (article_id,tag_id,create_time) values(#{aid},#{tid},#{date})")
    public void bindArticleAndTag(String aid, String tid, Date date);
    /**
     * 设置tag的文章数加1
     */
    @Update("update tag SET article_count = article_count +1  WHERE aid = #{tid};")
    public void setTagForArticleCount( String tid);
    /**
     * category+1
     */
    @Update("update tag SET article_count = article_count +1  WHERE aid = #{tid};")
    public void setCategoryForArticleCount( String tid);
    /**
     * 删除标签和文章关联关系
     */
    @Delete("delete from article_tag_mapper where tag_id = #{tid}")
    public void deleteArticleAndTag(String tid);
    /**
     * 删除分类和文章关联关系
     */
    @Update("update  article set category_id = '' WHERE category_id =#{categoryId}")
    public void deleteArticleAndCateory(String categoryId);
    /**
     * 获取分类
     */
    @Select("select * from category where aid = #{categoryId}")
    @ResultMap(value = "category")
    public CategoryBean getCategory( String categoryId);
    /**
     * 获取标签
     */
    @Select("select * from tag where aid = #{tagId}")
    @ResultMap(value = "tag")
    public Tag getTag( String tagId);
    /**
     * 通过articleID获取tag
     */
    @Select("SELECT tag.* FROM tag ,article_tag_mapper WHERE tag.aid = article_tag_mapper.tag_id AND article_tag_mapper.article_id = #{articleId} ")
    @ResultMap(value = "tag")
    public List<Tag> getTagByArticleId( String articleId);
    /**
     * select friends
     */
    @Select("SELECT * from friends ,friends_type where friends.type_id = friends_type.id")
    public List<FriendsBean> getFriendsList();
    /**
     * select friends type
     */
    @Select("SELECT * from friends_type ")
    @Results(id="friends_type",value = {
            @Result(column = "type_name", property = "typeName", jdbcType = JdbcType.VARCHAR)
    }
    )
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

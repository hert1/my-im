package com.im.api.apiservice.article;


import com.github.pagehelper.PageInfo;
import com.im.api.dto.article.*;

import java.util.List;

/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
public interface IArticleService {
    /**\
     *获取文章集合
     * @return
     */
    public PageInfo<ArticleBean> getArticleList(BaseArticleBean articleList);
    /**\
     *搜索全部
     * @return
     */
    public List<ArticleBean> searchArticle(BaseArticleBean articleList);
    /**\
     *分页搜索
     * @return
     */
    public List<ArticleBean> searchArticleByKey(BaseArticleBean articleList, int page, int pageSize);
    /**\
     *获取最新文章
     * @return
     */
    public List<ArticleBean> getNewArticle();
    /**\
     *获取最新评论
     * @return
     */
    public List<CommentBean> getNewComment();

    /**
     * 查询文章数量
     * @return
     */
    public Integer getArticleNum( int status);

    /**
     * 查询所有分类
     * @return
     */
    public Integer getCategoryCount();
    /**
     * 查询所有标签
     * @return
     */
    public Integer getTagCount();
    /**
     * 查询所有回复
     * @return
     */
    public Integer getCommentsCount();

    /**
     * 添加分类
     * @return
     */
    public void addCategory(CategoryBean category);
    /**
     * 添加标签
     * @return
     */
    public void addTag(Tag tag);
    /**
     * 获取标签列表
     * @return
     */
    public PageInfo<Tag> getTagList();
    /**
     * 获取分类列表
     * @return
     */
    public PageInfo<CategoryBean> getCategoryList();

    /**
     * 删除标签
     */
    public void deleteTag( String tagId) ;

    /**
     * 删除分类
     */
    public void deleteCategory( String categoryId);
    /**
     * 修改分类
     */
    public void modifyCategory( String categoryId,String categoryName);
    /**
     * 修改标签
     */
    public void modifyTag(  String tagId,String tagName);
    /**
     * 发布文章
     */
    public void publArticle( ArticleBean articleBean,List<Tag> tags);
    /**
     * save文章
     */
    public void saveArticle( ArticleBean articleBean,List<Tag> tags);
    /**
     * edit文章
     */
    public void modifyArticle( ArticleBean articleBean,List<Tag> tags);
    /**
     * remove文章
     */
    public void deleteArticle(String id);

    /**
     * 绑定标和文章
     */
    public void bindArticleAndTag( String aid,String tid);
    /**
     * 获取分类
     */
    public CategoryBean getCategory( String categoryId);

    /**
     * 设置category的ArticleCount+1
     * @param cid
     */
    public void bindArticleAndCategory(String cid);
    /**
     * 获取标签
     */
    public Tag getTag( String tagId);
    /**
     * 通过articleID获取tag
     */
    public List<Tag> getTagByArticleId( String articleId);
    /**
     *
     */
    public PageInfo<FriendsBean> getFriendsList(int page, int pageSize);
    /**
     *
     */
    public List<FriendTypeList> getFriendTypeList();
    /**
     *添加friends
     */
    public void addFriends(String name,String url, int typeId);
    /**
     *删除friends
     */
    public void deleteFriend(String fid);
}

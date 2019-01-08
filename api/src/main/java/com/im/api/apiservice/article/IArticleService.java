package com.im.api.apiservice.article;


import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;

import java.util.List;

/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
public interface IArticleService {
    /**\
     *
     * @param num 页数
     * @param size 页面大小
     * @return
     */
    public List<ArticleBean> getArticleByNumAndSize(int num, int size,int status);

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
    public List<Tag> getTagList();
    /**
     * 获取分类列表
     * @return
     */
    public List<CategoryBean> getCategoryList();

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
    public void publArticle( ArticleBean articleBean);
    /**
     * 绑定标和文章
     */
    public void bindArticleAndTag( String aid,String tid);
}

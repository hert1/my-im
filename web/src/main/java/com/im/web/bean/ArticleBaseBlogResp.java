package com.im.web.bean;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.CommentBean;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/18
 * @since 1.0.0
 */
@Getter
@Setter
public class ArticleBaseBlogResp extends BaseInfoResp {

    private List<ArticleBean> newArticle;//最新发布的文章
    private List<CommentBean> newComment;//最新回复
    private List<ArticleBean> viewArticle;//点击排行
    private List<CategoryBean> categorys;//全部类别
}

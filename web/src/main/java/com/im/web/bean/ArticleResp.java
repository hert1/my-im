package com.im.web.bean;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Getter
@Setter
public class ArticleResp {

    private ArticleBean article;
    private CategoryBean category;
    private List<Tag> tags;
}

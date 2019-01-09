package com.im.web.bean;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/9
 * @since 1.0.0
 */
@Setter
@Getter
public class ArticleInfo {

    private ArticleBean article;//文章
    private List<Tag> tags;//标签
    private CategoryBean category;//类别

}

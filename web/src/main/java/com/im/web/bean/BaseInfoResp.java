package com.im.web.bean;

import com.im.api.dto.article.ArticleBean;
import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author viruser
 * @create 2019/1/18
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseInfoResp implements Serializable {
    private ArticleBean article;//文章
    private List<Tag> tags;//标签
    private CategoryBean category;//类别
    private Long count;//数目

}

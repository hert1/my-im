package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author viruser
 * @create 2019/1/9
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseArticleBean implements Serializable {
    private String by;//状态
    private String categoryId;//分类id
    private int page;
    private int pageSize;
    private int status;//文章状态
    private String tagId;
    private String searchValue;//搜索关键字
}

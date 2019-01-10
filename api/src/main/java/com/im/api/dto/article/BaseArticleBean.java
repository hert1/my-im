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
    private String by;
    private String categoryId;
    private int page;
    private int pageSize;
    private int status;
    private String tagId;
}
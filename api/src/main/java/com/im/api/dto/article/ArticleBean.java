package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author viruser
 * @create 2018/12/24
 * @since 1.0.0
 */
@Setter
@Getter
public class ArticleBean extends BaseBean {
    /**
     * 文章分类id
     */
    private String categoryId;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 封面图
     */
    private String cover;
    /**
     * 生成的html
     */
    private String htmlContent;
    /**
     * 文章id
     */
    private String aid;
    /**
     * 是否加密，0否，1是，默认0
     */
    private int isEncrypt;
    /**
     * 文章阅读数
     */
    private int pageview;
    /**
     * 文章简述
     */
    private String subMessage;
    /**
     * 内容标题
     */
    private String title;


}

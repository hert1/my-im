package com.im.web.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author viruser
 * @create 2019/1/9
 * @since 1.0.0
 */
@Setter
@Getter
public class GetArticleListReq {
    private String by;//方法
    private String categoryId;//分类id
    private int page;//页
    private int pageSize;//页面大小
    private int status;//文章状态
    private String tagId;//标签id
    private String searchValue;//搜索关键字


}

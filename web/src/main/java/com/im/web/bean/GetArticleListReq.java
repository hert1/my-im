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
    private String by;
    private String categoryId;
    private int page;
    private int pageSize;
    private int status;
    private String tagId;


}

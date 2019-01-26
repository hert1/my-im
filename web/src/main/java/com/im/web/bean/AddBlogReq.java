package com.im.web.bean;

import com.im.api.dto.article.CategoryBean;
import com.im.api.dto.article.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */
@Getter
@Setter
public class AddBlogReq {
    private String aid;
    private String content;
    private CategoryBean category;
    private String cover;
    private String htmlContent;
    private int isEncrypt;
    private String subMessage;
    private String title;
    private List<Tag> tags;

}

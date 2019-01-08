package com.im.web.bean;

import com.im.api.dto.article.ArticleBean;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Setter
@Getter
public class IndexResp {
    private List list;
    private int count;
}

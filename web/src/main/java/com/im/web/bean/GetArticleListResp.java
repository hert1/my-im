package com.im.web.bean;

import com.im.api.dto.article.ArticleBean;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author viruser
 * @create 2019/1/14
 * @since 1.0.0
 */
@Setter
@Getter
public class GetArticleListResp implements Serializable {

    private List list ;

    private Integer count;

}

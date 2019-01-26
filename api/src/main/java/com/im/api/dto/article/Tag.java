package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author viruser
 * @create 2018/12/25
 * @since 1.0.0
 */
@Setter
@Getter
public class Tag extends BaseBean {
    /**
     * id
     */
    private String aid;

    /**
     * 该标签的文章数量
     */
    private int articleCount;
    private int length;
}

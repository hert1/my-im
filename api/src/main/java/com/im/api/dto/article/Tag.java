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
public class Tag implements Serializable {
    /**
     * post表主键
     */
    private Integer aid;
    /**
     * id
     */
    private String id;

    /**
     * 标签名称
     */
    private String name;
    /**
     * 更新时间
     */
    private Date update_time;
    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * 状态，0-正常（发布），1-删除，2-记录（待发布）
     */
    private int status;
    /**
     * 该标签的文章数量
     */
    private int article_count;
    private int length;
}

package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Getter
@Setter
public class CategoryBean implements Serializable {
    /**
     * post表主键
     */
    private Integer aid;
    /**
     * 该分类的文章数量
     */
    private int article_count;
    /**
     * 0表示不可删除，1表示可删除，默认1
     */
    private int can_del;
    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * id
     */
    private String id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 状态，0-正常（发布），1-删除，2-记录（待发布）
     */
    private int status;
    /**
     * 更新时间
     */
    private Date update_time;


}

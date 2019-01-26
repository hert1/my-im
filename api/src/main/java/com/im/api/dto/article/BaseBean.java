package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author viruser
 * @create 2019/1/25
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseBean implements Serializable {

    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除时间
     */
    private Date deleteTime;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态，0-正常（发布），1-删除，2-记录（待发布）
     */
    private int status;
    private int page;
    private int pageSize;
}

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
public class CategoryBean extends BaseBean{
    /**
     * 该分类的文章数量
     */
    private int articleCount;
    /**
     * 0表示不可删除，1表示可删除，默认1
     */
    private int canDel;
    /**
     * id
     */
    private String aid;


}

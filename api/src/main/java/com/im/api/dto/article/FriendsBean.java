package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author viruser
 * @create 2019/1/9
 * @since 1.0.0
 */
@Setter
@Getter
public class FriendsBean extends BaseBean {
    /**
     *友链id
     */
    private String friendId;
    /**
     *对应链接
     */
    private String url;
    /**
     *所属分类id
     */
    private int typeId;
    /**
     *所属分类name
     */
    private String typeName;


}

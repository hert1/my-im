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
public class FriendsBean implements Serializable {
    /**
     *id
     */
    private int aid;
    /**
     *友链id
     */
    private String friend_id;
    /**
     *友链名称
     */
    private String name;
    /**
     *对应链接
     */
    private String url;
    /**
     *创建时间
     */
    private Date create_time;
    /**
     *更新时间
     */
    private Date update_time;
    /**
     *删除时间
     */
    private Date delete_time;
    /**
     *状态，0表示可用，1表示删除，默认0
     */
    private int status;
    /**
     *所属分类id
     */
    private int type_id;
    /**
     *所属分类name
     */
    private String type_name;


}

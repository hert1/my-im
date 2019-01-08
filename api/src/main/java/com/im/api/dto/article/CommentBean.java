package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author viruser
 * @create 2018/12/25
 * @since 1.0.0
 */
@Setter
@Getter
public class CommentBean implements Serializable {
    /**
     * 文章id
     */
    private String article_id;
    /**
     * 评论内容json
     */
    private String content;
    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * 删除时间
     */
    private Date delete_time;
    /**
     * 评论者邮箱
     */
    private String email;
    private Integer id;
    /**
     * 是否是作者，0否，1是，默认0
     */
    private int is_author;
    /**
     * 评论者名称
     */
    private String name;
    /**
     * 父id, 默认0
     */
    private int parent_id;
    /**
     * 回复的评论id
     */
    private int reply_id;
    /**
     * 评论内容（原始内容）
     */
    private String source_content;
    /**
     * 状态，0正常，1删除，默认0
     */
    private int status;

    private List<CommentBean> children;


}

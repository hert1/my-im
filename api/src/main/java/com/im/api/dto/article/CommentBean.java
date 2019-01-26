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
public class CommentBean extends BaseBean {
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 评论内容json
     */
    private String content;
    /**
     * 评论者邮箱
     */
    private String email;
    /**
     * 是否是作者，0否，1是，默认0
     */
    private int isAuthor;
    /**
     * 父id, 默认0
     */
    private int parentId;
    /**
     * 回复的评论id
     */
    private int replyId;
    /**
     * 评论内容（原始内容）
     */
    private String sourceContent;

    private List<CommentBean> children;


}

package com.im.web.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Getter
@Setter
public class AddCommentReq {

    private String articleId;
    private String name;
    private int replyId;
    private String content;
    private String sourceContent;
    private String email;
    private String ticket;
    private String randstr;
    private int parentId;
}

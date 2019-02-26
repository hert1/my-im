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
public class BlogInfo extends BaseInfoResp{
    private Long articleCount;
    private String blogName;
    private Long categoryCount;
    private String sign;
    private Long tagCount;

}

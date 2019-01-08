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
public class BlogInfo {
    private Integer articleCount;
    private String blogName;
    private Integer categoryCount;
    private String sign;
    private Integer tagCount;

}

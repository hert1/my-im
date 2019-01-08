package com.im.web.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Setter
@Getter
public class InfoResp {
    private Integer articleCount;
    private BlogInfo blogInfo;
    private Integer categoryCount;
    private Integer tagCount;


}

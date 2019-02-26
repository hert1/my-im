package com.im.web.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */
@Getter
@Setter
public class StatisticsResp implements Serializable {

    private Long publishCount;
    private Long draftsCount;
    private Long deletedCount;
    private Long categoryCount;
    private Long tagCount;
    private Long commentsCount;
}

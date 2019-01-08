package com.im.api.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author viruser
 * @create 2019/1/8
 * @since 1.0.0
 */
@Setter
@Getter
public class AboutMeBean implements Serializable {
    /**
     * 生成的html内容
     */
    private String html;
    private int id;
    /**
     * markdown内容
     */
    private String md;
    /**
     * 页面名称
     */
    private String type;
}

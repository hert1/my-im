package com.im.web.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Setter
@Getter
public class IndexResp {
    private List list;
    private int count;
}

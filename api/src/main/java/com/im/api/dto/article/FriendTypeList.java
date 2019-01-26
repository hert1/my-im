package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author viruser
 * @create 2019/1/9
 * @since 1.0.0
 */
@Getter
@Setter
public class FriendTypeList extends BaseBean {
    private int count;
    private String typeName;

}

package com.im.service.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author viruser
 * @create 2018/12/29
 * @since 1.0.0
 */
@Setter
@Getter
@Component
public class ConfigBean {
    /**
     * 设置cache时间
     */
    @Value("${com.im.cache.time}")
    private String cacheTime;

}

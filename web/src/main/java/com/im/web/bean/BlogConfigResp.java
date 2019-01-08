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
public class BlogConfigResp implements Serializable {
    /**
     * 支付宝支付二维码
     */
    private String alipayQrcode;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 博客名称
     */
    private String blogName;
    /**
     * github
     */
    private String github;
    private int id;
    /**
     * 阅读加密秘钥
     */
    private String salt;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 阅读加密密码
     */
    private String viewPassword;
    /**
     * 微信支付二维码
     */
    private String wxpayQrcode;
    private Boolean hadOldPassword;
}

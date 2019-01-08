package com.im.api.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author viruser
 * @create 2019/1/7
 * @since 1.0.0
 */
@Getter
@Setter
public class BlogInfoBean implements Serializable {
    /**
     * 支付宝支付二维码
     */
    private String alipay_qrcode;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 博客名称
     */
    private String blog_name;
    /**
     * github
     */
    private String github;
    /**
     * id
     */
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
    private String view_password;
    /**
     * 微信支付二维码
     */
    private String wxpay_qrcode;
}

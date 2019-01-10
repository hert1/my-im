package com.im.api.dto.user;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Setter
@Getter
public class User  implements Serializable {
    private int aid;
    /**
     * 用户id
     */
    private String user_id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 秘钥
     */
    private String salt;
    /**
     * access_token
     */
    private String access_token;
    /**
     * token过期时间，单位为秒
     */
    private int token_expires_in;
    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * 状态，0为正常，默认0
     */
    private int status;
    /**
     * 最后登录时间
     */
    private int last_login_time;

}

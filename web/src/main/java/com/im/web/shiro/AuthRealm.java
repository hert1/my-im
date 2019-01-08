/*
package com.im.web.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.im.api.apiservice.user.IUserService;
import com.im.api.dto.user.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

*/
/**
 * @author viruser
 * @create 2019/1/4
 * @since 1.0.0
 *//*

public class AuthRealm extends AuthorizingRealm {

    @Reference
    IUserService userService;

    */
/**
     * 登录，认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     *//*

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.queryUserByUname(username);
        return new SimpleAuthenticationInfo(user, user.getUpwd(), this.getClass().getName());
    }

    */
/**
     * 授权
     *
     * @param principalCollection
     * @return
     *//*

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
*/

package com.quainhanhub.vue_blog.shiro_jwt.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.quainhanhub.vue_blog.autoGen.entity.User;
import com.quainhanhub.vue_blog.autoGen.service.UserService;
import com.quainhanhub.vue_blog.shiro_jwt.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AccountRealm is the core logic of shiro ti sign in or authentication.
 * There are 3 functions need overriding
 * 1. supports: help realm to support jwt.
 * 2. doGetAuthorizationInfo: Permission Check.
 * 3. doGetAuthenticationInfo: Sign in Check.
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //Use instanceof to check if it is token we want.
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { return null; }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwt = (JwtToken) token;

        log.info("jwt----------------->{}", jwt);
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();

        User user = userService.getById(Long.parseLong(userId));
        if(user == null) {
            throw new UnknownAccountException("Account is not exist！");
        }

        if(user.getStatus() == -1) {
            throw new LockedAccountException("Account is locked！");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        log.info("profile----------------->{}", profile.toString());
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}

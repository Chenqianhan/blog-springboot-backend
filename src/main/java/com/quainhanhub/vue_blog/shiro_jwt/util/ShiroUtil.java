package com.quainhanhub.vue_blog.shiro_jwt.util;

import com.quainhanhub.vue_blog.shiro_jwt.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}

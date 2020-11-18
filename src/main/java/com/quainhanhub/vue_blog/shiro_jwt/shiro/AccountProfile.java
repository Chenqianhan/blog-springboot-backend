package com.quainhanhub.vue_blog.shiro_jwt.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {
    private Long userId;
    private String username;
    private String email;
}
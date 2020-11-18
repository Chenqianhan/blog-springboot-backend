package com.quainhanhub.vue_blog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quainhanhub.vue_blog.autoGen.entity.User;
import com.quainhanhub.vue_blog.autoGen.service.UserService;
import com.quainhanhub.vue_blog.common.dto.LoginDto;
import com.quainhanhub.vue_blog.common.lang.Result;
import com.quainhanhub.vue_blog.shiro_jwt.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Login logic
 */
@RestController
public class AccountController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    //We defined restriction in dto. Then use LoginDto.java serialize requestBody the @Validated to validate it.
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        //Check username
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        //If it is null, which means username not exist. Then notNull() throws IllegalArgumentException.
        Assert.notNull(user, "account not exist");

        //Cause I didn't preprocess the password in database in advance.
        //if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
        if(!user.getPassword().equals(loginDto.getPassword())){
            return Result.fail("Password incorrect");
        }

        //Set session.
        String jwt = jwtUtils.generateToken(user.getUserId());
        //Place jwt in header of response. Then if we don't have to call another api to check token.
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Export-Header", "Authorization");

        return Result.success(MapUtil.builder()
                .put("userId", user.getUserId())
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .map());
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}

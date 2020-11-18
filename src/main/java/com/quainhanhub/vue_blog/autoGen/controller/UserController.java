package com.quainhanhub.vue_blog.autoGen.controller;


import com.quainhanhub.vue_blog.autoGen.entity.User;
import com.quainhanhub.vue_blog.common.lang.Result;
import com.quainhanhub.vue_blog.autoGen.service.UserService;
import com.quainhanhub.vue_blog.shiro_jwt.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author Quainhan Chen
 * @since 2020-11-14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //By this annotation, it requires authentication to request this api
    @RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        //System.out.println(ShiroUtil.getProfile().getUserId());
        return Result.success(userService.getById(ShiroUtil.getProfile().getUserId()));
    }

    //@Validated help us validate user by requirements in User. Exception will be handled as Assert exception.
    //Incomplete, we didn't insert it in database in fact.
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        userService.saveOrUpdate(user);
        return Result.success(user);
    }
}

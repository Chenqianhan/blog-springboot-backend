package com.quainhanhub.vue_blog.autoGen.controller;

import com.quainhanhub.vue_blog.autoGen.entity.Blog;
import com.quainhanhub.vue_blog.autoGen.service.BlogService;
import com.quainhanhub.vue_blog.common.lang.Result;
import com.quainhanhub.vue_blog.shiro_jwt.util.ShiroUtil;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Quainhan Chen
 * @since 2020-11-14
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        //We set size = 10.
        Page page = new Page(currentPage, 10);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("date"));

        return Result.success(pageData);
    }

    @GetMapping("/{blogId}")
    public Result detail(@PathVariable(name = "blogId") Long blogId) {
        Blog blog = blogService.getById(blogId);
        Assert.notNull(blog, "Blog not exist");

        return Result.success(null);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog tmp;
        System.out.println(ShiroUtil.getProfile().getUserId());
        if (blog.getBlogId() != null) {
            tmp = blogService.getById(blog.getBlogId());
            //Can only edit personal article
            System.out.println(ShiroUtil.getProfile().getUserId());
            Assert.isTrue(tmp.getUserId().longValue() == ShiroUtil.getProfile().getUserId().longValue()
                    , "No permission to edit");
        } else {
            tmp = new Blog();
            tmp.setUserId(ShiroUtil.getProfile().getUserId());
            tmp.setDate(LocalDateTime.now());
            tmp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, tmp, "blogId", "userId", "date", "status");
        blogService.saveOrUpdate(tmp);

        return Result.success(null);
    }
}

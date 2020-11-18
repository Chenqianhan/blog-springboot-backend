package com.quainhanhub.vue_blog.config;
//package main.java.com.quainhanhub.vue_blog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//Set mapper API scanner, add paging plugin.
//Before running the VueBlogApplication, please run CodeGenerator first to generate corresponding code.
@Configuration
@EnableTransactionManagement
@MapperScan("com.quainhanhub.vue_blog.autoGen.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}

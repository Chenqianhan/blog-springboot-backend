package com.quainhanhub.vue_blog.autoGen.service.impl;

import com.quainhanhub.vue_blog.autoGen.entity.User;
import com.quainhanhub.vue_blog.autoGen.mapper.UserMapper;
import com.quainhanhub.vue_blog.autoGen.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quainhan Chen
 * @since 2020-11-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

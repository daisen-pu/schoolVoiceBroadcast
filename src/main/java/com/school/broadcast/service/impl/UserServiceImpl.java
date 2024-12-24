package com.school.broadcast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.broadcast.entity.User;
import com.school.broadcast.mapper.UserMapper;
import com.school.broadcast.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getUserByOpenId(String openId) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenId, openId));
    }

    @Override
    public User login(String username, String password) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password));
    }

    @Override
    public void updateUserInfo(User user) {
        updateById(user);
    }

    @Override
    public Page<User> getUserList(String name, String phone, String schoolId, Page<User> page) {
        return page(page, new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(name), User::getRealName, name)
                .like(StringUtils.hasText(phone), User::getPhone, phone)
                .eq(StringUtils.hasText(schoolId), User::getSchoolId, schoolId)
                .eq(User::getRole, "USER")
                .orderByDesc(User::getCreateTime));
    }

    @Override
    public void updatePassword(String userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (user == null || !oldPassword.equals(user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        user.setPassword(newPassword);
        updateById(user);
    }
} 
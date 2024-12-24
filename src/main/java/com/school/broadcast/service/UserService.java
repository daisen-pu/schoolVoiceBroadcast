package com.school.broadcast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.broadcast.entity.User;

public interface UserService extends IService<User> {
    /**
     * 根据openId获取用户
     */
    User getUserByOpenId(String openId);

    /**
     * 管理员登录
     */
    User login(String username, String password);

    /**
     * 更新用户信息
     */
    void updateUserInfo(User user);

    /**
     * 分页查询用户列表
     */
    Page<User> getUserList(String name, String phone, String schoolId, Page<User> page);

    /**
     * 修改密码
     */
    void updatePassword(String userId, String oldPassword, String newPassword);
} 
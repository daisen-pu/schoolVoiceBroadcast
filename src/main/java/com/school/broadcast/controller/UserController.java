package com.school.broadcast.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.broadcast.common.Result;
import com.school.broadcast.entity.User;
import com.school.broadcast.service.UserService;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public Result<Page<User>> list(@RequestParam(defaultValue = "1") Integer current,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String phone,
                                 @RequestParam(required = false) String schoolId) {
        Page<User> page = new Page<>(current, size);

        return Result.success(userService.getUserList(name, phone, schoolId, page));
    }

    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable String id) {
        return Result.success(userService.getById(id));
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userService.updateUserInfo(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        userService.removeById(id);
        return Result.success();
    }

    @PostMapping("/change-password")
    public Result<?> changePassword(@RequestParam String oldPassword,
                                  @RequestParam String newPassword) {
        String userId = StpUtil.getLoginIdAsString();
        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success();
    }
}

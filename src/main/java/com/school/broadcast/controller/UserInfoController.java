package com.school.broadcast.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.school.broadcast.common.Result;
import com.school.broadcast.entity.Children;
import com.school.broadcast.entity.User;
import com.school.broadcast.service.ChildrenService;
import com.school.broadcast.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserService userService;  // 用户服务
    private final ChildrenService childrenService;  // 孩子服务 

    @GetMapping("/info")
    public Result<User> getUserInfo() {
        String userId = StpUtil.getLoginIdAsString();
        return Result.success(userService.getById(userId));
    }

    @PutMapping("/info")
    public Result<?> updateUserInfo(@RequestBody User user) {
        String userId = StpUtil.getLoginIdAsString();
        user.setId(userId);
        userService.updateUserInfo(user);
        return Result.success();
    }

    @PostMapping("/bind-children")
    public Result<?> bindChildren(@RequestBody Children children) {
        String userId = StpUtil.getLoginIdAsString();
        children.setUserId(userId);
        childrenService.save(children);
        return Result.success();
    }

    @PutMapping("/update-children")
    public Result<?> updateChildren(@RequestBody Children children) {
        String userId = StpUtil.getLoginIdAsString();
        Children exist = childrenService.getById(children.getId());
        if (exist == null || !userId.equals(exist.getUserId())) {
            return Result.error("无权操作");
        }
        childrenService.updateById(children);
        return Result.success();
    }

    @DeleteMapping("/children/{id}")
    public Result<?> deleteChildren(@PathVariable String id) {
        String userId = StpUtil.getLoginIdAsString();
        Children exist = childrenService.getById(id);
        if (exist == null || !userId.equals(exist.getUserId())) {
            return Result.error("无权操作");
        }
        childrenService.removeById(id);
        return Result.success();
    }
}

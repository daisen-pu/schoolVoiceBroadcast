package com.school.broadcast.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.StpUtil;

import com.alibaba.fastjson.JSONObject;
import com.school.broadcast.common.Result;
import com.school.broadcast.entity.User;
import com.school.broadcast.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class    AuthController {
    private final WxMaService wxMaService;
    private final UserService userService;

    @PostMapping("/wx-login")
    public Result<?> wxLogin(@RequestParam String code) {
        try {
            // 获取微信用户openId
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            String openId = session.getOpenid();

            // 查询或创建用户
            User user = userService.getUserByOpenId(openId);
            if (user == null) {
                user = new User();
                user.setOpenId(openId);
                user.setRole("USER");
                userService.save(user);
            }

            // 登录并返回token
            StpUtil.login(user.getId());
            return Result.success(StpUtil.getTokenValue());
        } catch (Exception e) {
            return Result.error("微信登录失败：" + e.getMessage());
        }
    }

    @PostMapping("/admin/login")
    public Result<?> adminLogin(@RequestBody JSONObject body) {
        if (body == null || !body.containsKey("username") || !body.containsKey("password")) {
            return Result.error("用户名或密码为空");
        }
        String username = body.getString("username");
        String password = body.getString("password");
        User user = userService.login(username, password);
        if (user != null && "ADMIN".equals(user.getRole())) {
            StpUtil.login(user.getId());
            return Result.success(StpUtil.getTokenValue());
        }
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.success();
    }
}

package com.school.broadcast.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 管理端接口，登录校验
            SaRouter.match("/admin/**", r -> StpUtil.checkLogin());
            
            // 用户端接口，登录校验
            SaRouter.match("/api/**", r -> {
                // 排除登录接口
                SaRouter.match("/api/auth/**", () -> {}).stop();
                StpUtil.checkLogin();
            });
        })).addPathPatterns("/**");
    }
}

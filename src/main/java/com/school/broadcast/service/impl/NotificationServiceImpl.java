package com.school.broadcast.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage.MsgData;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.broadcast.entity.Children;
import com.school.broadcast.entity.Notification;
import com.school.broadcast.entity.User;
import com.school.broadcast.mapper.NotificationMapper;
import com.school.broadcast.service.ChildrenService;
import com.school.broadcast.service.NotificationService;
import com.school.broadcast.service.UserService;
import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    private final WxMaService wxMaService;
    private final UserService userService;
    private final ChildrenService childrenService;

    @Override
    public Page<Notification> getNotificationList(Page<Notification> page, String schoolId, String gradeId, String classId) {
        return page(page, new LambdaQueryWrapper<Notification>()
                .eq(StringUtils.hasText(schoolId), Notification::getSchoolId, schoolId)
                .eq(StringUtils.hasText(gradeId), Notification::getGradeId, gradeId)
                .eq(StringUtils.hasText(classId), Notification::getClassId, classId)
                .orderByDesc(Notification::getCreateTime));
    }

    @Override
    @Transactional
    public void createNotification(Notification notification) {
        save(notification);
    }

    @Override
    @Transactional
    public void sendNotification(String id) {
        Notification notification = getById(id);
        if (notification == null) {
            throw new RuntimeException("通知不存在");
        }

        // 获取需要发送通知的用户列表
        List<Children> children;
        if (StringUtils.hasText(notification.getClassId())) {
            children = childrenService.getByClassId(notification.getClassId());
        } else if (StringUtils.hasText(notification.getGradeId())) {
            children = childrenService.getByGradeId(notification.getGradeId());
        } else if (StringUtils.hasText(notification.getSchoolId())) {
            children = childrenService.getBySchoolId(notification.getSchoolId());
        } else {
            throw new RuntimeException("通知范围未指定");
        }

        // 获取用户列表
        List<String> userIds = children.stream()
                .map(Children::getUserId)
                .distinct()
                .collect(Collectors.toList());

        List<User> users = userService.listByIds(userIds);

        // 发送微信通知
        for (User user : users) {
            try {
                if (StringUtils.hasText(user.getOpenId())) {
                    // 发送微信服务通知
                    wxMaService.getMsgService().sendSubscribeMsg(WxMaSubscribeMessage.builder()
                            .toUser(user.getOpenId())
                            .templateId("your_template_id") // 需要替换为实际的模板ID
                            .data(Lists.newArrayList(
                                    new MsgData("thing1", notification.getTitle()),
                                    new MsgData("thing2", notification.getContent())
                            ))
                            .build());
                }
            } catch (Exception e) {
                log.error("发送微信通知失败：" + e.getMessage());
            }
        }

        // 更新通知状态
        notification.setStatus("1");
        updateById(notification);
    }

    @Override
    public Page<Notification> getUserNotifications(String userId, Page<Notification> page) {
        // 获取用户的子女信息
        List<Children> children = childrenService.getByUserId(userId);
        if (children.isEmpty()) {
            return page;
        }

        // 构建查询条件
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        children.forEach(child -> {
            wrapper.or(w -> w
                    .eq(Notification::getClassId, child.getClassId())
                    .or()
                    .eq(Notification::getGradeId, child.getGradeId())
                    .or()
                    .eq(Notification::getSchoolId, child.getSchoolId())
            );
        });
        wrapper.orderByDesc(Notification::getCreateTime);

        return page(page, wrapper);
    }
}

package com.school.broadcast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.broadcast.entity.Notification;

public interface NotificationService extends IService<Notification> {
    /**
     * 分页查询通知列表
     */
    Page<Notification> getNotificationList(Page<Notification> page, String schoolId, String gradeId, String classId);

    /**
     * 创建通知
     */
    void createNotification(Notification notification);

    /**
     * 发送通知
     */
    void sendNotification(String id);

    /**
     * 获取用户的通知列表
     */
    Page<Notification> getUserNotifications(String userId, Page<Notification> page);
}

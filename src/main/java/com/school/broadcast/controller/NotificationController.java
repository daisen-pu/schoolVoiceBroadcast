package com.school.broadcast.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.broadcast.common.Result;
import com.school.broadcast.entity.Notification;
import com.school.broadcast.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/list")
    public Result<Page<Notification>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         @RequestParam(required = false) String schoolId,
                                         @RequestParam(required = false) String gradeId,
                                         @RequestParam(required = false) String classId) {
        Page<Notification> page = new Page<>(current, size);
        return Result.success(notificationService.getNotificationList(page, schoolId, gradeId, classId));
    }

    @PostMapping
    public Result<?> create(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
        return Result.success();
    }

    @PostMapping("/send/{id}")
    public Result<?> send(@PathVariable String id) {
        notificationService.sendNotification(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        notificationService.removeById(id);
        return Result.success();
    }
}

package com.school.broadcast.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_notification")
public class Notification {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String title;
    private String content;
    private String schoolId;
    private String gradeId;
    private String classId;
    private String status;
    
    @TableLogic
    private Boolean deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableField(exist = false)
    private String schoolName;
    
    @TableField(exist = false)
    private String gradeName;
    
    @TableField(exist = false)
    private String className;
}

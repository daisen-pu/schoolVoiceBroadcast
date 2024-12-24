package com.school.broadcast.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_children")
public class Children {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String userId;
    private String name;
    private String gender;
    private String schoolId;
    private String gradeId;
    private String classId;
    
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

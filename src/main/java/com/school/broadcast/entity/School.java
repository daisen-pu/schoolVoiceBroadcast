package com.school.broadcast.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_school")
public class School {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String name;
    
    @TableLogic
    private Boolean deleted;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

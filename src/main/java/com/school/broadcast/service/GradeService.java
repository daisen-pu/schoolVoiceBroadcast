package com.school.broadcast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.broadcast.entity.Grade;
import java.util.List;

public interface GradeService extends IService<Grade> {
    /**
     * 分页查询年级列表
     */
    Page<Grade> getGradeList(Page<Grade> page, String schoolId, String name);

    /**
     * 根据学校ID获取年级列表
     */
    List<Grade> getBySchoolId(String schoolId);
}

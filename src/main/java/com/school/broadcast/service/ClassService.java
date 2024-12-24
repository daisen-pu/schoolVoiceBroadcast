package com.school.broadcast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.broadcast.entity.ClassEntity;

import java.util.List;

public interface ClassService extends IService<ClassEntity> {
    /**
     * 分页查询班级列表
     */
    Page<ClassEntity> getClassList(Page<ClassEntity> page, String gradeId, String name);

    /**
     * 根据年级ID获取班级列表
     */
    List<ClassEntity> getByGradeId(String gradeId);
}

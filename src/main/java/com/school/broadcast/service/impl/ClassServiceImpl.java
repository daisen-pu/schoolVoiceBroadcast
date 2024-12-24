package com.school.broadcast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.broadcast.entity.ClassEntity;
import com.school.broadcast.mapper.ClassMapper;
import com.school.broadcast.service.ClassService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, ClassEntity> implements ClassService {

    @Override
    public Page<ClassEntity> getClassList(Page<ClassEntity> page, String gradeId, String name) {
        return page(page, new LambdaQueryWrapper<ClassEntity>()
                .eq(StringUtils.hasText(gradeId), ClassEntity::getGradeId, gradeId)
                .like(StringUtils.hasText(name), ClassEntity::getName, name)
                .orderByDesc(ClassEntity::getCreateTime));
    }

    @Override
    public List<ClassEntity> getByGradeId(String gradeId) {
        return list(new LambdaQueryWrapper<ClassEntity>()
                .eq(ClassEntity::getGradeId, gradeId)
                .orderByAsc(ClassEntity::getName));
    }
}

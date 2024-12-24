package com.school.broadcast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.broadcast.entity.Grade;
import com.school.broadcast.mapper.GradeMapper;
import com.school.broadcast.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public Page<Grade> getGradeList(Page<Grade> page, String schoolId, String name) {
        return page(page, new LambdaQueryWrapper<Grade>()
                .eq(StringUtils.hasText(schoolId), Grade::getSchoolId, schoolId)
                .like(StringUtils.hasText(name), Grade::getName, name)
                .orderByDesc(Grade::getCreateTime));
    }

    @Override
    public List<Grade> getBySchoolId(String schoolId) {
        return list(new LambdaQueryWrapper<Grade>()
                .eq(Grade::getSchoolId, schoolId)
                .orderByAsc(Grade::getName));
    }
}

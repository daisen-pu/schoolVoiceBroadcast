package com.school.broadcast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.broadcast.entity.Children;
import com.school.broadcast.mapper.ChildrenMapper;
import com.school.broadcast.service.ChildrenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenServiceImpl extends ServiceImpl<ChildrenMapper, Children> implements ChildrenService {

    @Override
    public List<Children> getByUserId(String userId) {
        return list(new LambdaQueryWrapper<Children>()
                .eq(Children::getUserId, userId)
                .orderByDesc(Children::getCreateTime));
    }

    @Override
    public List<Children> getByClassId(String classId) {
        return list(new LambdaQueryWrapper<Children>()
                .eq(Children::getClassId, classId)
                .orderByDesc(Children::getCreateTime));
    }

    @Override
    public List<Children> getByGradeId(String gradeId) {
        return list(new LambdaQueryWrapper<Children>()
                .eq(Children::getGradeId, gradeId)
                .orderByDesc(Children::getCreateTime));
    }

    @Override
    public List<Children> getBySchoolId(String schoolId) {
        return list(new LambdaQueryWrapper<Children>()
                .eq(Children::getSchoolId, schoolId)
                .orderByDesc(Children::getCreateTime));
    }
}

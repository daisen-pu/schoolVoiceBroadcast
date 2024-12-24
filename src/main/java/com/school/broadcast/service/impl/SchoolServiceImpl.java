package com.school.broadcast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.broadcast.entity.School;
import com.school.broadcast.mapper.SchoolMapper;
import com.school.broadcast.service.SchoolService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    @Override
    public Page<School> getSchoolList(Page<School> page, String name) {
        return page(page, new LambdaQueryWrapper<School>()
                .like(StringUtils.hasText(name), School::getName, name)
                .orderByDesc(School::getCreateTime));
    }
}

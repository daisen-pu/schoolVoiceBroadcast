package com.school.broadcast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.broadcast.entity.School;

public interface SchoolService extends IService<School> {
    /**
     * 分页查询学校列表
     */
    Page<School> getSchoolList(Page<School> page, String name);
}

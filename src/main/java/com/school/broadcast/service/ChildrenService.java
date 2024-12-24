package com.school.broadcast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.broadcast.entity.Children;
import java.util.List;

public interface ChildrenService extends IService<Children> {
    /**
     * 根据用户ID获取子女列表
     */
    List<Children> getByUserId(String userId);

    /**
     * 根据班级ID获取子女列表
     */
    List<Children> getByClassId(String classId);

    /**
     * 根据年级ID获取子女列表
     */
    List<Children> getByGradeId(String gradeId);

    /**
     * 根据学校ID获取子女列表
     */
    List<Children> getBySchoolId(String schoolId);
}

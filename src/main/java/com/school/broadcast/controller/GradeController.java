package com.school.broadcast.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.broadcast.entity.Grade;
import com.school.broadcast.common.Result;
import com.school.broadcast.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/grade")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @GetMapping("/list")
    public Result<Page<Grade>> list(@RequestParam(defaultValue = "1") Integer current,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(required = false) String schoolId,
                                  @RequestParam(required = false) String name) {
        Page<Grade> page = new Page<>(current, size);
        return Result.success(gradeService.getGradeList(page, schoolId, name));
    }

    @GetMapping("/by-school")
    public Result<?> getBySchool(@RequestParam String schoolId) {
        return Result.success(gradeService.getBySchoolId(schoolId));
    }

    @PostMapping
    public Result<?> save(@RequestBody Grade grade) {
        gradeService.save(grade);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Grade grade) {
        gradeService.updateById(grade);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        gradeService.removeById(id);
        return Result.success();
    }
}

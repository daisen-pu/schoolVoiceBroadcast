package com.school.broadcast.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.broadcast.common.Result;
import com.school.broadcast.entity.ClassEntity;
import com.school.broadcast.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/class")
@RequiredArgsConstructor
public class MyClassController {
    private final ClassService classService;

    @GetMapping("/list")
    public Result<Page<ClassEntity>> list(@RequestParam(defaultValue = "1") Integer current,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  @RequestParam(required = false) String gradeId,
                                  @RequestParam(required = false) String name) {
        Page<ClassEntity> page = new Page<>(current, size);
        return Result.success(classService.getClassList(page, gradeId, name));
    }

    @GetMapping("/by-grade")
    public Result<?> getByGrade(@RequestParam String gradeId) {
        return Result.success(classService.getByGradeId(gradeId));
    }

    @PostMapping
    public Result<?> save(@RequestBody ClassEntity clazz) {
        classService.save(clazz);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ClassEntity clazz) {
        classService.updateById(clazz);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        classService.removeById(id);
        return Result.success();
    }
}

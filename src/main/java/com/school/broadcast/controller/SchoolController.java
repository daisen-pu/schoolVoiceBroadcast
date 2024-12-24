package com.school.broadcast.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.broadcast.common.Result;
import com.school.broadcast.entity.School;
import com.school.broadcast.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/school")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;

    @GetMapping("/list")
    public Result<Page<School>> list(@RequestParam(defaultValue = "1") Integer current,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @RequestParam(required = false) String name) {
        Page<School> page = new Page<>(current, size);
        return Result.success(schoolService.getSchoolList(page, name));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(schoolService.list());
    }

    @PostMapping
    public Result<?> save(@RequestBody School school) {
        schoolService.save(school);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody School school) {
        schoolService.updateById(school);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        schoolService.removeById(id);
        return Result.success();
    }
}

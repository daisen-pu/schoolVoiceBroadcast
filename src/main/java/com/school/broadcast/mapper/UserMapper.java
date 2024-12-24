package com.school.broadcast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.broadcast.entity.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
} 
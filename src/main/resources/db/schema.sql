-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` varchar(32) NOT NULL COMMENT '主键ID',
    `open_id` varchar(64) DEFAULT NULL COMMENT '微信openId',
    `username` varchar(50) DEFAULT NULL COMMENT '用户名',
    `password` varchar(100) DEFAULT NULL COMMENT '密码',
    `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
    `gender` char(1) DEFAULT NULL COMMENT '性别：M-男，F-女',
    `school_id` varchar(32) NOT NULL COMMENT '学校ID',
    `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
    `role` varchar(10) NOT NULL DEFAULT 'USER' COMMENT '角色：ADMIN-管理员，USER-普通用户',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_school_id` (`school_id`),
    UNIQUE KEY `uk_open_id` (`open_id`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 学校表
CREATE TABLE IF NOT EXISTS `sys_school` (
    `id` varchar(32) NOT NULL COMMENT '主键ID',
    `name` varchar(100) NOT NULL COMMENT '学校名称',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学校表';

-- 年级表
CREATE TABLE IF NOT EXISTS `sys_grade` (
    `id` varchar(32) NOT NULL COMMENT '主键ID',
    `school_id` varchar(32) NOT NULL COMMENT '学校ID',
    `name` varchar(50) NOT NULL COMMENT '年级名称',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_school_id` (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='年级表';

-- 班级表
CREATE TABLE IF NOT EXISTS `sys_class` (
    `id` varchar(32) NOT NULL COMMENT '主键ID',
    `grade_id` varchar(32) NOT NULL COMMENT '年级ID',
    `name` varchar(50) NOT NULL COMMENT '班级名称',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_grade_id` (`grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 子女信息表
CREATE TABLE IF NOT EXISTS `sys_children` (
    `id` varchar(32) NOT NULL COMMENT '主键ID',
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `name` varchar(50) NOT NULL COMMENT '姓名',
    `gender` char(1) DEFAULT NULL COMMENT '性别：M-男，F-女',
    `school_id` varchar(32) NOT NULL COMMENT '学校ID',
    `grade_id` varchar(32) NOT NULL COMMENT '年级ID',
    `class_id` varchar(32) NOT NULL COMMENT '班级ID',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_school_id` (`school_id`),
    KEY `idx_grade_id` (`grade_id`),
    KEY `idx_class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子女信息表';

-- 消息通知表
CREATE TABLE IF NOT EXISTS `sys_notification` (
    `id` varchar(32) NOT NULL COMMENT '主键ID',
    `title` varchar(100) NOT NULL COMMENT '通知标题',
    `content` text NOT NULL COMMENT '通知内容',
    `school_id` varchar(32) DEFAULT NULL COMMENT '学校ID',
    `grade_id` varchar(32) DEFAULT NULL COMMENT '年级ID',
    `class_id` varchar(32) DEFAULT NULL COMMENT '班级ID',
    `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0-未发送，1-已发送',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_school_id` (`school_id`),
    KEY `idx_grade_id` (`grade_id`),
    KEY `idx_class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知表';

-- 初始化管理员账号
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `role`) 
VALUES ('1', 'admin', 'admin', '管理员', 'ADMIN');

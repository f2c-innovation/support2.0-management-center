CREATE TABLE `user` (
  `id` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户名',
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `source` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT 'local' COMMENT '用户类型',
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户密码',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态',
  `phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电话',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `last_source_id` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '上一次访问资源ID',
  `user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `dept_short_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_NAME` (`name`),
  KEY `IDX_EMAIL` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `user` (id,name,email,source,password,active,phone,create_time,last_source_id,user_name,dept_short_name) VALUES
('admin','admin','admin@fit2cloud.com','local','5b2f13d1b260f80a9dae48e94d9f6486',1,'',1537330192693,'admin','BBF-463','BBF-463')
,('xiaoqiu','xiaoqiu','xiaoqiu.cai@fit2cloud.com','local','5b2f13d1b260f80a9dae48e94d9f6486',1,'13520819652',1555671443031,'admin','Cai Xiaoqiu, BBF-463','BBF-463');

CREATE TABLE `user_key` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT 'user_key ID',
  `user_id` varchar(50) NOT NULL COMMENT '用户ID',
  `access_key` varchar(50) NOT NULL COMMENT 'access_key',
  `secret_key` varchar(50) NOT NULL COMMENT 'secret key',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `status` varchar(10) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_AK` (`access_key`),
  KEY `IDX_USER_ID` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_role` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `user_id` varchar(50) NOT NULL COMMENT '用户 id',
  `role_id` varchar(50) NOT NULL COMMENT '角色 id',
  `source_id` varchar(50) DEFAULT NULL COMMENT '资源 id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_UR` (`user_id`,`role_id`,`source_id`),
  KEY `IDX_USER_ID` (`user_id`),
  KEY `IDX_ROLE_ID` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO user_role (id,user_id,role_id,source_id) VALUES
('2a6d65db-74f4-4cbf-8ff8-8ec264b98414','admin','ADMIN',NULL)
,('6e88d03a-4234-485f-a745-de553c8bf9c1','admin','ORGADMIN','834928d5-149e-436c-907b-8fdd482347dd')
,('65fc5723-9949-49b9-ae94-fe3a841a5bff','xiaoqiu','82642ed5-e0df-4d03-8571-3c20d011b49d','1b3c7c28-2e21-4314-b0a6-07396a876db8')
,('a226776b-d6b6-468d-965f-e59053658866','xiaoqiu','ADMIN',NULL)
;


CREATE TABLE `role` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `type` varchar(20) NOT NULL COMMENT '类型 original/inherit/custom',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '父类角色 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_NAME` (`name`),
  KEY `IDX_PARENT` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `role` (id,name,description,`type`,parent_id) VALUES
('ADMIN','Sys Administrator','Sys Administrator','System','ADMIN')
,('ORGADMIN','Org Administrator','Org Administrator','System','ORGADMIN')
,('USER','Workspace User','Workspace User','System','USER')
;

CREATE TABLE `role_permission` (
  `id` varchar(50) NOT NULL,
  `role_id` varchar(50) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(128) NOT NULL COMMENT '权限ID',
  `module_id` varchar(50) NOT NULL COMMENT '模块ID',
  PRIMARY KEY (`id`),
  KEY `UNIQUE_RP` (`role_id`,`permission_id`),
  KEY `IDX_MODULE` (`module_id`),
  KEY `INX_ROLE` (`role_id`),
  KEY `IDX_PERMISSION` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `module` (
  `id` varchar(50) NOT NULL COMMENT '模块ID',
  `name` varchar(50) NOT NULL COMMENT '模块名称',
  `type` varchar(20) DEFAULT NULL COMMENT '模块类型',
  `license` varchar(50) DEFAULT NULL,
  `auth` tinyint(1) DEFAULT '1',
  `summary` varchar(128) DEFAULT NULL COMMENT '模块概要',
  `module_url` varchar(255) DEFAULT NULL COMMENT '模块跳转URL',
  `port` bigint(10) DEFAULT NULL COMMENT '模块端口',
  `status` varchar(20) DEFAULT NULL COMMENT '模块状态',
  `active` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `icon` varchar(255) DEFAULT 'link' COMMENT '模块icon',
  `sort` int(10) DEFAULT '0' COMMENT '排序',
  `open` varchar(20) DEFAULT 'current' COMMENT '模块打开方式',
  `version` varchar(100) DEFAULT NULL COMMENT '版本',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `ext1` varchar(255) DEFAULT NULL,
  `ext2` varchar(255) DEFAULT NULL COMMENT '是否授权和license ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE `system_parameter` (
  `param_key` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '参数名称',
  `param_value` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数值',
  `type` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT 'text' COMMENT '参数类型',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `workspace` (
  `id` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '工作空间ID',
  `organization_id` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '组织ID',
  `name` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '工作空间名称',
  `description` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '描述',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_WN` (`organization_id`,`name`),
  KEY `IDX_ORG` (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO workspace (id,organization_id,name,description,create_time) VALUES
('00673775-e22e-4ecb-b7ca-a17841d1b5b6','834928d5-149e-436c-907b-8fdd482347dd','Orange-工作空间','自动创建',1560026923218)
,('1b3c7c28-2e21-4314-b0a6-07396a876db8','834928d5-149e-436c-907b-8fdd482347dd','创新中心',NULL,1555905658243)
;

CREATE TABLE `organization` (
  `id` varchar(50) NOT NULL COMMENT '组织 id',
  `name` varchar(64) NOT NULL COMMENT '组织名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO organization (id,name,description,create_time) VALUES
('834928d5-149e-436c-907b-8fdd482347dd','创新开源',NULL,1555671277107)
;

CREATE TABLE `flow_task` (
  `task_id` varchar(36) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '任务ID',
  `task_step` int(10) DEFAULT NULL COMMENT '环节顺序',
  `task_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '任务名称',
  `task_status` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '任务状态',
  `task_assignee` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '任务指定执行人',
  `task_executor` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '任务实际执行人',
  `task_start_time` bigint(13) DEFAULT NULL COMMENT '任务开始时间',
  `task_end_time` bigint(13) DEFAULT NULL COMMENT '任务结束时间',
  `task_remarks` longtext CHARACTER SET utf8mb4 COMMENT '备注',
  `task_form_url` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '任务页面地址',
  `task_activity` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '环节ID',
  `process_id` varchar(36) CHARACTER SET utf8mb4 NOT NULL COMMENT '流程ID',
  `workspace_id` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工作空间ID',
  `business_type` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '关联业务类型',
  `business_key` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '关联业务ID',
  `module` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '模块',
  PRIMARY KEY (`task_id`),
  KEY `IDX_TASK_STATUS` (`task_status`) USING BTREE,
  KEY `IDX_TASK_ASSIGNEE` (`task_assignee`) USING BTREE,
  KEY `IDX_PROCESS_ID` (`process_id`) USING BTREE,
  KEY `IDX_BUSINESS_KEY` (`business_key`) USING BTREE,
  KEY `IDX_MODULE` (`module`) USING BTREE,
  KEY `IDX_WORKSPACE_ID` (`workspace_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `flow_process` (
  `process_id` varchar(36) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '流程ID',
  `process_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流程名称',
  `process_status` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '流程状态',
  `process_creator` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '发起人',
  `process_start_time` bigint(13) DEFAULT NULL COMMENT '开始时间',
  `process_end_time` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `deploy_id` varchar(36) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '部署ID',
  `business_key` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '关联业务ID',
  `business_type` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '关联业务类型',
  `workspace_id` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工作空间ID',
  `module` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '模块ID',
  PRIMARY KEY (`process_id`),
  KEY `IDX_DEPLOY_ID` (`deploy_id`) USING BTREE,
  KEY `IDX_BUSINESS_KEY` (`business_key`) USING BTREE,
  KEY `IDX_MODULE` (`module`) USING BTREE,
  KEY `IDX_WORKSPACE_ID` (`workspace_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `menu_preference` (
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `module_id` varchar(50) DEFAULT NULL COMMENT '模块ID',
  `menu_id` varchar(50) DEFAULT NULL COMMENT '菜单ID',
  `sort` int(4) DEFAULT '100' COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `IDX_USER` (`user_id`),
  KEY `IDX_MENU` (`menu_id`),
  KEY `IDX_MODULE` (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单收藏表';

CREATE TABLE `license` (
  `id` varchar(50) NOT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `license` longtext COMMENT 'license',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `notification` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(30) DEFAULT NULL COMMENT '通知类型',
  `receiver` varchar(100) DEFAULT NULL COMMENT '接收人',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `status` varchar(30) DEFAULT NULL COMMENT '状态',
  `create_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  `uuid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_RECEIVER` (`receiver`) USING BTREE,
  KEY `IDX_UUID` (`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `operation_log` (
  `id` varchar(64) NOT NULL,
  `dept_id` varchar(64) NOT NULL DEFAULT '' COMMENT '部门ID',
  `dept_name` varchar(100) NOT NULL DEFAULT '' COMMENT '部门名称',
  `resource_user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '资源拥有者ID',
  `resource_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '资源拥有者名称',
  `resource_id` varchar(64) DEFAULT NULL COMMENT '资源ID',
  `resource_name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `operation` varchar(45) NOT NULL DEFAULT '' COMMENT '操作',
  `time` bigint(13) NOT NULL COMMENT '操作时间',
  `message` mediumtext COMMENT '操作信息',
  `module` varchar(20) DEFAULT 'management-center' COMMENT '模块',
  `source_ip` varchar(15) DEFAULT NULL COMMENT '操作方IP',
  PRIMARY KEY (`id`),
  KEY `IDX_OP` (`operation`),
  KEY `IDX_RES_ID` (`resource_id`),
  KEY `IDX_RES_NAME` (`resource_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

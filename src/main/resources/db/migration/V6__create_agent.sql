DROP TABLE IF EXISTS `agent`;
CREATE TABLE `agent` (
  `id`    varchar(128) NOT NULL,
  `name`  varchar(128) NOT NULL COMMENT '代理商名称',
  `email` varchar(50) NOT NULL COMMENT '代理商邮箱',
  `phone` varchar(13) NOT NULL COMMENT '代理商电话',
  `is_delete` tinyint(1) not null default false,
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `description` text DEFAULT NULL COMMENT '代理商简介',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `agent_company`;
CREATE TABLE `agent_company` (
  `id`    varchar(128) NOT NULL,
  `company_id`  varchar(128) NOT NULL COMMENT '客户id',
  `agent_id` varchar(128) NOT NULL COMMENT '代理商id',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

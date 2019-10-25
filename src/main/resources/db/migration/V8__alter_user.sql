ALTER TABLE user ADD content varchar(255) NULL COMMENT '描述';
ALTER TABLE user ADD is_delete tinyint(1) NULL COMMENT '是否已删除';
ALTER TABLE user ADD type varchar (30) NULL COMMENT '创建类型';
ALTER TABLE user ADD uid varchar (100) NULL COMMENT 'uid为每个平台购买用户的id';


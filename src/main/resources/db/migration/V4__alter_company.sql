ALTER TABLE company ADD is_own tinyint(1) NULL COMMENT 'fitcloud区别其他公司';
ALTER TABLE company ADD email varchar(128) NULL COMMENT '公司邮箱';
ALTER TABLE company ADD address varchar(128) NULL COMMENT '公司地址';
ALTER TABLE company ADD phone varchar(50) NULL COMMENT '公司电话';
ALTER TABLE company ADD is_delete tinyint(1) NULL COMMENT '是否删除';
ALTER TABLE company ADD type varchar(30) NULL COMMENT '类型';
ALTER TABLE company ADD instance_id varchar(128) NULL COMMENT '阿里云市场标识id';
ALTER TABLE company CHANGE description content varchar(128) NULL COMMENT '描述';

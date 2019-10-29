SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` varchar(128) COLLATE utf8_bin NOT NULL,
  `category_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `dictionary_key` varchar(255) COLLATE utf8_bin NOT NULL,
  `dictionary_value` varchar(255) COLLATE utf8_bin NOT NULL,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
BEGIN;
INSERT INTO `dictionary` VALUES ('01c2b0f50e0240998a0894fa2c34c126', 'product_classification', 'openshift', '红帽云开发平台', 0);
INSERT INTO `dictionary` VALUES ('043629ba59ab4fd7824599a8b8e84508', 'apply_subscription_notice', '张博翰', 'bohan@fit2cloud.com1', 0);
INSERT INTO `dictionary` VALUES ('0ecbc585ea5a46e8a6e9a0ace03ea5f6', 'ticket_status', 'pending_support', '等待技术回复', 1);
INSERT INTO `dictionary` VALUES ('13026a2e3e9a46418971429aba232017', 'extensions_tags', '安全合规', '安全合规', 0);
INSERT INTO `dictionary` VALUES ('2b78a0f1d1474d2dac616d479f887bc5', 'info_category', 'info_package', '安装包', 0);
INSERT INTO `dictionary` VALUES ('36061828beab4760812f8089703482fe', 'apply_subscription_notice', '吕小虎', 'xiaohu.lv@fit2cloud.com1', 0);
INSERT INTO `dictionary` VALUES ('4bf690f75d124cfb9bdcb2c7b22cb55c', 'info_category', 'info_file', '文档', 0);
INSERT INTO `dictionary` VALUES ('54f108cf02ac4176b89f3f1cf056a080', 'product_classification', 'jumpServer', '开源的堡垒机', 0);
INSERT INTO `dictionary` VALUES ('5b599715a0bd462aba42c0a5f8a44960', 'ticket_type', 'T3', '远程协助', 0);
INSERT INTO `dictionary` VALUES ('5bb978fcb5cd4b5cbbca00ba51de19a6', 'product_classification', 'DBaaS', 'DBaaS', 0);
INSERT INTO `dictionary` VALUES ('5cfc873e949942568551a2060588bbb4', 'ticket_status', 'closed', '关闭', 1);
INSERT INTO `dictionary` VALUES ('5d7dcae9850f4126b9337ce9b906ea39', 'ossMapping', 'jumpServer', 'jumpserver/', 0);
INSERT INTO `dictionary` VALUES ('5dc3cb0a53ed4cb1bcf9b71e5202cf12', 'product_edition', 'StandardEdition', '标准版', 0);
INSERT INTO `dictionary` VALUES ('5ea646915aa148f1a89b3810a203f4a3', 'ossMapping', 'extensions', 'extensions/', 0);
INSERT INTO `dictionary` VALUES ('5ee672e090ee48b7972df03c5f80287d', 'apply_subscription_notice', '蔡小秋', 'xiaoqiu.cai@fit2cloud.com', 0);
INSERT INTO `dictionary` VALUES ('6e872ab1c7334fe2b5ff686e05b3b36d', 'ossMapping', 'openshift', 'openshift/', 0);
INSERT INTO `dictionary` VALUES ('7318ca8f5cf64b4d9c2f01c1744d12df', 'ossMapping', 'CMP', 'cmp/', 0);
INSERT INTO `dictionary` VALUES ('8453bab851ba47718553648b881486ce', 'extensions_tags', '备份', '备份', 0);
INSERT INTO `dictionary` VALUES ('8dff84dd285448f98e6552d1d9e438c4', 'ticket_priority', '2', '紧急', 1);
INSERT INTO `dictionary` VALUES ('92f10e99a46547b9982afa88a78d97a4', 'product_classification', 'CMP', '云管平台', 0);
INSERT INTO `dictionary` VALUES ('949d32bad18143aca0049a002db56a1e', 'extensions_tags', '其他', '其他', 0);
INSERT INTO `dictionary` VALUES ('a43fae1b5bb54dc0bbe9241421cee7a3', 'extensions_tags', '移动端', '移动端', 0);
INSERT INTO `dictionary` VALUES ('a6aaf3e3597c4315b2175d02ca8fe625', 'ossMapping', 'referenceDoc', 'docs/', 0);
INSERT INTO `dictionary` VALUES ('aad811a62f2d41bb93443fed521f2486', 'incoice_type', 'VAT_invoice', '增值税专用发票', 1);
INSERT INTO `dictionary` VALUES ('abef7b18e5fc4339b2abbae1d8396a32', 'ticket_priority', '3', '非常紧急', 1);
INSERT INTO `dictionary` VALUES ('b07bb4855da44a1692c3ffc64ab09dae', 'ticket_priority', '1', '一般', 1);
INSERT INTO `dictionary` VALUES ('b15bce8ce13b4c27a2969e3156346316', 'product_classification', 'KubeOperator', '开源的 kubernetes 集群部署和管理平台', 0);
INSERT INTO `dictionary` VALUES ('c0f0884a6ed647b5bff8e42b846860d2', 'ticket_status', 'live', '等待处理', 0);
INSERT INTO `dictionary` VALUES ('c41ce0480bba40c8a9bada70141cb9af', 'ticket_type', 'T2', '产品续约', 1);
INSERT INTO `dictionary` VALUES ('cb569a142e0b44d6adc9363ad27c8466', 'ticket_type', 'T1', '产品问题', 1);
INSERT INTO `dictionary` VALUES ('cbc71d59ef794497b703a08734cbc163', 'product_edition', 'EnterpriseEdition', '企业版', 0);
INSERT INTO `dictionary` VALUES ('cf815c1eb1614301af958c43039182f7', 'extensions_tags', '存储', '存储', 0);
INSERT INTO `dictionary` VALUES ('d58df4c3f1e448d7b04cdaecf2b0942c', 'ossMapping', 'ticketAttachment', 'ticket-attachment/', 0);
INSERT INTO `dictionary` VALUES ('dd7e08e806b640db9744f584380b1958', 'ticket_status', 'pending_user', '等待用户回复', 1);
INSERT INTO `dictionary` VALUES ('ee0f310b0760437bb9657029cadce34c', 'info_category', 'info_middleware', '中间件', 0);
INSERT INTO `dictionary` VALUES ('f8dd8c0e69414d3691eecd4716c5de7e', 'info_category', 'info_system', '操作系统', 0);
INSERT INTO `dictionary` VALUES ('fe31dd2a0d62407b88ae2a8425718634', 'apply_subscription_notice', '马国昊', 'guohao.ma@fit2cloud.com1', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

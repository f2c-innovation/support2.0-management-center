SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` VALUES ('apply_subscription_notice', '申请订阅邮件通知', 1);
INSERT INTO `category` VALUES ('extensions_tags', '扩展模块标签', 1);
INSERT INTO `category` VALUES ('incoice_type', '发票类型', 1);
INSERT INTO `category` VALUES ('info_category', '资料类别', 1);
INSERT INTO `category` VALUES ('ossMapping', 'OSS文件夹映射', 1);
INSERT INTO `category` VALUES ('product_classification', '产品分类', 1);
INSERT INTO `category` VALUES ('product_edition', '产品版本', 1);
INSERT INTO `category` VALUES ('ticket_priority', '优先级', 1);
INSERT INTO `category` VALUES ('ticket_status', '工单状态', 1);
INSERT INTO `category` VALUES ('ticket_type', '工单类型', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

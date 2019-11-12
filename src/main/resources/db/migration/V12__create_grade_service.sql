/*
 Date: 12/11/2019 16:18:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for grade_service
-- ----------------------------
DROP TABLE IF EXISTS `grade_service`;
CREATE TABLE `grade_service` (
  `id` varchar(128) COLLATE utf8_bin NOT NULL,
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '服务键值',
  `description` text COLLATE utf8_bin COMMENT '服务描述',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of grade_service
-- ----------------------------
BEGIN;
INSERT INTO `grade_service` VALUES ('af90940ee9564dd6b7c1f69495b08e80', '增强级', '<p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #009051}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>支持服务</b></p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #ffffff}\nspan.s1 {font: 40.0px Helvetica; font-kerning: none; color: #0076ba; -webkit-text-stroke: 0px #0076ba}\nspan.s2 {font-kerning: none}\n</style>\n\n\n</p><p><b>7×24 </b>工单及电话支持服务，<b>1</b>个小时内响应客户工单；接到故障申报后，工程师通过电话支持、远程接入等方式协助客户及时排除软件故障。</p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #009051}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>原厂现场服务</b></p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #5e5e5e}\nspan.s1 {font-kerning: none}\nspan.s2 {font: 28.0px \'Helvetica Neue\'; font-kerning: none}\n</style>\n\n\n</p><p>合计 5 人天的原厂专业服务，可提供现场安装、现场培训、现场紧急救助、软件故障现场排查等专业支持服务；并且可以根据企业 IT 规划提供云管平台建设及容量规划等专家顾问咨询服务。</p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #009051}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>软件升级服务</b></p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #ffffff}\nspan.s1 {font-kerning: none}\n</style>\n\n\n</p><p>提供软件补丁、增强功能包等软件升级服务，无缝升级软件版本。</p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #ffffff}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>在线自助服务</b></p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #ffffff}\nspan.s1 {font-kerning: none}\n</style>\n\n\n</p><p>提供客户支持门户，支持客户在线访问网站并下载相关资料，及时掌握最新的软件特性、维护经验、使用技巧等相关知识。</p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #ffffff}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #009051}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>', 1541498252152, 1541498252152);
INSERT INTO `grade_service` VALUES ('cdc05e0332d14105b7fa171087cc01c4', '基础级', '<p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #ffffff}\nspan.s1 {font: 40.0px Helvetica; font-kerning: none; color: #0076ba; -webkit-text-stroke: 0px #0076ba}\nspan.s2 {font: 28.0px Helvetica; font-kerning: none; color: #0076ba; -webkit-text-stroke: 0px #0076ba}\nspan.s3 {font-kerning: none}\n</style>\n\n\n</p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #009051}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>支持服务：</b></p><p><b>5×8</b> 工单及电话支持服务，<b>4</b>个小时内响应客户工单；接到故障申报后，工程师通过电话支持、远程接入等方式协助客户及时排除软件故障。</p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #009051}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>软件升级服务</b></p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #ffffff}\nspan.s1 {font-kerning: none}\n</style>\n\n\n</p><p>提供软件补丁、增强功能包等软件升级服务，无缝升级软件版本。</p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; text-align: center; font: 28.0px \'PingFang SC Semibold\'; color: #ffffff; -webkit-text-stroke: #ffffff}\nspan.s1 {font: 28.0px \'PingFang SC\'; font-kerning: none}\n</style>\n\n\n</p><p><b>在线自助服务</b></p><p>\n\n\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; font: 28.0px \'PingFang SC\'; color: #5e5e5e; -webkit-text-stroke: #ffffff}\nspan.s1 {font-kerning: none}\n</style>\n\n\n</p><p>提供客户支持门户，支持客户在线访问网站并下载相关资料，及时掌握最新的软件特性、维护经验、使用技巧等相关知识。</p>', 1541498187005, 1541498187005);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_5.7
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : bricks_framework_db

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-10-26 20:38:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_task
-- ----------------------------
DROP TABLE IF EXISTS `schedule_task`;
CREATE TABLE `schedule_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(100) DEFAULT NULL COMMENT '定时器分组名称',
  `job_status` varchar(10) DEFAULT NULL COMMENT '任务状态（1启用，0未启用）',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `description` varchar(1000) DEFAULT NULL COMMENT '任务描述',
  `bean_class` varchar(200) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法',
  `is_concurrent` varchar(10) DEFAULT NULL COMMENT '是否可以并发运行（1允许，0不允许）',
  `spring_id` varchar(100) DEFAULT NULL COMMENT '任务处理类的在spring中的ID',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of schedule_task
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（0未知，1女，2男）',
  `age` tinyint(3) DEFAULT NULL COMMENT '年龄',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------

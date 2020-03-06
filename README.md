# 老兵之家

## 数据库脚本
```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cm_Question
-- ----------------------------
DROP TABLE IF EXISTS `cm_Question`;
CREATE TABLE `cm_Question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `decription` text,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cm_User
-- ----------------------------
DROP TABLE IF EXISTS `cm_User`;
CREATE TABLE `cm_User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cm_article
-- ----------------------------
DROP TABLE IF EXISTS `cm_article`;
CREATE TABLE `cm_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gtm_modifiled` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `decription` varchar(255) DEFAULT NULL,
  `content` text,
  `view_count` int(11) DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cm_comment
-- ----------------------------
DROP TABLE IF EXISTS `cm_comment`;
CREATE TABLE `cm_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `from_uid` varchar(100) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `comment_like` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1053 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cm_notification
-- ----------------------------
DROP TABLE IF EXISTS `cm_notification`;
CREATE TABLE `cm_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifier` int(11) DEFAULT NULL,
  `reciver` int(11) DEFAULT NULL,
  `outerId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `notifier_name` varchar(255) DEFAULT NULL,
  `outer_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cm_reply
-- ----------------------------
DROP TABLE IF EXISTS `cm_reply`;
CREATE TABLE `cm_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_id` bigint(20) DEFAULT NULL,
  `form_uid` varchar(100) DEFAULT NULL,
  `to_uid` varchar(100) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `reply_like` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
```
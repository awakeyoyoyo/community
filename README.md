# 老兵之家

## 数据库脚本
```sql
#user表
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
#问题表
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
```
# 老兵之家

## 数据库脚本
```sql
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
```
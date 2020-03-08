# 与我常在's Blog
## 技术栈
- 项目构建:Maven
- web框架:Springboot
- 数据库ORM:Mybatis
- 数据库:MySql
- 缓存:Redis
- 前端模版:Thymeleaf
## 主要功能：
- 文章管理<br>
1.分页展示文章信息，对于分页进行异步请求，减少页面刷新次数
- 发布文章<br>
1.使用markdown编辑器，支持插入代码，插入图片等功能<br>
2.文章可选择分类和标签<br>
3.需要处于登陆状态、并且是管理员身份<br>
- 发布问题<br>
1.使用markdown编辑器，支持插入代码，插入图片等功能<br>
2.问题可选择分类和标签<br>
3.需要处于登陆状态<br>
- 分类管理
1.根据分类显示出相应的博客<br>
2.分类标签存于redis中，提高访问速度<br>
- 留言功能
- 登陆注册<br>
1.采用手机验证码方式进行注册<br>
2.支持github登陆(目前由于服务器无法访问github授权api，此功能暂且用不成)

##如何部署运行
- 运行数据库脚本
- 克隆本项目后新建并且修改项目的application.properties(位于下方有模版)
- maven打包成jar包运行即可

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
## 由于配置文件有大量隐私，所以仓库中并没有上传。这里给出模版
### application.properties
```properties
#github授权部分
github.client.id=******
github.client.secret=*****
github.redirect.uri=http://localhost:8080/callback
#数据库部分
spring.datasource.username=******
spring.datasource.password=******
spring.datasource.url=jdbc:mysql://localhost:3306/community?useUnicode=true&characterEncoding=utf8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
mybatis.config-location=classpath:mybatis-config/mybatis-config.xml
#devtools实现页面热部署
#spring.thymeleaf.cache=false

spring.mvc.favicon.enabled=false
#阿里巴巴oss
alibabaoss.accessKeyId=**********
alibabaoss.accessKeySecret=**********
alibabaoss.bucketName=**********
alibabaoss.endpoint=oss-cn-shenzhen.aliyuncs.com
alibabaoss.communityfolder=**********
alibabaoss.url=******
#logging.file=logs/community.log
logging.config=classpath:lockback/logback-spring.xml
logging.level.root=info
logging.level.com.awakeyo.community.mapper=debug
logging.file.max-size=15MB
logging.file.max-history=10
#redis
# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=localhost
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
#管理员账号
neverDie.user=**********
```

## 更新内容：
- .....
- .....
- .....
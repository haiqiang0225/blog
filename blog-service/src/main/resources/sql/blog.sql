SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `article_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '发表文章的用户id',
  `title` text NOT NULL COMMENT '文章标题',
  `abstract` varchar(255) NOT NULL COMMENT '文章摘要',
  `view_count` bigint(20) NOT NULL COMMENT '浏览量',
  `comment_count` bigint(20) NOT NULL COMMENT '评论总数',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发表时间',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `like_count` bigint(20) NOT NULL COMMENT '点赞数',
  `top_flag` varchar(1) DEFAULT NULL COMMENT '置顶标志，非0即真',
  `article_detail_id` bigint(20) NOT NULL COMMENT '文章详情的id',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章信息表';

-- ----------------------------
-- Table structure for tb_article_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_category`;
CREATE TABLE `tb_article_category` (
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`article_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_article_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_detail`;
CREATE TABLE `tb_article_detail` (
  `article_deatil_id` bigint(20) NOT NULL COMMENT '文章详情id',
  `content_md` longtext COMMENT 'markdown内容',
  `content_html` longtext COMMENT 'html内容',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  PRIMARY KEY (`article_deatil_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_article_label
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_label`;
CREATE TABLE `tb_article_label` (
  `arcicle_id` bigint(20) NOT NULL COMMENT '文章id',
  `label_id` bigint(20) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`arcicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类的名称',
  `alias` varchar(15) DEFAULT NULL COMMENT '别名',
  `description` text COMMENT '描述',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类别的ID',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `comment_id` bigint(20) NOT NULL COMMENT '评论id',
  `user_id` bigint(20) NOT NULL COMMENT '发表评论的用户id',
  `article_id` bigint(20) NOT NULL COMMENT '评论的文章id',
  `like_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发表日期',
  `content` text NOT NULL COMMENT '评论内容',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父评论的id',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_tags`;
CREATE TABLE `tb_tags` (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(20) NOT NULL COMMENT '标签名称',
  `alias` varchar(15) DEFAULT NULL COMMENT '标签别名',
  `description` text COMMENT '标签描述',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_ip` varchar(32) NOT NULL COMMENT '用户ip',
  `user_ipv6` varchar(128) DEFAULT NULL COMMENT 'ipv6地址',
  `user_password` varchar(255) NOT NULL COMMENT '用户密码（加密后）',
  `user_email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `user_profile_photo_url` varchar(255) DEFAULT NULL COMMENT '用户头像的地址',
  `user_register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `user_phone_number` int(11) NOT NULL COMMENT '手机号',
  `user_nickname` varchar(20) NOT NULL COMMENT '用户昵称',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

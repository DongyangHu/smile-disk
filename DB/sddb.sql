/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : sddb_test

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-06-09 13:38:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sd_friend_apply`
-- ----------------------------
DROP TABLE IF EXISTS `sd_friend_apply`;
CREATE TABLE `sd_friend_apply` (
`apply_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '申请id' ,
`send_user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '发出好友申请的人' ,
`receive_user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '接受申请的人' ,
`crtime`  datetime NOT NULL COMMENT '申请时间' ,
`handle_time`  datetime NULL DEFAULT NULL COMMENT '处理时间' ,
`result`  varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '0' COMMENT '0未处理，1同意，2拒绝' ,
PRIMARY KEY (`apply_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_friend_apply
-- ----------------------------
BEGIN;
INSERT INTO `sd_friend_apply` VALUES ('1', '2', '3', '2018-04-11 10:27:51', null, '1'), ('2', '2', '3', '2018-04-11 10:28:17', '2018-04-12 17:17:33', '1'), ('3', '2', '3', '2018-04-11 10:28:19', '2018-04-12 17:19:12', '1'), ('4', '2', '3', '2018-04-11 10:28:21', null, '2'), ('5', '2', '3', '2018-04-11 10:28:23', null, '0'), ('6', '2', '3', '2018-04-11 10:28:24', '2018-04-12 17:19:17', '2'), ('7', '7', '7', '2018-05-18 18:45:32', '2018-05-18 18:45:46', '2'), ('8', '7', '6', '2018-05-18 18:45:52', '2018-05-18 18:46:06', '1');
COMMIT;

-- ----------------------------
-- Table structure for `sd_friend_relationship`
-- ----------------------------
DROP TABLE IF EXISTS `sd_friend_relationship`;
CREATE TABLE `sd_friend_relationship` (
`user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户id' ,
`friend_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '好友id' ,
`crtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`user_id`, `friend_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
COMMENT='好友关系表'

;

-- ----------------------------
-- Records of sd_friend_relationship
-- ----------------------------
BEGIN;
INSERT INTO `sd_friend_relationship` VALUES ('2', '1', '2018-04-10 16:23:54'), ('6', '7', '2018-05-18 18:46:06'), ('7', '6', '2018-05-18 18:46:06');
COMMIT;

-- ----------------------------
-- Table structure for `sd_notice_info`
-- ----------------------------
DROP TABLE IF EXISTS `sd_notice_info`;
CREATE TABLE `sd_notice_info` (
`notice_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息id' ,
`notice_title`  varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息标题' ,
`notice_content`  text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息内容' ,
`cruser_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '创建者用户id' ,
`crtime`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`notice_id`),
UNIQUE INDEX `notice_id` (`notice_id`) USING BTREE ,
INDEX `notice_title` (`notice_title`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
COMMENT='站内消息'

;

-- ----------------------------
-- Records of sd_notice_info
-- ----------------------------
BEGIN;
INSERT INTO `sd_notice_info` VALUES ('1', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('10', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('11', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('12', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('13', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('14', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('15', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('16', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('17', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('18', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('19', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('2', '测试站内信标题12', '测试站内信标题1测试站内信标题1', '1', '2018-03-27 15:15:56'), ('20', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('21', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('22', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('23', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('24', '20180506测试消息', '20180506测试消息', '1', '2018-05-06 22:41:57'), ('26', '今天在下雨', '今天在下雨', '1', '2018-05-06 22:47:03'), ('27', '今天是下雨天', '今天是下雨天', '1', '2018-05-06 22:48:11'), ('28', '这是一个测试的消息', '这是一个测试的消息', '1', '2018-05-18 18:47:40'), ('29', '这仅仅是一个测试的消息', '这仅仅是一个测试的消息这仅仅是一个测试的消息这仅仅是一个测试的消息这仅仅是一个测试的消息这仅仅是一个测试的消息这仅仅是一个测试的消息这仅仅是一个测试的消息', '1', '2018-05-18 18:54:06'), ('3', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('4', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('5', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('6', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('7', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('8', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43'), ('9', '测试站内信标题1', '测试站内信标题1', '1', '2018-03-27 15:15:43');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_file`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_file`;
CREATE TABLE `sd_sys_file` (
`file_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件id' ,
`file_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户上传的文件名' ,
`file_path`  varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件存储的路径' ,
`file_dir_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件对应文件夹的id' ,
`status`  int(5) NOT NULL DEFAULT 1 COMMENT '文件状态0：失效 1：正常' ,
`cruser_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '创建者用户id' ,
`crtime`  datetime NOT NULL COMMENT '创建时间' ,
`file_type`  int(5) NOT NULL DEFAULT 0 COMMENT '1：视频，2：音乐，3：文档，4：图片，0：其他' ,
`file_lable`  varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '原始文件名称' ,
`file_size`  varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件大小' ,
PRIMARY KEY (`file_id`),
UNIQUE INDEX `file_id` (`file_id`) USING BTREE ,
INDEX `file_dir_id` (`file_dir_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_file
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_file` VALUES ('10', 'a593e6e6046e415d9a958f897a970b6e.txt', 'hudy/2018/2/8', '2', '1', '2', '2018-02-08 15:34:34', '3', '北京移动.txt', ''), ('11', '9d5030c04f5d42f59b6e9b4aa26de762.doc', 'hudy/2018/2/8', '2', '1', '2', '2018-02-08 15:36:22', '3', '胡东阳-Java开发.doc', ''), ('12', '3e6722b2d9fc4160ac64955622557d7e.jpg', 'hudy/2018/2/24', '2', '1', '2', '2018-02-24 19:59:41', '4', 'IMG_20170708_121818.jpg', ''), ('13', 'a4d3464069714f3189efcc6d50c7fb2d.rar', 'demo123/2018/2/24', '3', '1', '3', '2018-02-24 20:01:41', '0', 'Win10_activate.rar', ''), ('14', 'dfgdfgdgdgdfg', 'dfg', '2', '0', '2', '2018-03-10 13:59:14', '0', 'serwer', ''), ('15', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '1', '2', '2018-03-10 14:03:01', '0', 'petshow.css', ''), ('16', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:16', '0', 'petshow.css', ''), ('17', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:16', '0', 'petshow.css', ''), ('18', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '1', '2', '2018-03-10 14:07:16', '0', 'petshow.css', ''), ('19', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '1', '2', '2018-03-10 14:07:16', '0', 'petshow.css', ''), ('20', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '1', '2', '2018-03-10 14:07:16', '0', 'petshow.css', ''), ('21', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '1', '2', '2018-03-10 14:07:16', '0', 'petshow.css', ''), ('22', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', 'petshow.css', ''), ('23', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', 'petshow.css', ''), ('24', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', 'petshow.css', ''), ('25', '123', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', 'petshow.css', ''), ('26', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', '123a4.css', ''), ('27', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', 'petshow.css', ''), ('28', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '2', '0', '2', '2018-03-10 14:07:17', '0', 'petshow.css', ''), ('29', 'adf5c2899374430e80f85d9091064cf1.zip', 'hudy/2018/3/12', '', '0', '2', '2018-03-12 16:04:12', '0', '投票.zip', '1.62Mb'), ('3', 'aedc930899494e128e3ca2dcbe9245df.zip', 'hudy/2018/2/6', '2', '1', '2', '2018-02-06 10:27:16', '0', '01-视频类.zip', ''), ('30', '1e13335c288445168463292c806cc14e.zip', 'hudy/2018/3/12', '', '0', '2', '2018-03-12 19:33:05', '0', '投票.zip', '1.62Mb'), ('31', '807bfd1ae16249cf850deedf99cf881b.zip', 'hudy/2018/3/12', '14', '1', '2', '2018-03-12 19:50:09', '0', '投票.zip', '1.62Mb'), ('33', 'd62825d3e5f6436a91e224219c38916c.zip', 'hudy/2018/3/14', '14', '1', '2', '2018-03-14 00:03:01', '0', 'jQueryVideo.zip', '0.14Mb'), ('34', 'feadcb0f1e1e4744a5c42e0a9d76b313.zip', 'hudy/2018/3/14', '2', '1', '2', '2018-03-14 00:07:07', '0', 'sd_web_view.zip', '1.96Mb'), ('35', 'cbf758d7299e4ef7876bdcaee946073f.docx', 'hudy/2018/3/14', '2', '1', '2', '2018-03-14 00:08:43', '3', 'solr学习总结.docx', '0.06Mb'), ('36', 'f40a3f78bba74a8ebb97d1e8367787d0.zip', 'hudy/2018/3/14', '', '1', '2', '2018-03-14 15:49:39', '0', 'sd_web_view.zip', '1.96Mb'), ('37', '9d5525f5066f43b592fe3d8f34f3e0d5.zip', 'hudy/2018/3/14', '', '1', '2', '2018-03-14 15:59:48', '0', 'adt-bundle-windows-x86_64-20140702.zip', '353.58Mb'), ('38', 'b33ae4467ab847eab17c5e5f891d2fc6.doc', 'shimengxuan/2018/5/18', '19', '1', '5', '2018-05-18 11:09:18', '3', '中国移动通信集团北京有限公司考试系统维护方案_20171102.doc', '0.14Mb'), ('39', 'e306b74a8967493ba31005fcda6518f0.jpg', 'shimengxuan/2018/5/18', '19', '1', '5', '2018-05-18 11:10:13', '4', '罗聪.jpg', '0.05Mb'), ('4', '153f1945857b44ad89a206de3ebabd78.jpg', 'hudy/2018/2/8', '2', '1', '2', '2018-02-08 15:27:39', '4', 'IMG_20170708_121818.jpg', ''), ('40', '51439af69bc44989867fd561a247a7ed.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:41:51', '4', 'index.jpg', '0.00Mb'), ('41', '4caa94af38e84015b1f619c88ae5798e.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:41:56', '4', 'index1.jpg', '0.02Mb'), ('42', '80bc4130c8fc436494b11549794fba0f.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:01', '4', 'u=52012746,2788855913&fm=27&gp=0.jpg', '0.01Mb'), ('43', '5a31f2a523f74c4b90998fcc6304da3e.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:06', '4', 'u=584818429,315483516&fm=27&gp=0.jpg', '0.01Mb'), ('44', '087e1846078942d28fdf92971945c73b.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:11', '4', 'u=613337516,2383193070&fm=27&gp=0.jpg', '0.04Mb'), ('45', 'f671ab62db2944719f25aa8f52a3846b.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:17', '4', 'u=626765215,747034866&fm=27&gp=0.jpg', '0.04Mb'), ('46', '5cdfce6a45154fe39ac4c933dd226c0c.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:23', '4', 'u=1620464803,2737906254&fm=27&gp=0.jpg', '0.02Mb'), ('47', '2c166839d8e7457fb8af5a22ee45a346.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:30', '4', 'u=3875968917,2352913688&fm=200&gp=0.jpg', '0.02Mb'), ('48', '9af7614c20024bdc9f555803f0466799.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:35', '4', 'u=1620464803,2737906254&fm=27&gp=0.jpg', '0.02Mb'), ('49', '63e01c75ea2a46a59dd0d61404cc7a03.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:39', '4', 'index1.jpg', '0.02Mb'), ('5', 'f212eea3ef7447a48c0d5e61ffb0a711.jpg', 'hudy/2018/2/8', '3', '1', '2', '2018-02-08 15:27:50', '4', 'IMG_20170708_121818.jpg', ''), ('50', '9be33941025c453faaa26156d7fb566e.jpg', 'test1/2018/5/18', '21', '1', '6', '2018-05-18 17:42:44', '4', 'u=584818429,315483516&fm=27&gp=0.jpg', '0.01Mb'), ('51', 'bc08704b76bc48e6bd630dca9c5acb00.mp3', 'test1/2018/5/18', '', '1', '6', '2018-05-18 17:43:48', '2', 'Alan Walker - Fade.mp3', '10.08Mb'), ('52', 'aacd277eb3cd4802b21086dba033453a.mp3', 'test1/2018/5/18', '', '1', '6', '2018-05-18 17:43:58', '2', 'IF YOU.mp3', '10.18Mb'), ('53', '702476ac5f574391bdb116e5c49502f2.mp3', 'test1/2018/5/18', '', '1', '6', '2018-05-18 17:44:07', '2', '阿杜 - 他一定很爱你.mp3', '8.23Mb'), ('54', '6f916a937e2b4baa8251e5c1885ed9b9.mp3', 'test1/2018/5/18', '', '1', '6', '2018-05-18 17:44:15', '2', '单车.mp3', '7.95Mb'), ('55', '5dc5a0ae2991431e8962527fe1dbe22e.mp4', 'test1/2018/5/18', '', '1', '6', '2018-05-18 17:44:50', '1', '纠正哥.mp4', '5.86Mb'), ('56', '5d2321d61e4742058e991cfb527306a5.doc', 'test1/2018/5/18', '', '1', '6', '2018-05-18 17:46:43', '3', '第二次测试.doc', '0.04Mb'), ('57', '5de87057faef4f19a41870feb12e712c.png', 'test1/2018/5/18', '20', '1', '6', '2018-05-18 17:47:23', '4', '无标题.png', '0.16Mb'), ('58', 'a2cadd95d28946e2b1e189ec199fd2fa.avi', 'test1/2018/5/18', '20', '1', '6', '2018-05-18 17:50:48', '1', '13、聚合函数.avi', '27.09Mb'), ('59', 'ec3762b2c97c4414bde7d2048a814e59.jpg', 'test1/2018/5/18', '23', '1', '6', '2018-05-18 18:00:46', '4', 'u=626765215,747034866&fm=27&gp=0.jpg', '0.04Mb'), ('6', '9d780e52f9a24a2c9b0c649eb555e55b.jpg', 'hudy/2018/2/8', '5', '1', '2', '2018-02-08 15:27:59', '4', 'IMG_20170708_121818.jpg', ''), ('60', '98830b50a3ca4a5c8889eb134d7d2f9f.zip', 'test1/2018/5/18', '', '1', '6', '2018-05-18 18:37:42', '0', '山寨版qq源码.素材.zip', '0.07Mb'), ('61', 'd7f8720e74ea49fcb81c64afbf122d91.zip', 'test1/2018/5/18', '', '1', '6', '2018-05-18 18:37:51', '0', '坦克大战游戏源码.素材.文档.zip', '0.56Mb'), ('62', '46a708c9db71458cbd488b305c8fcd62.avi', 'test1/2018/5/18', '', '1', '6', '2018-05-18 18:38:08', '1', '韩顺平.循序渐进学.java.从入门到精通.第4讲-.流程控制.avi', '47.26Mb'), ('63', 'd4a1303edb2642fd97abbd2074934d83.avi', 'test1/2018/5/18', '', '1', '6', '2018-05-18 18:40:07', '1', '韩顺平.循序渐进学.java.从入门到精通.第94讲-山寨qq项目8.好友在线提示.avi', '19.97Mb'), ('64', '164f02178bb74b15a326d9b491dc82fc.pdf', 'test1/2018/5/18', '25', '1', '6', '2018-05-18 18:40:58', '3', '【Java】技术指南.pdf', '0.06Mb'), ('65', 'bd600a705da246f1926c937effa415e1.pdf', 'test1/2018/5/18', '25', '1', '6', '2018-05-18 18:41:03', '3', 'springboot.pdf', '0.15Mb'), ('66', '46f9a8f56230481c8a9199eb42e8fbb8.pdf', 'test1/2018/5/18', '25', '1', '6', '2018-05-18 18:41:08', '3', '《阿里巴巴Java开发手册》(纪念版).pdf', '1.07Mb'), ('67', '3a54b555b7e34251b86381e9bde4accb.pdf', 'test1/2018/5/19', '25', '1', '6', '2018-05-19 13:20:32', '3', 'springboot.pdf', '0.15Mb'), ('68', '272b9fc187394b52833bb21ee392f20b.pdf', 'test1/2018/5/19', '34', '0', '6', '2018-05-19 13:34:45', '3', '《阿里巴巴Java开发手册》(纪念版).pdf', '1.07Mb'), ('69', '136e906e47ce4c319f60660b29a11217.jpg', 'test1/2018/5/19', '34', '1', '6', '2018-05-19 13:34:54', '4', '图片1.jpg', '0.44Mb'), ('7', 'a96135bef73945849249a78a3c4a20f7.jpg', 'hudy/2018/2/8', '7', '1', '2', '2018-02-08 15:28:48', '4', 'IMG_20170708_121818.jpg', ''), ('70', 'f860639bfe1747ae9830c6bcf3f9cae8.gif', 'test1/2018/5/19', '34', '1', '6', '2018-05-19 13:35:10', '4', '13525712.gif', '0.00Mb'), ('71', '24efe355b8e54860a0e2be3eac5853aa.pptx', 'test1/2018/5/19', '34', '1', '6', '2018-05-19 13:35:28', '3', '扁平化动态视觉感转场工作总结汇报ppt模板.pptx', '9.94Mb'), ('72', 'db7e96897fc94529af11c6dfd4369193.jpg', 'test1/2018/5/19', '', '1', '6', '2018-05-19 14:49:27', '4', '背景.jpg', '0.03Mb'), ('8', '7798d60ce7f24cdda9f38d58c8dd7a0e.txt', 'hudy/2018/2/8', '3', '1', '2', '2018-02-08 15:31:31', '3', 'JavaScript学习（一）.txt', ''), ('9', '3c03970d4257484ba1d3f0af3f1dfc17.css', 'hudy/2018/2/8', '3', '1', '2', '2018-02-08 15:32:59', '0', 'petshow.css', '');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_file_dir`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_file_dir`;
CREATE TABLE `sd_sys_file_dir` (
`dir_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件夹id' ,
`dir_name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件夹名称' ,
`parent_dir_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件夹父节点id' ,
`dir_path_id`  varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件夹路径id' ,
`dir_path_name`  varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文件夹路径名' ,
`status`  int(5) NOT NULL DEFAULT 1 COMMENT '文件状态0：失效 1：正常' ,
`cruser_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '创建者用户id' ,
`crtime`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`dir_id`),
UNIQUE INDEX `dir_id` (`dir_id`) USING BTREE ,
INDEX `parent_dir_id` (`parent_dir_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_file_dir
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_file_dir` VALUES ('10', '哈哈', '3', '2,3,', '根目录1,测试文件夹1,', '1', '2', '2018-03-09 16:31:27'), ('11', '测试文件夹', '9', '9,', '根目录4,', '1', '2', '2018-03-11 17:49:11'), ('13', '撒大声地所', '9', '9,', '根目录4,', '1', '2', '2018-03-11 17:53:50'), ('14', '测试', '9', '9,', '根目录4,', '1', '2', '2018-03-11 17:58:11'), ('15', '测试123', '9', '9,', '根目录4,', '0', '2', '2018-03-11 17:58:31'), ('16', 'ceshi', '9', '9,', '根目录4,', '0', '2', '2018-03-11 19:48:53'), ('17', 'cdcd', '9', '9,', '根目录4,', '0', '2', '2018-03-11 19:49:06'), ('18', '文档', '', '', '', '1', '5', '2018-05-18 11:08:23'), ('19', '20180518', '18', '18,', '文档,', '1', '5', '2018-05-18 11:08:40'), ('2', '根目录1', '', '', '', '1', '2', '2018-02-06 17:31:38'), ('20', '测试文件夹', '', '', '', '1', '6', '2018-05-18 17:40:57'), ('21', '20180518', '20', '20,', '测试文件夹,', '1', '6', '2018-05-18 17:41:08'), ('22', '文档', '21', '20,21,', '测试文件夹,20180518,', '1', '6', '2018-05-18 18:00:13'), ('23', '图片', '21', '20,21,', '测试文件夹,20180518,', '1', '6', '2018-05-18 18:00:29'), ('24', '我的文件', '20', '20,', '测试文件夹,', '1', '6', '2018-05-18 18:01:42'), ('25', '测试文件123', '', '', '', '1', '6', '2018-05-18 18:02:00'), ('26', '测试', '25', '25,', '测试文件1,', '1', '6', '2018-05-18 18:02:07'), ('27', '测试123', '25', '25,', '测试文件1,', '1', '6', '2018-05-18 18:02:19'), ('28', 'test', '', '', '', '0', '6', '2018-05-18 18:02:34'), ('29', '123', '25', '25,', '测试文件123,', '1', '6', '2018-05-18 18:33:29'), ('3', '测试文件夹1', '2', '2,', '根目录1,', '1', '2', '2018-02-06 17:32:59'), ('30', '123123', '29', '25,29,', '测试文件123,123,', '1', '6', '2018-05-18 18:33:37'), ('31', 'ceshi', '25', '25,', '测试文件123,', '1', '6', '2018-05-18 18:35:48'), ('32', '20180608', '20', '20,', '测试文件夹,', '1', '6', '2018-05-18 18:36:02'), ('33', '111', '25', '25,', '测试文件123,', '1', '6', '2018-05-19 13:20:21'), ('34', '20180519', '', '', '', '1', '6', '2018-05-19 13:34:08'), ('35', '子文件夹', '34', '34,', '20180519,', '1', '6', '2018-05-19 13:34:25'), ('36', '子文件夹1', '34', '34,', '20180519,', '1', '6', '2018-05-19 13:34:38'), ('4', '123', '2', '2,', '根目录1,', '1', '2', '2018-02-07 14:22:17'), ('5', '1234', '2', '2,', '根目录1,', '1', '2', '2018-02-07 14:23:22'), ('6', '12345', '3', '2,3,', '根目录1,测试文件夹1,', '1', '2', '2018-02-07 14:23:26'), ('7', '根目录2', '', '', '', '1', '2', '2018-02-07 14:25:53'), ('8', '根目录3', '', '', '', '1', '2', '2018-02-07 14:25:55'), ('9', '根目录4', '', '', '', '1', '2', '2018-02-07 14:25:58');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_file_share`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_file_share`;
CREATE TABLE `sd_sys_file_share` (
`share_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '文件共享id' ,
`share_user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '分享人id' ,
`receive_user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '接收人id' ,
`share_file_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '共享的文件id' ,
`crtime`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`share_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
COMMENT='文件分享'

;

-- ----------------------------
-- Records of sd_sys_file_share
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_file_share` VALUES ('3', '2', '2', '20', '2018-04-16 11:43:45'), ('4', '2', '2', '33', '2018-04-16 11:48:51'), ('5', '2', '2', '33', '2018-04-16 11:51:09'), ('6', '2', '2', '12', '2018-04-16 11:52:50'), ('7', '2', '2', '33', '2018-04-16 12:00:30'), ('8', '6', '7', '66', '2018-05-18 18:46:18');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_module`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_module`;
CREATE TABLE `sd_sys_module` (
`module_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '模块id' ,
`module_name`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '模块名称' ,
`module_status`  int(5) NOT NULL COMMENT '模块状态' ,
`module_path_id`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '模块id路径' ,
`module_url`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL ,
`module_parent_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '模块父节点id' ,
`module_orderby`  int(5) NULL DEFAULT NULL COMMENT '模块排序' ,
PRIMARY KEY (`module_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_module
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_module` VALUES ('1', '我的主页', '1', '', 'javascript:changeLeft(\'1\')', null, '1'), ('10', '图片', '1', '1,3,', 'javascript:changeContent(\'/fileManage/myFileList.html\',4)', '3', '5'), ('11', '其他', '1', '1,3,', 'javascript:changeContent(\'/fileManage/myFileList.html\',0)', '3', '6'), ('12', '音乐', '1', '1,3,', 'javascript:changeContent(\'/fileManage/myFileList.html\',2)', '3', '3'), ('13', '后台管理', '1', null, 'javascript:openAdminWindow()', null, '3'), ('2', '分享', '1', '', 'javascript:openShareWindow()', null, '2'), ('3', '我的文件', '1', '1,', null, '1', '1'), ('4', '消息管理', '1', '1,', 'javascript:changeContent(\'/notice/myNoticeList.html\',-1)', '1', '2'), ('5', '好友管理', '1', '1,', 'javascript:changeContent(\'/friend/friendManage.html\',-1)', '1', '3'), ('6', '回收站', '1', '1,', 'javascript:changeContent(\'/fileManage/myRecyclebin.html\',-1)', '1', '4'), ('7', '所有文件', '1', '1,3,', 'javascript:changeContent(\'/fileManage/myFileList.html\',-1)', '3', '1'), ('8', '视频', '1', '1,3,', 'javascript:changeContent(\'/fileManage/myFileList.html\',1)', '3', '2'), ('9', '文档', '1', '1,3,', 'javascript:changeContent(\'/fileManage/myFileList.html\',3)', '3', '4');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_register`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_register`;
CREATE TABLE `sd_sys_register` (
`register_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '注册信息id标识' ,
`user_code`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户登录code' ,
`user_password`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户密码' ,
`user_email`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户邮箱' ,
`invalid_time`  datetime NOT NULL COMMENT '注册信息失效时间' ,
`check_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '校验码' ,
PRIMARY KEY (`register_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_register
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_register` VALUES ('2', 'hudy1', 'A6403CBBA6A07C0480B5AF424762769B', '632433151@qq.com', '2017-12-23 00:30:00', 'A8X90n');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_role`;
CREATE TABLE `sd_sys_role` (
`role_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id' ,
`role_name`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称' ,
`crtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`cruser_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '创建用户' ,
PRIMARY KEY (`role_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_role` VALUES ('0', '普通用户', '2018-01-29 11:28:23', '1'), ('1', '系统管理员', '2017-11-25 11:26:21', '1');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_role_module`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_role_module`;
CREATE TABLE `sd_sys_role_module` (
`role_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id' ,
`module_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '模块id' ,
`cruser_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '创建人用户id' ,
`crtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`role_id`, `module_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_role_module
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_role_module` VALUES ('0', '1', '1', '2018-01-29 13:39:19'), ('0', '10', '1', '2018-02-01 21:01:43'), ('0', '11', '1', '2018-02-01 21:01:50'), ('0', '12', '1', '2018-02-03 13:37:22'), ('0', '2', '1', '2018-01-29 13:39:54'), ('0', '3', '1', '2018-01-29 13:40:03'), ('0', '4', '1', '2018-01-29 13:40:12'), ('0', '5', '1', '2018-01-29 13:40:20'), ('0', '6', '1', '2018-01-29 13:40:29'), ('0', '7', '1', '2018-02-01 21:01:06'), ('0', '8', '1', '2018-02-01 21:01:15'), ('0', '9', '1', '2018-02-01 21:01:32'), ('1', '1', '1', '2017-12-24 19:33:03'), ('1', '10', '1', '2018-05-06 10:35:01'), ('1', '11', '1', '2018-05-06 10:35:07'), ('1', '12', '1', '2018-05-06 10:35:13'), ('1', '13', '1', '2018-05-06 10:35:18'), ('1', '2', '1', '2017-12-24 19:33:03'), ('1', '3', '1', '2017-12-24 19:33:03'), ('1', '4', '1', '2017-12-24 19:33:03'), ('1', '5', '1', '2017-12-24 19:33:03'), ('1', '6', '1', '2017-12-24 19:33:03'), ('1', '7', '1', '2018-05-06 10:34:39'), ('1', '8', '1', '2018-05-06 10:34:47'), ('1', '9', '1', '2018-05-06 10:34:54');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_user`;
CREATE TABLE `sd_sys_user` (
`user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID' ,
`user_code`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户登录编码' ,
`user_nikename`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称' ,
`user_gender`  varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户性别' ,
`user_password`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户密码' ,
`user_email`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户电子邮箱' ,
`user_status`  int(5) NOT NULL COMMENT '用户状态：0失效，1正常' ,
`user_headimg_url`  varchar(160) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户头像地址' ,
`crtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`user_id`),
UNIQUE INDEX `index_user_1` (`user_code`) USING BTREE ,
UNIQUE INDEX `index_user_2` (`user_code`, `user_status`) USING BTREE ,
UNIQUE INDEX `user_code` (`user_code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci
COMMENT='用户信息表'

;

-- ----------------------------
-- Records of sd_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_user` VALUES ('1', 'admin', '管理员', '男', 'A6403CBBA6A07C0480B5AF424762769B', '123@qq.com', '1', '', '2017-11-22 11:04:44'), ('6', 'test1', 'test1', null, 'A6403CBBA6A07C0480B5AF424762769B', '632433151@qq.com', '1', null, '2018-05-18 17:40:16');
COMMIT;

-- ----------------------------
-- Table structure for `sd_sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sd_sys_user_role`;
CREATE TABLE `sd_sys_user_role` (
`user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID' ,
`role_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色id' ,
`cruser_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '创建用户' ,
`crtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`user_id`, `role_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sd_sys_user_role` VALUES ('1', '1', '管理员', '2017-11-25 11:26:46');
COMMIT;

-- ----------------------------
-- Table structure for `sd_user_notice`
-- ----------------------------
DROP TABLE IF EXISTS `sd_user_notice`;
CREATE TABLE `sd_user_notice` (
`user_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户id' ,
`notice_id`  varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '消息id' ,
`status`  int(5) NOT NULL COMMENT '0:未读，1:已读' ,
PRIMARY KEY (`user_id`, `notice_id`),
UNIQUE INDEX `user_id, notice_id` (`user_id`, `notice_id`) USING BTREE ,
INDEX `user_id` (`user_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci

;

-- ----------------------------
-- Records of sd_user_notice
-- ----------------------------
BEGIN;
INSERT INTO `sd_user_notice` VALUES ('1', '27', '1'), ('2', '1', '1'), ('2', '10', '1'), ('2', '11', '0'), ('2', '12', '0'), ('2', '13', '0'), ('2', '14', '0'), ('2', '15', '0'), ('2', '16', '1'), ('2', '17', '0'), ('2', '18', '0'), ('2', '19', '0'), ('2', '2', '1'), ('2', '20', '0'), ('2', '21', '0'), ('2', '22', '0'), ('2', '23', '1'), ('2', '3', '0'), ('2', '4', '0'), ('2', '5', '1'), ('2', '6', '1'), ('2', '7', '0'), ('2', '8', '0'), ('2', '9', '0'), ('6', '28', '1'), ('6', '29', '1'), ('7', '28', '0'), ('7', '29', '0');
COMMIT;

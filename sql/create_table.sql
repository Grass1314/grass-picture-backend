/*
 Navicat Premium Dump SQL

 Source Server         : 175.24.73.151
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44-log)
 Source Host           : 175.24.73.151:3306
 Source Schema         : grass_picture

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44-log)
 File Encoding         : 65001

 Date: 11/06/2025 14:21:54
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `url`           varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片 url',
    `name`          varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片名称',
    `introduction`  varchar(512) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '简介',
    `category`      varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '分类',
    `tags`          varchar(512) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '标签（JSON 数组）',
    `picSize`       bigint(20) DEFAULT NULL COMMENT '图片体积',
    `picWidth`      int(11) DEFAULT NULL COMMENT '图片宽度',
    `picHeight`     int(11) DEFAULT NULL COMMENT '图片高度',
    `picScale` double DEFAULT NULL COMMENT '图片宽高比例',
    `picFormat`     varchar(32) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '图片格式',
    `userId`        bigint(20) NOT NULL COMMENT '创建用户 id',
    `createTime`    datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `editTime`      datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    `updateTime`    datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`      tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `reviewStatus`  int(11) NOT NULL DEFAULT '0' COMMENT '审核状态：0-待审核; 1-通过; 2-拒绝',
    `reviewMessage` varchar(512) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '审核信息',
    `reviewerId`    bigint(20) DEFAULT NULL COMMENT '审核人 ID',
    `reviewTime`    datetime                                         DEFAULT NULL COMMENT '审核时间',
    `thumbnailUrl`  varchar(512) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '缩略图 url',
    `spaceId`       bigint(20) DEFAULT NULL COMMENT '空间 id（为空表示公共空间）',
    `picColor`      varchar(16) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '图片主色调',
    PRIMARY KEY (`id`),
    KEY             `idx_name` (`name`),
    KEY             `idx_introduction` (`introduction`),
    KEY             `idx_category` (`category`),
    KEY             `idx_tags` (`tags`),
    KEY             `idx_userId` (`userId`),
    KEY             `idx_reviewStatus` (`reviewStatus`),
    KEY             `idx_spaceId` (`spaceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图片';

-- ----------------------------
-- Table structure for space
-- ----------------------------
DROP TABLE IF EXISTS `space`;
CREATE TABLE `space`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `spaceName`  varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '空间名称',
    `spaceLevel` int(11) DEFAULT '0' COMMENT '空间级别：0-普通版 1-专业版 2-旗舰版',
    `maxSize`    bigint(20) DEFAULT '0' COMMENT '空间图片的最大总大小',
    `maxCount`   bigint(20) DEFAULT '0' COMMENT '空间图片的最大数量',
    `totalSize`  bigint(20) DEFAULT '0' COMMENT '当前空间下图片的总大小',
    `totalCount` bigint(20) DEFAULT '0' COMMENT '当前空间下的图片数量',
    `userId`     bigint(20) NOT NULL COMMENT '创建用户 id',
    `createTime` datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `editTime`   datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    `updateTime` datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`   tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `spaceType`  int(11) NOT NULL DEFAULT '0' COMMENT '空间类型：0-私有 1-团队',
    PRIMARY KEY (`id`),
    KEY          `idx_userId` (`userId`),
    KEY          `idx_spaceName` (`spaceName`),
    KEY          `idx_spaceLevel` (`spaceLevel`),
    KEY          `idx_spaceType` (`spaceType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='空间';

-- ----------------------------
-- Table structure for space_user
-- ----------------------------
DROP TABLE IF EXISTS `space_user`;
CREATE TABLE `space_user`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `spaceId`    bigint(20) NOT NULL COMMENT '空间 id',
    `userId`     bigint(20) NOT NULL COMMENT '用户 id',
    `spaceRole`  varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT 'viewer' COMMENT '空间角色：viewer/editor/admin',
    `createTime` datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_spaceId_userId` (`spaceId`,`userId`),
    KEY          `idx_spaceId` (`spaceId`),
    KEY          `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='空间用户关联';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `userAccount`   varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
    `userPassword`  varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `userName`      varchar(256) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '用户昵称',
    `userAvatar`    varchar(1024) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '用户头像',
    `userProfile`   varchar(512) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '用户简介',
    `userRole`      varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin',
    `editTime`      datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    `createTime`    datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`    datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`      tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `vipExpireTime` datetime                                         DEFAULT NULL COMMENT '会员过期时间',
    `vipCode`       varchar(128) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '会员兑换码',
    `vipNumber`     bigint(20) DEFAULT NULL COMMENT '会员编号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_userAccount` (`userAccount`),
    KEY             `idx_userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=1922568286434504707 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';

ALTER TABLE `grass_picture`.`user`
    ADD COLUMN `shareCode` varchar(20) NULL DEFAULT NULL COMMENT '分享码' AFTER `vipNumber`,
ADD COLUMN `inviteUser` bigint NULL DEFAULT NULL COMMENT '邀请用户 id' AFTER `shareCode`;

SET
FOREIGN_KEY_CHECKS = 1;

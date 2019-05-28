/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : springssm

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2019-05-28 23:19:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `images`
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(600) DEFAULT NULL,
  `name` varchar(600) DEFAULT NULL,
  `item_id` int(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of images
-- ----------------------------
INSERT INTO `images` VALUES ('6', 'http:/localhost:8090/images20190518_截屏_20190510_175335.jpg', '20190518_截屏_20190510_175335.jpg', null);
INSERT INTO `images` VALUES ('7', 'http:/localhost:8090/static/images/20190518_截屏_20190510_175031.jpg', '20190518_截屏_20190510_175031.jpg', null);
INSERT INTO `images` VALUES ('8', 'http:/localhost:8090/static/images/20190518_截屏_20190510_175004.jpg', '20190518_截屏_20190510_175004.jpg', null);
INSERT INTO `images` VALUES ('9', 'http:/localhost:8090/static/ images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('10', 'http:/localhost:8090/static/ images/20190518_258b24e8de49356f.jpg', '20190518_258b24e8de49356f.jpg', null);
INSERT INTO `images` VALUES ('11', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('12', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('13', 'http:/localhost:8090/images/20190518_599661240001c79712800800.jpg', '20190518_599661240001c79712800800.jpg', null);
INSERT INTO `images` VALUES ('14', 'http:/localhost:8090/images/20190518_599660be0001380114400900.jpg', '20190518_599660be0001380114400900.jpg', null);
INSERT INTO `images` VALUES ('15', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('16', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('17', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('18', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('19', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('20', 'http:/localhost:8090/images/20190518_599661240001c79712800800.jpg', '20190518_599661240001c79712800800.jpg', null);
INSERT INTO `images` VALUES ('21', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('22', 'http:/localhost:8090/images/20190518_lemon.jpg', '20190518_lemon.jpg', null);
INSERT INTO `images` VALUES ('23', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('24', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('25', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('26', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('27', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('28', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('29', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('30', 'http:/localhost:8090/images/20190519_lemon.jpg', '20190519_lemon.jpg', null);
INSERT INTO `images` VALUES ('31', 'http:/localhost:8090/images/lemon.jpg', 'lemon.jpg', null);
INSERT INTO `images` VALUES ('32', 'http:/localhost:8090/images/lemon.jpg', 'lemon.jpg', null);
INSERT INTO `images` VALUES ('33', 'http:/localhost:8090/images/lemon.jpg', 'lemon.jpg', null);
INSERT INTO `images` VALUES ('34', 'http:/localhost:8090/images/lemon.jpg', 'lemon.jpg', null);
INSERT INTO `images` VALUES ('35', 'images/lemon.jpg', 'lemon.jpg', null);
INSERT INTO `images` VALUES ('36', 'images/599661240001c79712800800.jpg', '599661240001c79712800800.jpg', null);
INSERT INTO `images` VALUES ('37', '../images/lemon.jpg', 'lemon.jpg', null);
INSERT INTO `images` VALUES ('38', '../images/lemon.jpg', 'lemon.jpg', null);

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT '标题',
  `price` double(64,0) DEFAULT '0',
  `desciption` varchar(500) DEFAULT '无',
  `sales` int(11) DEFAULT '0',
  `image` varchar(255) DEFAULT 'www.hh.com',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('13', '模态框测试', '12', '模态框测试', '5', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('15', 'ff', '120', 'fsdffgdfg', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('20', '图片上传测试', '1200', '图片上传测试', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('21', '图片上传测试', '1200', '图片上传测试', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('22', '图片上传测试', '1200', '图片上传测试', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('23', '图片上传测试', '1200', '图片上传测试', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('24', '图片上传测试', '1200', '图片上传测试', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('25', '图片上传测试', '1200', '图片上传测试', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('27', '图片测试第二次', '1200', '图片测试第二次', '100', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('28', '测试三', '130', '测试三', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('29', '测试三', '130', '测试三', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('30', '测试三', '12', '测试三', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('31', '测试三', '12', '测试三', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('32', '测试三', '12', '测试三', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('33', '测试三', '12', '测试三', '0', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `item` VALUES ('34', '测试五', '120', '测试五', '0', 'http:/localhost:8090/images/截屏_20190510_175004.jpg;http:/localhost:8090/images/截屏_20190510_175304.jpg;http:/localhost:8090/images/截屏_20190510_175315.jpg;http:/localhost:8090/images/截屏_20190510_175300.jpg;http:/localhost:8090/images/截屏_20190510_175031.jpg;');
INSERT INTO `item` VALUES ('35', '测试五', '1200', '测试三', '0', 'http:/localhost:8090/images/截屏_20190510_175004.jpg;');

-- ----------------------------
-- Table structure for `item_stock`
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) DEFAULT '0',
  `item_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES ('13', '3', '13');
INSERT INTO `item_stock` VALUES ('15', '12', '15');
INSERT INTO `item_stock` VALUES ('19', '12', '22');
INSERT INTO `item_stock` VALUES ('20', '12', '21');
INSERT INTO `item_stock` VALUES ('21', '12', '20');
INSERT INTO `item_stock` VALUES ('23', '12', '23');
INSERT INTO `item_stock` VALUES ('24', '12', '25');
INSERT INTO `item_stock` VALUES ('25', '12', '24');
INSERT INTO `item_stock` VALUES ('27', '1200', '27');
INSERT INTO `item_stock` VALUES ('28', '100', '28');
INSERT INTO `item_stock` VALUES ('29', '100', '29');
INSERT INTO `item_stock` VALUES ('30', '12', '30');
INSERT INTO `item_stock` VALUES ('31', '12', '31');
INSERT INTO `item_stock` VALUES ('32', '12', '32');
INSERT INTO `item_stock` VALUES ('33', '12', '33');
INSERT INTO `item_stock` VALUES ('34', '120', '34');
INSERT INTO `item_stock` VALUES ('35', '120', '35');

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(64) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_price` double DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `order_price` double DEFAULT '0',
  `promo_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019050600000800', '5', '13', '12', '1', '12', '0');
INSERT INTO `order_info` VALUES ('2019050600000900', '5', '13', '12', '1', '12', '0');
INSERT INTO `order_info` VALUES ('2019050600001000', '5', '13', '12', '1', '12', '0');
INSERT INTO `order_info` VALUES ('2019050600001100', '5', '13', '12', '1', '12', '0');
INSERT INTO `order_info` VALUES ('2019050700001200', '5', '13', '120', '1', '120', '1');
INSERT INTO `order_info` VALUES ('2019050700001300', '5', '13', '120', '1', '120', '1');
INSERT INTO `order_info` VALUES ('2019050700001400', '5', '11', '12', '1', '12', '0');
INSERT INTO `order_info` VALUES ('2019050700001500', '5', '12', '120', '1', '120', '1');
INSERT INTO `order_info` VALUES ('2019050700001600', '5', '12', '120', '2', '240', '1');
INSERT INTO `order_info` VALUES ('2019050700001700', '5', '12', '120', '9', '1080', '1');
INSERT INTO `order_info` VALUES ('2019050800001800', '5', '12', '120', '1', '120', '1');
INSERT INTO `order_info` VALUES ('2019050800001900', '5', '12', '120', '7', '840', '1');
INSERT INTO `order_info` VALUES ('2019050800002000', '5', '12', '120', '1', '120', '1');
INSERT INTO `order_info` VALUES ('2019050800002100', '5', '12', '120', '79', '9480', '1');
INSERT INTO `order_info` VALUES ('2019052800002200', '22', '13', '120', '1', '120', '1');
INSERT INTO `order_info` VALUES ('2019052800002300', '22', '13', '120', '1', '120', '1');

-- ----------------------------
-- Table structure for `promo`
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT '0000-00-00 00:00:00',
  `item_id` int(11) NOT NULL,
  `promo_item_price` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES ('1', '双11特惠', '2019-05-26 18:11:00', '2019-05-29 21:03:02', '13', '120');

-- ----------------------------
-- Table structure for `sequence_info`
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `name` varchar(255) NOT NULL,
  `current_value` int(11) DEFAULT '0',
  `step` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', '24', '1');

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT 'ad',
  `gender` tinyint(4) DEFAULT NULL COMMENT '//1男，0女',
  `age` int(11) DEFAULT NULL,
  `telphone` varchar(255) DEFAULT NULL,
  `register_mode` varchar(255) DEFAULT NULL COMMENT '//byphone,bywechat,byalipy',
  `thrid_party_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`),
  UNIQUE KEY `name_unique_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'gg', '0', '12', '123', 'byphone', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('5', '测试', '0', '12', '19960441024', 'byphone', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('6', '123', '0', '12', '123456', 'byphone', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('9', '123456', '1', '12', '12333', 'byphone', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('16', '测试1', '1', '120', '123331', null, 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('20', 'ad', '1', '12', '12', null, 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('21', '测试001', '1', '3', '19900000000', 'byphone', 'http:/localhost:8090/images/lemon.jpg');
INSERT INTO `user_info` VALUES ('22', '测试四', '1', '3', '110', 'byphone', 'http:/localhost:8090/images/lemon.jpg');

-- ----------------------------
-- Table structure for `user_password`
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(255) DEFAULT '**',
  `user_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('1', 'ddd', '1');
INSERT INTO `user_password` VALUES ('4', 'ICy5YqxZB1uWSwcVLSNLcA==', '5');
INSERT INTO `user_password` VALUES ('5', 'ICy5YqxZB1uWSwcVLSNLcA==', '6');
INSERT INTO `user_password` VALUES ('7', 'l51HKoSAS59ke8GFqHeotQ==', '9');
INSERT INTO `user_password` VALUES ('9', 'ICy5YqxZB1uWSwcVLSNLcA==', '16');
INSERT INTO `user_password` VALUES ('10', 'Qpf0SxOVUjUkWySXOZ16kw==', '21');
INSERT INTO `user_password` VALUES ('11', 'X5P5g1JN7z3KRkRp0s+fPg==', '22');

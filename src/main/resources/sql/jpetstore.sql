/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50135
Source Host           : localhost:3306
Source Database       : jpetstore

Target Server Type    : MYSQL
Target Server Version : 50135
File Encoding         : 65001

Date: 2012-09-28 21:46:57
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`
(
    id                    BIGINT PRIMARY KEY AUTO_INCREMENT,
    username              varchar(80) NOT NULL UNIQUE,
    `email`               varchar(80) NOT NULL,
    `first_name`          varchar(80) NOT NULL,
    `last_name`           varchar(80) NOT NULL,
    `status`              INT                  DEFAULT 0,
    address1              varchar(80) NOT NULL,
    address2              varchar(40)          DEFAULT NULL,
    `city`                varchar(80) NOT NULL,
    `state`               varchar(80) NOT NULL,
    `zip`                 varchar(20) NOT NULL,
    `country`             varchar(20) NOT NULL,
    `phone`               varchar(80) NOT NULL,
    favourite_category_id varchar(80) REFERENCES bannerdata (favcategory),
    profile_id            BIGINT REFERENCES profile (id),
    register_date         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `bannerdata`
-- ----------------------------
DROP TABLE IF EXISTS `bannerdata`;
CREATE TABLE `bannerdata`
(
    `favcategory` varchar(80) NOT NULL,
    banner_name   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`favcategory`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name`     varchar(80) DEFAULT NULL,
    image_path varchar(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for pet_breed
-- ----------------------------
DROP TABLE IF EXISTS pet_breed;
CREATE TABLE pet_breed
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    `name`      varchar(80)  DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    image_path  varchar(255) DEFAULT NULL,
    KEY `petBreedCat` (category_id),
    KEY `petBreedName` (`name`),
    CONSTRAINT `fk_pet_breed_1` FOREIGN KEY (category_id) REFERENCES `category` (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    product_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    pet_breed_id BIGINT NOT NULL,
    list_price   decimal(10, 2) DEFAULT NULL,
    unit_cost    decimal(10, 2) DEFAULT NULL,
    supplier_id  int REFERENCES supplier (suppid),
    `status`     varchar(2)     DEFAULT NULL,
    quantity     INT            DEFAULT 0,
    KEY `fk_product_2` (supplier_id),
    KEY `productProd` (pet_breed_id),
    CONSTRAINT `fk_product_1` FOREIGN KEY (pet_breed_id) REFERENCES pet_breed (id),
    CONSTRAINT `fk_product_2` FOREIGN KEY (supplier_id) REFERENCES `supplier` (`suppid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for `product_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute`;
CREATE TABLE `product_attribute`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT REFERENCES product (product_id),
    content    varchar(127) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


-- ----------------------------
-- Table structure for `line_item`
-- ----------------------------
DROP TABLE IF EXISTS line_item;
CREATE TABLE line_item
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    order_id     bigint REFERENCES orders (order_id),
    `product_id` bigint         NOT NULL REFERENCES product (product_id),
    `quantity`   int            NOT NULL,
    unit_price   decimal(10, 2) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    order_id       bigint AUTO_INCREMENT,
    `user_id`      bigint REFERENCES account (id),
    `order_date`   date           NOT NULL,
    `ship_address` varchar(80)    NOT NULL,
    `ship_city`    varchar(80)    NOT NULL,
    `ship_state`   varchar(80)    NOT NULL,
    `ship_country` varchar(20)    NOT NULL,
    `total_price`  decimal(10, 2) NOT NULL,
    status         INT            NOT NULL,
    PRIMARY KEY (order_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `orderstatus`
-- ----------------------------
DROP TABLE IF EXISTS `orderstatus`;
CREATE TABLE `orderstatus`
(
    `orderid`   bigint REFERENCES orders (order_id),
    `linenum`   bigint     NOT NULL,
    `timestamp` date       NOT NULL,
    `status`    varchar(2) NOT NULL,
    PRIMARY KEY (`orderid`, `linenum`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `profile`
-- ----------------------------
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile`
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    `langpref`    varchar(80) NOT NULL,
    `favcategory` varchar(30) DEFAULT NULL,
    `mylistopt`   BOOL        DEFAULT FALSE,
    `banneropt`   BOOL        DEFAULT FALSE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence`
(
    `name`   varchar(30) NOT NULL,
    `nextid` INT         NOT NULL,
    PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `signon`
-- ----------------------------
DROP TABLE IF EXISTS `signon`;
CREATE TABLE `signon`
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` varchar(25) REFERENCES account (username),
    `password` varchar(25) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `supplier`
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`
(
    `suppid` int        NOT NULL,
    `name`   varchar(80) DEFAULT NULL,
    `status` varchar(2) NOT NULL,
    `addr1`  varchar(80) DEFAULT NULL,
    `addr2`  varchar(80) DEFAULT NULL,
    `city`   varchar(80) DEFAULT NULL,
    `state`  varchar(80) DEFAULT NULL,
    `zip`    varchar(5)  DEFAULT NULL,
    `phone`  varchar(80) DEFAULT NULL,
    PRIMARY KEY (`suppid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



DROP TABLE IF EXISTS cart_item;
CREATE TABLE cart_item
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    username     VARCHAR(80) REFERENCES account (username),
    product_id   BIGINT REFERENCES product (product_id),
    quantity     INT  DEFAULT 0,
    in_stock     BOOL DEFAULT TRUE,
    is_purchased BOOL DEFAULT FALSE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS admin;
CREATE TABLE admin
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_zh_0900_as_cs;


SET FOREIGN_KEY_CHECKS = 1;
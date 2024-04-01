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
    `status`              varchar(2)  DEFAULT NULL,
    address1              varchar(80) NOT NULL,
    address2              varchar(40) DEFAULT NULL,
    `city`                varchar(80) NOT NULL,
    `state`               varchar(80) NOT NULL,
    `zip`                 varchar(20) NOT NULL,
    `country`             varchar(20) NOT NULL,
    `phone`               varchar(80) NOT NULL,
    favourite_category_id varchar(80) REFERENCES bannerdata (favcategory),
    profile_id            BIGINT REFERENCES profile (id)
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
    category_id varchar(10) NOT NULL,
    `name`      varchar(80)  DEFAULT NULL,
    description varchar(255) DEFAULT NULL,
    PRIMARY KEY (category_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `inventory`
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`
(
    `item_id`  varchar(10) NOT NULL REFERENCES item (item_id),
    `quantity` int         NOT NULL,
    PRIMARY KEY (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `item_id`   varchar(10) NOT NULL UNIQUE KEY,
    product_id  VARCHAR(10) NOT NULL, -- REFERENCES product(product_id)
    list_price  decimal(10, 2) DEFAULT NULL,
    unit_cost   decimal(10, 2) DEFAULT NULL,
    supplier_id int REFERENCES supplier (suppid),
    `status`    varchar(2)     DEFAULT NULL,
    attribute1  varchar(80)    DEFAULT NULL,
    attribute2  varchar(80)    DEFAULT NULL,
    attribute3  varchar(80)    DEFAULT NULL,
    attribute4  varchar(80)    DEFAULT NULL,
    attribute5  varchar(80)    DEFAULT NULL,
    quantity    INT            DEFAULT 0,
    KEY `fk_item_2` (supplier_id),
    KEY `itemProd` (product_id),
    CONSTRAINT `fk_item_1` FOREIGN KEY (product_id) REFERENCES `product` (product_id),
    CONSTRAINT `fk_item_2` FOREIGN KEY (supplier_id) REFERENCES `supplier` (`suppid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `lineitem`
-- ----------------------------
DROP TABLE IF EXISTS `lineitem`;
CREATE TABLE `lineitem`
(
    `orderid`   bigint REFERENCES orders (orderid),
    `linenum`   int            NOT NULL,
    `item_id`   varchar(10)    NOT NULL REFERENCES item (item_id),
    `quantity`  int            NOT NULL,
    `unitprice` decimal(10, 2) NOT NULL,
    PRIMARY KEY (`orderid`, `linenum`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`
(
    `orderid`         bigint         NOT NULL,
    `userid`          varchar(80)    NOT NULL,
    `orderdate`       date           NOT NULL,
    `shipaddr1`       varchar(80)    NOT NULL,
    `shipaddr2`       varchar(80) DEFAULT NULL,
    `shipcity`        varchar(80)    NOT NULL,
    `shipstate`       varchar(80)    NOT NULL,
    `shipzip`         varchar(20)    NOT NULL,
    `shipcountry`     varchar(20)    NOT NULL,
    `billaddr1`       varchar(80)    NOT NULL,
    `billaddr2`       varchar(80) DEFAULT NULL,
    `billcity`        varchar(80)    NOT NULL,
    `billstate`       varchar(80)    NOT NULL,
    `billzip`         varchar(20)    NOT NULL,
    `billcountry`     varchar(20)    NOT NULL,
    `courier`         varchar(80)    NOT NULL,
    `totalprice`      decimal(10, 2) NOT NULL,
    `billtofirstname` varchar(80)    NOT NULL,
    `billtolastname`  varchar(80)    NOT NULL,
    `shiptofirstname` varchar(80)    NOT NULL,
    `shiptolastname`  varchar(80)    NOT NULL,
    `creditcard`      varchar(80)    NOT NULL,
    `exprdate`        varchar(7)     NOT NULL,
    `cardtype`        varchar(80)    NOT NULL,
    `locale`          varchar(80)    NOT NULL,
    PRIMARY KEY (`orderid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `orderstatus`
-- ----------------------------
DROP TABLE IF EXISTS `orderstatus`;
CREATE TABLE `orderstatus`
(
    `orderid`   bigint REFERENCES orders (orderid),
    `linenum`   bigint     NOT NULL,
    `timestamp` date       NOT NULL,
    `status`    varchar(2) NOT NULL,
    PRIMARY KEY (`orderid`, `linenum`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS product;
CREATE TABLE `product`
(
    product_id  varchar(10) PRIMARY KEY,
    category_id varchar(10) NOT NULL,
    `name`      varchar(80)  DEFAULT NULL,
    description     varchar(255) DEFAULT NULL,
    KEY `productCat` (category_id),
    KEY `productName` (`name`),
    CONSTRAINT `fk_product_1` FOREIGN KEY (category_id) REFERENCES `category` (category_id)
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
    `mylistopt`   BOOL        DEFAULT NULL,
    `banneropt`   BOOL        DEFAULT NULL
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
    `username` varchar(25) NOT NULL,
    `password` varchar(25) NOT NULL,
    PRIMARY KEY (`username`)
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



DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    userid       VARCHAR(80) REFERENCES account (username),
    item_id      VARCHAR(10) REFERENCES item (item_id),
    quantity     INT  DEFAULT 0,
    in_stock     BOOL DEFAULT TRUE,
    is_purchased BOOL DEFAULT FALSE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS admin;
CREATE TABLE admin
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32),
    password VARCHAR(64)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO admin(username, password)
VALUES ('Admin', '[Admin]');


SET FOREIGN_KEY_CHECKS = 1;
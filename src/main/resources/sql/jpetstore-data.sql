SET FOREIGN_KEY_CHECKS = 0;


SET @BIRD = 1;
SET @CAT = 2;
SET @DOG = 3;
SET @FISH = 4;
SET @REPTILE = 5;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` (username, email, first_name, last_name, status, address1, address2, city, state, zip, country,
                       phone, profile_id)
VALUES ('a', 'a', 'a', 'a', null, 'a', 'a', 'aa', 'a', 'a', 'a', 'a', 1);
INSERT INTO `account` (username, email, first_name, last_name, status, address1, address2, city, state, zip, country,
                       phone, profile_id)
VALUES ('ACID', 'acid@yourdomain.com', 'ABC', 'XYX', 'OK', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA',
        '94303', 'USA', '555-555-5555', 2);
INSERT INTO `account` (username, email, first_name, last_name, status, address1, address2, city, state, zip, country,
                       phone, profile_id)
VALUES ('j2ee', 'yourname@yourdomain.com', 'ABC', 'XYX', null, '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto',
        'CA', '94303', 'USA', '555-555-5555', 3);


-- ----------------------------
-- Records of bannerdata
-- ----------------------------
INSERT INTO `bannerdata`
VALUES ('BIRDS', '<image src=\"/images/banner_birds.gif\">');
INSERT INTO `bannerdata`
VALUES ('CATS', '<image src=\"/images/banner_cats.gif\">');
INSERT INTO `bannerdata`
VALUES ('DOGS', '<image src=\"/images/banner_dogs.gif\">');
INSERT INTO `bannerdata`
VALUES ('FISH', '<image src=\"/images/banner_fish.gif\">');
INSERT INTO `bannerdata`
VALUES ('REPTILES', '<image src=\"/images/banner_reptiles.gif\">');

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category`(id, name, image_path)
VALUES (@BIRD, 'Birds', '/images/birds_icon.gif');
INSERT INTO `category`
VALUES (@CAT, 'Cats', '/images/cats_icon.gif');
INSERT INTO `category`
VALUES (@DOG, 'Dogs', '/images/dogs_icon.gif');
INSERT INTO `category`
VALUES (@FISH, 'Fish', '/images/fish_icon.gif');
INSERT INTO `category`
VALUES (@REPTILE, 'Reptiles', '/images/reptiles_icon.gif');

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-1', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-10', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-11', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-12', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-13', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-14', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-15', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-16', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-17', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-18', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-19', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-2', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-20', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-21', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-22', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-23', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-24', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-25', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-26', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-27', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-28', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-3', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-4', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-5', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-6', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-7', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-8', '10000');
INSERT INTO `inventory`(item_id, quantity)
VALUES ('EST-9', '10000');


-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5, quantity)
VALUES ('EST-1', 'FI-FW-01', '16.50', '10.00', '1', 'P', 'Large', null, null, null, null, 3);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-10', 'K9-DL-01', '18.50', '12.00', '1', 'P', 'Spotted Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-11', 'RP-SN-01', '18.50', '12.00', '1', 'P', 'Venomless', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-12', 'RP-SN-01', '18.50', '12.00', '1', 'P', 'Rattleless', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-13', 'RP-LI-02', '18.50', '12.00', '1', 'P', 'Green Adult', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-14', 'FL-DSH-01', '58.50', '12.00', '1', 'P', 'Tailless', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-15', 'FL-DSH-01', '23.50', '12.00', '1', 'P', 'With tail', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-16', 'FL-DLH-02', '93.50', '12.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-17', 'FL-DLH-02', '93.50', '12.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-18', 'AV-CB-01', '193.50', '92.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-19', 'AV-SB-02', '15.50', '2.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-2', 'FI-SW-01', '16.50', '10.00', '1', 'P', 'Small', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-20', 'FI-FW-02', '5.50', '2.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-21', 'FI-FW-02', '5.29', '1.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-22', 'K9-RT-02', '135.50', '100.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-23', 'K9-RT-02', '145.49', '100.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-24', 'K9-RT-02', '255.50', '92.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-25', 'K9-RT-02', '325.29', '90.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-26', 'K9-CW-01', '125.50', '92.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-27', 'K9-CW-01', '155.29', '90.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-28', 'K9-RT-01', '155.29', '90.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-3', 'FI-SW-02', '18.50', '12.00', '1', 'P', 'Toothless', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-4', 'FI-FW-01', '18.50', '12.00', '1', 'P', 'Spotted', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-5', 'FI-FW-01', '18.50', '12.00', '1', 'P', 'Spotless', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-6', 'K9-BD-01', '18.50', '12.00', '1', 'P', 'Male Adult', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-7', 'K9-BD-01', '18.50', '12.00', '1', 'P', 'Female Puppy', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-8', 'K9-PO-02', '18.50', '12.00', '1', 'P', 'Male Puppy', null, null, null, null);
INSERT INTO `item`(item_id, product_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-9', 11, '18.50', '12.00', '1', 'P', 'Spotless Male Puppy', null, null, null, null);

-- ----------------------------
-- Records of lineitem
-- ----------------------------

-- ----------------------------
-- Records of orders
-- ----------------------------


-- ----------------------------
-- Records of orderstatus
-- ----------------------------


-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('AV-CB-01', @BIRD, 'Amazon Parrot', '<image src=\"/images/bird4.gif\">Great companion for up to 75 years');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('AV-SB-02', @BIRD, 'Finch', '<image src=\"/images/bird1.gif\">Great stress reliever');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('FI-FW-01', @FISH, 'Koi', '<image src=\"/images/fish3.gif\">Fresh Water fish from Japan');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('FI-FW-02', @FISH, 'Goldfish', '<image src=\"/images/fish2.gif\">Fresh Water fish from China');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('FI-SW-01', @FISH, 'Angelfish', '<image src=\"/images/fish1.jpg\">Salt Water fish from Australia');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('FI-SW-02', @FISH, 'Tiger Shark', '<image src=\"/images/fish4.gif\">Salt Water fish from Australia');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('FL-DLH-02', @CAT, 'Persian', '<image src=\"/images/cat1.gif\">Friendly house cat, doubles as a princess');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('FL-DSH-01', @CAT, 'Manx', '<image src=\"/images/cat3.gif\">Great for reducing mouse populations');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('K9-BD-01', @DOG, 'Bulldog', '<image src=\"/images/dog2.gif\">Friendly dog from England');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('K9-CW-01', @DOG, 'Chihuahua', '<image src=\"/images/dog4.gif\">Great companion dog');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('K9-DL-01', @DOG, 'Dalmation', '<image src=\"/images/dog5.gif\">Great dog for a Fire Station');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('K9-PO-02', @DOG, 'Poodle', '<image src=\"/images/dog6.gif\">Cute dog from France');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('K9-RT-01', @DOG, 'Golden Retriever', '<image src=\"/images/dog1.gif\">Great family dog');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('K9-RT-02', @DOG, 'Labrador Retriever', '<image src=\"/images/dog5.gif\">Great hunting dog');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('RP-LI-02', @REPTILE, 'Iguana', '<image src=\"/images/lizard2.gif\">Friendly green friend');
INSERT INTO `product` (product_id, category_id, name, description)
VALUES ('RP-SN-01', @REPTILE, 'Rattlesnake', '<image src=\"/images/lizard3.gif\">Doubles as a watch dog');


-- ----------------------------
-- Records of profile
-- ----------------------------
INSERT INTO `profile`(id, langpref, favcategory, mylistopt, banneropt)
VALUES (1, 'japanese', 'DOGS', FALSE, false);
INSERT INTO `profile` (id, langpref, favcategory, mylistopt, banneropt)
VALUES (2, 'english', 'CATS', '1', '1');
INSERT INTO `profile`(id, langpref, favcategory, mylistopt, banneropt)
VALUES (3, 'english', 'FISH', '1', '1');

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence`
VALUES ('linenum', '1000');
INSERT INTO `sequence`
VALUES ('ordernum', '1000');

-- ----------------------------
-- Records of signon
-- ----------------------------
INSERT INTO `signon`(username, password)
VALUES ('a', 'a');
INSERT INTO `signon`(username, password)
VALUES ('ACID', 'ACID');
INSERT INTO `signon`(username, password)
VALUES ('j2ee', 'j2ee');


-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier`
VALUES ('1', 'XYZ Pets', 'AC', '600 Avon Way', '', 'Los Angeles', 'CA', '94024', '212-947-0797');
INSERT INTO `supplier`
VALUES ('2', 'ABC Pets', 'AC', '700 Abalone Way', '', 'San Francisco ', 'CA', '94024', '415-947-0797');

SET FOREIGN_KEY_CHECKS = 1;
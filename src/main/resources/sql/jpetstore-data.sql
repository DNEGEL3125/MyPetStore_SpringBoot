SET FOREIGN_KEY_CHECKS = 0;


SET @BIRD = 1;
SET @CAT = 2;
SET @DOG = 3;
SET @FISH = 4;
SET @REPTILE = 5;

SET @Amazon_Parrot_id = 1;
SET @Angelfish_id = 2;
SET @Bulldog_id = 3;
SET @Chihuahua_id = 4;
SET @Dalmation_id = 5;
SET @Finch_id = 6;
SET @Golden_Retriever_id = 7;
SET @Goldfish_id = 8;
SET @Iguana_id = 9;
SET @Koi_id = 10;
SET @Labrador_Retriever_id = 11;
SET @Manx_id = 12;
SET @Persian_id = 13;
SET @Poodle_id = 14;
SET @Rattlesnake_id = 15;
SET @Tiger_Shark_id = 16;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` (username, email, first_name, last_name, status, address, city, state, zip, country,
                       phone, profile_id)
VALUES ('a', 'a', 'a', 'a', 0, 'a', 'a', 'aa', 'a', 'a', 'a', 1);
INSERT INTO `account` (username, email, first_name, last_name, status, address, city, state, zip, country,
                       phone, profile_id)
VALUES ('ACID', 'acid@yourdomain.com', 'ABC', 'XYX', 0, '901 San Antonio Road', 'Palo Alto', 'CA',
        '94303', 'USA', '555-555-5555', 2);
INSERT INTO `account` (username, email, first_name, last_name, status, address, city, state, zip, country,
                       phone, profile_id)
VALUES ('j2ee', 'yourname@yourdomain.com', 'ABC', 'XYX', 0, '901 San Antonio Road', 'Palo Alto',
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
INSERT INTO `category`(id, category_name, image_path)
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
-- Records of product
-- ----------------------------
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status, quantity)
VALUES (1, @Labrador_Retriever_id, '16.50', '10.00', '1', 'P', 3);
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (2, @Golden_Retriever_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status, quantity)
VALUES (3, @Tiger_Shark_id, '18.50', '12.00', '1', 'P', 99);
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (4, @Tiger_Shark_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (5, @Rattlesnake_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (6, @Chihuahua_id, '58.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (7, @Chihuahua_id, '23.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (8, @Bulldog_id, '93.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (9, @Bulldog_id, '93.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (10, @Amazon_Parrot_id, '193.50', '92.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (11, @Angelfish_id, '15.50', '2.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (12, @Persian_id, '16.50', '10.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (13, @Manx_id, '5.50', '2.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (14, @Manx_id, '5.29', '1.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (15, @Koi_id, '135.50', '100.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (16, @Koi_id, '145.49', '100.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (17, @Koi_id, '255.50', '92.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (18, @Koi_id, '325.29', '90.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (19, @Finch_id, '125.50', '92.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (20, @Finch_id, '155.29', '90.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (21, @Iguana_id, '155.29', '90.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (22, @Poodle_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (23, @Labrador_Retriever_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (24, @Labrador_Retriever_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (25, @Dalmation_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (26, @Dalmation_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (27, @Goldfish_id, '18.50', '12.00', '1', 'P');
INSERT INTO `product`(product_id, pet_breed_id, list_price, unit_cost, supplier_id, status)
VALUES (28, 11, '18.50', '12.00', '1', 'P');

-- ----------------------------
-- Records of product_attribute
-- ----------------------------

INSERT INTO product_attribute (product_id, content)
VALUES ('1', 'Large');


INSERT INTO product_attribute (product_id, content)
VALUES ('2', 'Spotted Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('3', 'Venomless');


INSERT INTO product_attribute (product_id, content)
VALUES ('4', 'Rattleless');


INSERT INTO product_attribute (product_id, content)
VALUES ('5', 'Green Adult');


INSERT INTO product_attribute (product_id, content)
VALUES ('6', 'Tailless');


INSERT INTO product_attribute (product_id, content)
VALUES ('7', 'With tail');


INSERT INTO product_attribute (product_id, content)
VALUES ('8', 'Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('9', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('10', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('11', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('12', 'Small');


INSERT INTO product_attribute (product_id, content)
VALUES ('13', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('14', 'Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('15', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('16', 'Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('17', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('18', 'Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('19', 'Adult Male');


INSERT INTO product_attribute (product_id, content)
VALUES ('20', 'Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('21', 'Adult Female');


INSERT INTO product_attribute (product_id, content)
VALUES ('22', 'Toothless');


INSERT INTO product_attribute (product_id, content)
VALUES ('23', 'Spotted');


INSERT INTO product_attribute (product_id, content)
VALUES ('24', 'Spotless');


INSERT INTO product_attribute (product_id, content)
VALUES ('25', 'Male Adult');


INSERT INTO product_attribute (product_id, content)
VALUES ('26', 'Female Puppy');


INSERT INTO product_attribute (product_id, content)
VALUES ('27', 'Male Puppy');


INSERT INTO product_attribute (product_id, content)
VALUES ('28', 'Spotless Male Puppy');

-- ----------------------------
-- Records of line_item
-- ----------------------------

-- ----------------------------
-- Records of orders
-- ----------------------------


-- ----------------------------
-- Records of orderstatus
-- ----------------------------


-- ----------------------------
-- Records of pet_breed
-- ----------------------------
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Amazon_Parrot_id, @BIRD, 'Amazon Parrot', 'Great companion for up to 75 years', '/images/bird4.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Angelfish_id, @BIRD, 'Finch', 'Great stress reliever', '/images/bird1.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Labrador_Retriever_id, @FISH, 'Koi', 'Fresh Water fish from Japan', '/images/fish3.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Manx_id, @FISH, 'Goldfish', 'Fresh Water fish from China', '/images/fish2.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Persian_id, @FISH, 'Angelfish', 'Salt Water fish from Australia', '/images/fish1.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Poodle_id, @FISH, 'Tiger Shark', 'Salt Water fish from Australia', '/images/fish4.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Bulldog_id, @CAT, 'Persian', 'Friendly house cat, doubles as a princess', '/images/cat1.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Chihuahua_id, @CAT, 'Manx', 'Great for reducing mouse populations', '/images/cat3.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Dalmation_id, @DOG, 'Bulldog', 'Friendly dog from England', '/images/dog2.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Finch_id, @DOG, 'Chihuahua', 'Great companion dog', '/images/dog4.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Golden_Retriever_id, @DOG, 'Dalmation', 'Great dog for a Fire Station', '/images/dog5.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Goldfish_id, @DOG, 'Poodle', 'Cute dog from France', '/images/dog6.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Iguana_id, @DOG, 'Golden Retriever', 'Great family dog', '/images/dog1.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Koi_id, @DOG, 'Labrador Retriever', 'Great hunting dog', '/images/dog5.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Rattlesnake_id, @REPTILE, 'Iguana', 'Friendly green friend', '/images/lizard2.gif');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Tiger_Shark_id, @REPTILE, 'Rattlesnake', 'Doubles as a watch dog', '/images/lizard3.gif');


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
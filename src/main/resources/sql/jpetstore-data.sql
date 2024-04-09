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
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5, quantity)
VALUES ('EST-1', @Labrador_Retriever_id, '16.50', '10.00', '1', 'P', 'Large', null, null, null, null, 3);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-10', @Golden_Retriever_id, '18.50', '12.00', '1', 'P', 'Spotted Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-11', @Tiger_Shark_id, '18.50', '12.00', '1', 'P', 'Venomless', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-12', @Tiger_Shark_id, '18.50', '12.00', '1', 'P', 'Rattleless', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-13', @Rattlesnake_id, '18.50', '12.00', '1', 'P', 'Green Adult', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-14', @Chihuahua_id, '58.50', '12.00', '1', 'P', 'Tailless', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-15', @Chihuahua_id, '23.50', '12.00', '1', 'P', 'With tail', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-16', @Bulldog_id, '93.50', '12.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-17', @Bulldog_id, '93.50', '12.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-18', @Amazon_Parrot_id, '193.50', '92.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-19', @Angelfish_id, '15.50', '2.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-2', @Persian_id, '16.50', '10.00', '1', 'P', 'Small', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-20', @Manx_id, '5.50', '2.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-21', @Manx_id, '5.29', '1.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-22', @Koi_id, '135.50', '100.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-23', @Koi_id, '145.49', '100.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-24', @Koi_id, '255.50', '92.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-25', @Koi_id, '325.29', '90.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-26', @Finch_id, '125.50', '92.00', '1', 'P', 'Adult Male', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-27', @Finch_id, '155.29', '90.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-28', @Iguana_id, '155.29', '90.00', '1', 'P', 'Adult Female', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-3', @Poodle_id, '18.50', '12.00', '1', 'P', 'Toothless', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-4', @Labrador_Retriever_id, '18.50', '12.00', '1', 'P', 'Spotted', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-5', @Labrador_Retriever_id, '18.50', '12.00', '1', 'P', 'Spotless', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-6', @Dalmation_id, '18.50', '12.00', '1', 'P', 'Male Adult', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-7', @Dalmation_id, '18.50', '12.00', '1', 'P', 'Female Puppy', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
                   attribute4, attribute5)
VALUES ('EST-8', @Goldfish_id, '18.50', '12.00', '1', 'P', 'Male Puppy', null, null, null, null);
INSERT INTO `item`(item_id, pet_breed_id, list_price, unit_cost, supplier_id, status, attribute1, attribute2, attribute3,
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
VALUES (@Persian_id, @FISH, 'Angelfish', 'Salt Water fish from Australia', '/images/fish1.jpg');
INSERT INTO pet_breed (id, category_id, name, description, image_path)
VALUES (@Poodle_id, @FISH, 'Tiger Shark', 'Salt Water fish from Australia', '/images.fish4.gif');
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
-- H2 Database Compatible Script from MySQL Dump

-- No need for these in H2
-- SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT;
-- SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS;
-- SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION;
-- SET NAMES utf8mb4;
-- SET @OLD_TIME_ZONE=@@TIME_ZONE;
-- SET TIME_ZONE='+00:00';
-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
-- SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(50) NOT NULL,
                            PRIMARY KEY (`id`),
                            CONSTRAINT `UK46ccwnsi9409t36lurvtyljak` UNIQUE (`name`)
);

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES (1,'Appetizer'),(3,'Desserts'),(4,'Drinks'),(2,'Main Course');
-- Manually set sequence value if needed after inserts, e.g., for H2 2.x:
-- ALTER TABLE `category` ALTER COLUMN `id` RESTART WITH 5;


--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
                            `customer_id` bigint NOT NULL AUTO_INCREMENT,
                            `address` varchar(255) DEFAULT NULL,
                            `date_of_birth` varchar(255) DEFAULT NULL, -- Kept as varchar as in original, consider changing to DATE type
                            `email` varchar(255) DEFAULT NULL,
                            `first_name` varchar(255) DEFAULT NULL,
                            `last_name` varchar(255) DEFAULT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `phone` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`customer_id`)
);

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `address`, `date_of_birth`, `email`, `first_name`, `last_name`, `password`, `phone`) VALUES
                                                                                                                                (1,'HCM','2024-12-12','khoa@gmail.com','Võ Minh ','Khoa','$2a$10$yw8e96VAM2tL3wN.NwaGAeDmBOpz6lniz7SyhB4uddSq3D7YtFw4G','0978586850'),
                                                                                                                                (2,'a','2004-11-11','admin@gmail.com','Nhân','Nguyễn Văn','$2a$10$462Po/CkyyoB1Na.owLii.LEp2CbuOrTEnaTkM4hn3lSm4uaFB.m.','0365235789'),
                                                                                                                                (3,'HCm','2004-11-11','noinfo@default.com','noinfo','noinfo','$2a$10$BMb.cRz09gdECqbyfJ5Te.gEZk2DDFJxO5/cfT08JtjzCmYwBXEmq','+84386056830');
-- ALTER TABLE `customer` ALTER COLUMN `customer_id` RESTART WITH 5;

--
-- Table structure for table `dining_table`
--

DROP TABLE IF EXISTS `dining_table`;
CREATE TABLE `dining_table` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `capacity` int NOT NULL,
                                `qr_code_url` varchar(255) NOT NULL,
                                `status` varchar(20) NOT NULL, -- Changed ENUM to VARCHAR
                                `table_number` int NOT NULL,
                                PRIMARY KEY (`id`),
                                CONSTRAINT `UKsn1m34gmwngjikke0cfirfc03` UNIQUE (`table_number`)
);

-- Add CHECK constraint for ENUM simulation (optional but recommended)
ALTER TABLE `dining_table` ADD CONSTRAINT `chk_dining_table_status` CHECK (`status` IN ('AVAILABLE','OCCUPIED','RESERVED','UNAVAILABLE'));

--
-- Dumping data for table `dining_table`
--

INSERT INTO `dining_table` (`id`, `capacity`, `qr_code_url`, `status`, `table_number`) VALUES
                                                                                           (1,1,'http://res.cloudinary.com/driu62xj1/image/upload/v1733404528/eg0plklxuioon480ro6l.png','AVAILABLE',1),
                                                                                           (2,4,'http://res.cloudinary.com/driu62xj1/image/upload/v1733479312/ewhspgadfxafalrmx3iu.png','AVAILABLE',2),
                                                                                           (3,4,'http://res.cloudinary.com/driu62xj1/image/upload/v1733486864/kd6viygsausjzcio1xcv.png','AVAILABLE',3),
                                                                                           (4,4,'http://res.cloudinary.com/driu62xj1/image/upload/v1733486878/iv8sirilp0ukj5oxnunp.png','AVAILABLE',4),
                                                                                           (5,5,'http://res.cloudinary.com/driu62xj1/image/upload/v1733486888/m3yv6jrdqqr5ukmlltv6.png','AVAILABLE',5),
                                                                                           (6,6,'http://res.cloudinary.com/driu62xj1/image/upload/v1733486894/jdkrv9xublnh0zwy0o4a.png','AVAILABLE',6);
-- ALTER TABLE `dining_table` ALTER COLUMN `id` RESTART WITH 7;


--
-- Table structure for table `customer_order`
--

DROP TABLE IF EXISTS `customer_order`;
CREATE TABLE `customer_order` (
                                  `id` varchar(255) NOT NULL,
                                  `comment` varchar(255) DEFAULT NULL,
                                  `order_date` TIMESTAMP DEFAULT NULL, -- Changed datetime(6) to TIMESTAMP
                                  `order_method` varchar(10) DEFAULT NULL, -- Changed ENUM to VARCHAR
                                  `order_status` varchar(20) DEFAULT NULL, -- Changed ENUM to VARCHAR
                                  `rating` int NOT NULL,
                                  `total_amount` double DEFAULT NULL,
                                  `customer_id` bigint DEFAULT NULL,
                                  `dining_table_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`)
    -- Foreign key constraints added after table creation
);

-- Add CHECK constraints for ENUM simulation (optional but recommended)
ALTER TABLE `customer_order` ADD CONSTRAINT `chk_order_method` CHECK (`order_method` IN ('CASH','MOMO','VNPAY'));
ALTER TABLE `customer_order` ADD CONSTRAINT `chk_order_status` CHECK (`order_status` IN ('CANCELED','COMPLETED','PAID','PENDING','UNPAID'));

-- Create Indexes (previously KEY)
CREATE INDEX `idx_order_customer` ON `customer_order` (`customer_id`);
CREATE INDEX `idx_order_dining_table` ON `customer_order` (`dining_table_id`);

-- Add Foreign Keys
ALTER TABLE `customer_order` ADD CONSTRAINT `FKf9abd30bhiqvugayxlpq8ryq9` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);
ALTER TABLE `customer_order` ADD CONSTRAINT `FKhkhw19ph0itlyfiy28fghwuuu` FOREIGN KEY (`dining_table_id`) REFERENCES `dining_table` (`id`);

--
-- Dumping data for table `customer_order`
--

INSERT INTO `customer_order` (`id`, `comment`, `order_date`, `order_method`, `order_status`, `rating`, `total_amount`, `customer_id`, `dining_table_id`) VALUES
                                                                                                                                                             ('2024120612279',NULL,'2024-12-06 16:34:16.260491','CASH','COMPLETED',0,125000,1,1),
                                                                                                                                                             ('2024120630567',NULL,'2024-11-06 15:28:23.202989','CASH','COMPLETED',0,85000,3,5),
                                                                                                                                                             ('2024120632366',NULL,'2024-11-02 15:27:16.416583','CASH','COMPLETED',0,250000,3,3),
                                                                                                                                                             ('2024120632621',NULL,'2024-11-03 15:26:37.866482','CASH','COMPLETED',0,95000,3,6),
                                                                                                                                                             ('2024120632710',NULL,'2024-11-11 15:28:09.932687','CASH','COMPLETED',0,80000,3,5),
                                                                                                                                                             ('2024120633128',NULL,'2024-11-05 15:28:45.947093','CASH','COMPLETED',0,95000,3,5),
                                                                                                                                                             ('2024120633522',NULL,'2024-11-12 15:26:14.738756','CASH','COMPLETED',0,80000,3,1),
                                                                                                                                                             ('2024120634926',NULL,'2024-12-06 15:27:34.588129','CASH','COMPLETED',0,80000,3,2),
                                                                                                                                                             ('2024120636275',NULL,'2024-12-06 15:27:44.890964','CASH','COMPLETED',0,50000,3,4),
                                                                                                                                                             ('2024120637035',NULL,'2024-12-06 15:25:57.189998','CASH','COMPLETED',0,190000,3,1),
                                                                                                                                                             ('2024120637144',NULL,'2024-12-06 15:26:48.817867','CASH','COMPLETED',0,300000,3,5),
                                                                                                                                                             ('2024120638754',NULL,'2024-12-06 15:28:34.893369','CASH','COMPLETED',0,95000,3,3),
                                                                                                                                                             ('2024120638869',NULL,'2024-12-06 15:27:57.527910','CASH','COMPLETED',0,75000,3,5),
                                                                                                                                                             ('2024120639054',NULL,'2024-12-06 15:27:04.367530','CASH','COMPLETED',0,440000,3,4),
                                                                                                                                                             ('2024120639149','rất ngon','2024-12-06 16:47:59.651850','CASH','COMPLETED',5,80000,3,4),
                                                                                                                                                             ('2024120639872',NULL,'2024-12-06 15:26:26.762658','CASH','COMPLETED',0,30000,3,6);


--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
CREATE TABLE `discount` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `code` varchar(255) NOT NULL,
                            `end_date` date NOT NULL,
                            `is_active` BOOLEAN NOT NULL, -- Changed bit(1) to BOOLEAN
                            `max_amount` decimal(38,2) NOT NULL,
                            `percentage` int NOT NULL,
                            `start_date` date NOT NULL,
                            `times_used` int NOT NULL,
                            `usage_limit` int NOT NULL,
                            PRIMARY KEY (`id`),
                            CONSTRAINT `UKi14w897ofrtv43vbx44rtv01u` UNIQUE (`code`)
);

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`id`, `code`, `end_date`, `is_active`, `max_amount`, `percentage`, `start_date`, `times_used`, `usage_limit`) VALUES
                                                                                                                                          (1,'120','2024-12-21',FALSE,20000.00,20,'2024-12-05',0,2), -- Changed _binary ' ' to FALSE
                                                                                                                                          (2,'121','2024-12-28',FALSE,300000.00,30,'2024-12-02',0,10), -- Changed _binary ' ' to FALSE
                                                                                                                                          (3,'1','2024-12-20',FALSE,300000.00,30,'2024-12-04',0,10); -- Changed _binary ' ' to FALSE
-- ALTER TABLE `discount` ALTER COLUMN `id` RESTART WITH 4;


--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
                        `dish_id` bigint NOT NULL AUTO_INCREMENT,
                        `cost` double NOT NULL,
                        `description` varchar(500) DEFAULT NULL,
                        `image` varchar(255) DEFAULT NULL,
                        `name` varchar(100) NOT NULL,
                        `price` double NOT NULL,
                        `category_id` bigint NOT NULL,
                        PRIMARY KEY (`dish_id`),
                        CONSTRAINT `UKr7g2l08wdh3uv3gvurli4s1bx` UNIQUE (`name`)
    -- Foreign key constraint added after table creation
);

-- Create Index (previously KEY)
CREATE INDEX `idx_dish_category` ON `dish` (`category_id`);

-- Add Foreign Key
ALTER TABLE `dish` ADD CONSTRAINT `FK3h7qfevodvyk24ss68mwu8ap6` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Dumping data for table `dish`
--

INSERT INTO `dish` (`dish_id`, `cost`, `description`, `image`, `name`, `price`, `category_id`) VALUES
                                                                                                   (1,34300,'Pho','http://res.cloudinary.com/driu62xj1/image/upload/v1733476290/zzazs1e0ceht1eelvajs.jpg','Pho',30000,2),
                                                                                                   (2,29150,' Bánh mì bơ tỏi ','http://res.cloudinary.com/driu62xj1/image/upload/v1733476397/fobmbqvuynx0ghenh05o.jpg','Bánh mì bơ tỏi',50000,1),
                                                                                                   (3,21790,'Gỏi mực','http://res.cloudinary.com/driu62xj1/image/upload/v1733476413/bpyt4rizmetzydlh7dln.jpg','Gỏi mực',45000,1),
                                                                                                   (4,50825,'cá tươi','http://res.cloudinary.com/driu62xj1/image/upload/v1733476488/wp7g705usv5uhrtzsza2.jpg','Cá hồi áp chảo sốt tiêu xanh',250000,1),
                                                                                                   (5,31500,'Cocktail','http://res.cloudinary.com/driu62xj1/image/upload/v1733476511/oxmkl880urtyvizpeam2.jpg','Cocktail',100000,4),
                                                                                                   (6,5000,'Nước suối','http://res.cloudinary.com/driu62xj1/image/upload/v1733476589/pyprxuapgoad0uuxp4dw.jpg','Nước suối',20000,4),
                                                                                                   (7,30000,'Coffee','http://res.cloudinary.com/driu62xj1/image/upload/v1733476654/zzgnzbl0xfdchty5khi8.jpg','Coffee',85000,4),
                                                                                                   (9,75005,'Bánh xèo','http://res.cloudinary.com/driu62xj1/image/upload/v1733487148/okrcmrnwhvll3u7lms6x.jpg','Bánh xèo',200000,2),
                                                                                                   (10,56024,'Bún riêu cua','http://res.cloudinary.com/driu62xj1/image/upload/v1733487170/m1zow7cqwhqzilcgry8b.jpg','Bún riêu cua',250000,2),
                                                                                                   (11,67935,'Gỏi cuốn','http://res.cloudinary.com/driu62xj1/image/upload/v1733487193/bictffvn7gqnqp9caq5v.jpg','Gỏi cuốn',190000,2),
                                                                                                   (12,98653,'Cơm tấm','http://res.cloudinary.com/driu62xj1/image/upload/v1733487286/huxvzobcxr143kplexak.jpg','Cơm tấm',300000,2);
-- ALTER TABLE `dish` ALTER COLUMN `dish_id` RESTART WITH 14;


--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `email` varchar(255) DEFAULT NULL,
                            `image` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `password` varchar(255) DEFAULT NULL,
                            `phone` varchar(255) DEFAULT NULL,
                            `position` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
);

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `email`, `image`, `name`, `password`, `phone`, `position`) VALUES
                                                                                             (1,'admin@gmail.com',NULL,'admin','$2a$10$zwyzjIIqXNE2LsR2pGviWOYaJXMWaAGrBH9UFSLK3TilyL6.aDMJa',NULL,'ADMIN'),
                                                                                             (5,'staff@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733493990/nadmnzctjsho58t3kqkk.jpg','Nguyễn Văn A','$2a$10$gx/1edUh3JZDNMrcCALfYeQr7BmyFCkFwry6rYUo0W3sEpXG1BsIG','0123456789','STAFF'),
                                                                                             (6,'chef@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733494041/ijhd5dyeavviswbdl2e3.jpg','Chef Num 1','$2a$10$LtXGNfYYEtV4cqTwSlxwfuH7eHQe0N2Sqc/.mQ7CrXdR.6/UHvuJC','0123456789','CHEF'),
                                                                                             (7,'staff1@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733494070/orjxuolsscgxeeyzn5v6.webp','Nguyễn Văn B','$2a$10$M0WL/Vnrn8v480GxOPb2I.8EdB1LgV6VLcjoygdYHxaSHNq/Hmu3a','0123456789','STAFF'),
                                                                                             (8,'staff2@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733494108/gsdatorga4l4a56cuvsm.jpg','Nguyễn Văn C','$2a$10$2rlDkrokJLUaK5/YaJsmT..NuP87TcFu05Na7rdK9p08w68FTkE1y','0123456789','STAFF'),
                                                                                             (9,'staff3@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733494135/klkr6ehitp1anfaezvqp.jpg','Nguyễn Văn D','$2a$10$G7MKGUHCyu7MFqV3MZRP.eGxC4fIxQv5ld4pi8lUajV08fvyZ8TKm','0123456789','STAFF'),
                                                                                             (10,'staff4@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733494179/t9kdv1p5uz0nfyw3pmrk.jpg','Nguyễn Văn E','$2a$10$nKLqy0tLpFdMw5nwLRckOOc/Gvw5uWUeLrfa/.QUOrHQWHARj5CVO','0123456789','STAFF'),
                                                                                             (11,'chef1@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733494208/ytbz4czgzdhweswlaak0.jpg','Chef Num 2','$2a$10$AThlgz1wMNIZax3g9f.br.N7LofvFhmZnpYSAYk0Dy7dIVpgx2/lm','0123456789','CHEF'),
                                                                                             (14,'staff5@gmail.com','http://res.cloudinary.com/driu62xj1/image/upload/v1733506597/f9bls6wmgzkxk7eeotxt.jpg','Nguyễn Văn F','$2a$10$SYemgs5bCMjjPdx7CVHehuTngIzT.bBcb/Tk.0JdwSz8oQQir/Qrm','0123456789','STAFF');
-- ALTER TABLE `employee` ALTER COLUMN `id` RESTART WITH 15;


--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
                            `supplier_id` bigint NOT NULL AUTO_INCREMENT,
                            `address` varchar(255) DEFAULT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `notes` varchar(255) DEFAULT NULL,
                            `phone` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`supplier_id`)
);

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplier_id`, `address`, `email`, `name`, `notes`, `phone`) VALUES
                                                                                         (1,'456 Main St, Hanoi','foodco@example.com','Food Co.',NULL,'0912345678'),
                                                                                         (2,'789 Oak St, Hanoi','beefsuppliers@example.com','Beef Suppliers',NULL,'0912345679'),
                                                                                         (3,'101 Pine St, Hanoi','sweettreats@example.com','Sweet Treats',NULL,'0912345680'),
                                                                                         (4,'202 Cedar Rd, Hanoi','veggieworld@example.com','Veggie World',NULL,'0912345681'),
                                                                                         (5,'303 Elm St, Hanoi','fruitfarm@example.com','Fruit Farm',NULL,'0912345682');
-- ALTER TABLE `supplier` ALTER COLUMN `supplier_id` RESTART WITH 7;


--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
                             `inventory_id` bigint NOT NULL AUTO_INCREMENT,
                             `description` varchar(255) DEFAULT NULL,
                             `item_name` varchar(255) DEFAULT NULL,
                             `quantity` int NOT NULL,
                             `unit` varchar(255) DEFAULT NULL,
                             `unit_price` double DEFAULT NULL,
                             `supplier_id` bigint NOT NULL,
                             PRIMARY KEY (`inventory_id`)
    -- Foreign key constraint added after table creation
);

-- Create Index (previously KEY)
CREATE INDEX `idx_inventory_supplier` ON `inventory` (`supplier_id`);

-- Add Foreign Key
ALTER TABLE `inventory` ADD CONSTRAINT `FKe0810rp6mmsbj1f46yhc4h7vb` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`);

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`inventory_id`, `description`, `item_name`, `quantity`, `unit`, `unit_price`, `supplier_id`) VALUES
                                                                                                                          (1,'Táo tươi, nhập khẩu từ Đà Lạt','Táo',49980,'g',70,1),
                                                                                                                          (2,'Thịt bò phi lê, thích hợp làm bò bít tết','Thịt bò',29920,'g',200,2),
                                                                                                                          (3,'Sô cô la nguyên chất, dùng làm bánh ngọt','Sô cô la',99943,'g',350,3),
                                                                                                                          (4,'Cà rốt tươi, được trồng tại Gia Lai','Cà rốt',59940,'g',40,4),
                                                                                                                          (5,'Xà lách sạch, thu hoạch tại Đà Lạt','Xà lách',39943,'g',40,5),
                                                                                                                          (6,'Hành lá tươi, gia vị cần thiết trong món ăn Việt','Hành lá',24940,'g',30,1),
                                                                                                                          (7,'Gạo thơm hảo hạng, thích hợp nấu cơm và xôi','Gạo',499943,'g',15,2),
                                                                                                                          (8,'Nước mắm cốt nhĩ, gia vị đặc trưng của Việt Nam','Nước mắm',99940,'ml',20,3),
                                                                                                                          (9,'Bột mì đa dụng, nguyên liệu chính cho bánh mì','Bột mì',199940,'g',20,4),
                                                                                                                          (10,'Đường cát trắng, dùng trong các món tráng miệng','Đường',300000,'g',10,5),
                                                                                                                          (11,'Muối biển sạch, gia vị phổ biến trong mọi món ăn','Muối',100000,'g',500,1),
                                                                                                                          (12,'Cá hồi tươi nhập khẩu, thích hợp làm sushi','Cá hồi',19940,'g',400,2),
                                                                                                                          (13,'Tôm sú tươi, sử dụng trong các món chiên và hấp','Tôm sú',30000,'g',280,3),
                                                                                                                          (14,'Gừng tươi, gia vị không thể thiếu trong các món Việt','Gừng',15000,'g',5,4),
                                                                                                                          (15,'Ớt tươi đỏ, nguyên liệu cho các món cay','Ớt',25000,'g',60,5),
                                                                                                                          (16,'Ớt bột nhập khẩu từ Hàn Quốc,','Gia vị ớt bột',30000,'gói',10000,2);
-- ALTER TABLE `inventory` ALTER COLUMN `inventory_id` RESTART WITH 18;


--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `cost` double NOT NULL,
                              `order_status` varchar(20) DEFAULT NULL, -- Changed ENUM to VARCHAR
                              `price` double NOT NULL,
                              `quantity` int NOT NULL,
                              `dish_id` bigint DEFAULT NULL,
                              `order_id` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
    -- Foreign key constraints added after table creation
);

-- Add CHECK constraint for ENUM simulation (optional but recommended)
ALTER TABLE `order_item` ADD CONSTRAINT `chk_order_item_status` CHECK (`order_status` IN ('CANCELED','COMPLETED','PAID','PENDING','UNPAID'));

-- Create Indexes (previously KEY)
CREATE INDEX `idx_order_item_dish` ON `order_item` (`dish_id`);
CREATE INDEX `idx_order_item_order` ON `order_item` (`order_id`);

-- Add Foreign Keys
ALTER TABLE `order_item` ADD CONSTRAINT `FKgv4bnmo7cbib2nh0b2rw9yvir` FOREIGN KEY (`order_id`) REFERENCES `customer_order` (`id`);
ALTER TABLE `order_item` ADD CONSTRAINT `FKs7aplknkrukmckr29wijlvcy1` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`);

--
-- Dumping data for table `order_item`
--

INSERT INTO `order_item` (`id`, `cost`, `order_status`, `price`, `quantity`, `dish_id`, `order_id`) VALUES
                                                                                                        (16,29150,'COMPLETED',50000,2,2,'2024120637035'),(17,21790,'COMPLETED',45000,2,3,'2024120637035'),(18,56024,'COMPLETED',250000,1,10,'2024120637035'),
                                                                                                        (19,34300,'COMPLETED',30000,1,1,'2024120633522'),(20,29150,'COMPLETED',50000,1,2,'2024120633522'),(21,56024,'COMPLETED',250000,1,10,'2024120633522'),
                                                                                                        (22,34300,'COMPLETED',30000,1,1,'2024120639872'),(23,98653,'COMPLETED',300000,1,12,'2024120639872'),
                                                                                                        (24,29150,'COMPLETED',50000,1,2,'2024120632621'),(25,21790,'COMPLETED',45000,1,3,'2024120632621'),(26,98653,'COMPLETED',300000,1,12,'2024120632621'),
                                                                                                        (27,98653,'COMPLETED',300000,1,12,'2024120637144'),(28,29150,'COMPLETED',50000,1,2,'2024120637144'),
                                                                                                        (29,67935,'COMPLETED',190000,1,11,'2024120639054'),(30,56024,'PENDING',250000,1,10,'2024120639054'),(31,75005,'PENDING',200000,1,9,'2024120639054'),
                                                                                                        (32,29150,'PENDING',50000,5,2,'2024120632366'),
                                                                                                        (33,34300,'PENDING',30000,1,1,'2024120634926'),(34,29150,'PENDING',50000,1,2,'2024120634926'),(35,21790,'PENDING',45000,1,3,'2024120634926'),
                                                                                                        (36,29150,'PENDING',50000,1,2,'2024120636275'),(37,5000,'PENDING',20000,1,6,'2024120636275'),
                                                                                                        (38,21790,'PENDING',45000,1,3,'2024120638869'),(39,34300,'PENDING',30000,1,1,'2024120638869'),(40,31500,'PENDING',100000,1,5,'2024120638869'),
                                                                                                        (41,34300,'PENDING',30000,1,1,'2024120632710'),(42,29150,'PENDING',50000,1,2,'2024120632710'),(43,31500,'PENDING',100000,1,5,'2024120632710'),
                                                                                                        (44,30000,'PENDING',85000,1,7,'2024120630567'),(45,75005,'PENDING',200000,1,9,'2024120630567'),
                                                                                                        (46,29150,'PENDING',50000,1,2,'2024120638754'),(47,21790,'PENDING',45000,1,3,'2024120638754'),(48,56024,'PENDING',250000,1,10,'2024120638754'),
                                                                                                        (49,29150,'PENDING',50000,1,2,'2024120633128'),(50,21790,'PENDING',45000,1,3,'2024120633128'),(51,5000,'PENDING',20000,1,6,'2024120633128'),
                                                                                                        (52,34300,'PENDING',30000,1,1,'2024120612279'),(53,29150,'PENDING',50000,1,2,'2024120612279'),(54,21790,'PENDING',45000,1,3,'2024120612279'),(55,5000,'PENDING',20000,1,6,'2024120612279'),
                                                                                                        (56,34300,'PENDING',30000,1,1,'2024120639149'),(57,29150,'PENDING',50000,1,2,'2024120639149'),(58,21790,'PENDING',45000,1,3,'2024120639149');
-- ALTER TABLE `order_item` ALTER COLUMN `id` RESTART WITH 59;


--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
CREATE TABLE `otp` (
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `expiry_time` TIMESTAMP DEFAULT NULL, -- Changed datetime(6) to TIMESTAMP
                       `is_used` BOOLEAN NOT NULL, -- Changed bit(1) to BOOLEAN
                       `otp_code` varchar(255) DEFAULT NULL,
                       `phone_number` varchar(255) DEFAULT NULL,
                       PRIMARY KEY (`id`)
);

--
-- Dumping data for table `otp`
--

INSERT INTO `otp` (`id`, `expiry_time`, `is_used`, `otp_code`, `phone_number`) VALUES
                                                                                   (1,'2024-12-05 14:11:36.324807',FALSE,'438957','0978586850'), -- Changed _binary '\0' to FALSE
                                                                                   (2,'2024-12-05 14:12:32.092353',TRUE,'836797','+84386056830'), -- Changed _binary ' ' to TRUE (assuming space meant TRUE/1, adjust if needed)
                                                                                   (3,'2024-12-06 16:27:53.073011',FALSE,'587649','+84386056830'), -- Changed _binary '\0' to FALSE
                                                                                   (4,'2024-12-06 16:28:41.958252',TRUE,'920391','+84386056830'); -- Changed _binary ' ' to TRUE
-- ALTER TABLE `otp` ALTER COLUMN `id` RESTART WITH 5;


--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
CREATE TABLE `recipe` (
                          `recipe_id` bigint NOT NULL AUTO_INCREMENT,
                          `quantity_required` int NOT NULL,
                          `dish_id` bigint NOT NULL,
                          `inventory_id` bigint NOT NULL,
                          PRIMARY KEY (`recipe_id`)
    -- Foreign key constraints added after table creation
);

-- Create Indexes (previously KEY)
CREATE INDEX `idx_recipe_dish` ON `recipe` (`dish_id`);
CREATE INDEX `idx_recipe_inventory` ON `recipe` (`inventory_id`);

-- Add Foreign Keys
ALTER TABLE `recipe` ADD CONSTRAINT `FK6gd6sjjumfpbk8w03lv6ohwb5` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`);
ALTER TABLE `recipe` ADD CONSTRAINT `FKqr8ypyincg2gje81jnt3bqv8o` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`inventory_id`);

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`recipe_id`, `quantity_required`, `dish_id`, `inventory_id`) VALUES
                                                                                       (207,200,5,1),(208,50,5,3),
                                                                                       (226,99,1,2),(227,15,1,4),(228,30,1,5),(229,50,1,7),(230,10,1,8),(231,190,1,9),(232,10,1,10),(233,10,1,11),(234,10,1,13),(235,10,1,14),
                                                                                       (241,50,2,1),(242,80,2,2),(243,10,2,3),(244,10,2,4),(245,10,2,5),(246,10,2,9),(247,10,2,10),(248,10,2,11),(249,10,2,14),
                                                                                       (285,99,3,4),(286,19,3,5),(287,9,3,8),(288,9,3,10),(289,20,3,11),(290,10,3,12),(291,10,3,13),
                                                                                       (311,49,4,2),(312,20,4,4),(313,30,4,5),(314,10,4,6),(315,13,4,7),(316,94,4,8),(317,20,4,12),(318,100,4,13),(319,10,4,14),(320,10,4,15);
-- ALTER TABLE `recipe` ALTER COLUMN `recipe_id` RESTART WITH 321;


--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `customer_name` varchar(255) DEFAULT NULL,
                               `date_to_come` date DEFAULT NULL,
                               `email` varchar(255) DEFAULT NULL,
                               `note` varchar(255) DEFAULT NULL,
                               `number_of_persons` int DEFAULT NULL,
                               `status` varchar(20) DEFAULT NULL, -- Changed ENUM to VARCHAR
                               `time_to_come` TIME DEFAULT NULL, -- Changed time(6) to TIME
                               `table_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`)
    -- Foreign key constraint added after table creation
);

-- Add CHECK constraint for ENUM simulation (optional but recommended)
ALTER TABLE `reservation` ADD CONSTRAINT `chk_reservation_status` CHECK (`status` IN ('CANCELLED','CONFIRMED','FINISHED','PENDING'));

-- Create Index (previously KEY)
CREATE INDEX `idx_reservation_table` ON `reservation` (`table_id`);

-- Add Foreign Key
ALTER TABLE `reservation` ADD CONSTRAINT `FKarab6o324oagupooctuexfwsi` FOREIGN KEY (`table_id`) REFERENCES `dining_table` (`id`);

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id`, `customer_name`, `date_to_come`, `email`, `note`, `number_of_persons`, `status`, `time_to_come`, `table_id`) VALUES
                                                                                                                                                  (1,'loc','2024-12-12','lantubuon08@gmail.com','ko ăn cay',4,'CANCELLED','09:31:00',2),
                                                                                                                                                  (2,'Võ Minh Khoa','2024-12-07','v.minhkhoa123456@gmail.com','Web',2,'CONFIRMED','20:15:00',6),
                                                                                                                                                  (3,'loc','2024-12-07','loc@gmail.com','ko có',4,'CONFIRMED','20:16:00',5),
                                                                                                                                                  (4,'loc1','2024-12-21','loc@gmail.com','',3,'CONFIRMED','20:19:00',1),
                                                                                                                                                  (5,'loc','2024-12-07','loc@gmail.com','',3,'CANCELLED','23:39:00',2);
-- ALTER TABLE `reservation` ALTER COLUMN `id` RESTART WITH 6;


--
-- Table structure for table `salary`
--

DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `bonus` double DEFAULT NULL,
                          `hourly_rate` double DEFAULT NULL,
                          `pay_date` date DEFAULT NULL,
                          `status` varchar(10) DEFAULT NULL, -- Changed ENUM to VARCHAR
                          `total_hours_worked` double DEFAULT NULL,
                          `total_salary` double DEFAULT NULL,
                          `employee_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`)
    -- Foreign key constraint added after table creation
);

-- Add CHECK constraint for ENUM simulation (optional but recommended)
ALTER TABLE `salary` ADD CONSTRAINT `chk_salary_status` CHECK (`status` IN ('PAID','PENDING'));

-- Create Index (previously KEY)
CREATE INDEX `idx_salary_employee` ON `salary` (`employee_id`);

-- Add Foreign Key
ALTER TABLE `salary` ADD CONSTRAINT `FKnlnv3jbyvbiu8ci59r3btlk00` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Dumping data for table `salary`
-- (No data provided in the original dump)
-- ALTER TABLE `salary` ALTER COLUMN `id` RESTART WITH 31; -- Assuming 31 based on original dump info


--
-- Table structure for table `shift`
--

DROP TABLE IF EXISTS `shift`;
CREATE TABLE `shift` (
                         `shift_id` bigint NOT NULL AUTO_INCREMENT,
                         `available` int DEFAULT NULL,
                         `end_time` TIME DEFAULT NULL, -- Changed time(6) to TIME
                         `is_active` BOOLEAN NOT NULL, -- Changed bit(1) to BOOLEAN
                         `max_registration` int NOT NULL,
                         `shift_name` varchar(255) DEFAULT NULL,
                         `shift_type` varchar(10) DEFAULT NULL, -- Changed ENUM to VARCHAR
                         `start_time` TIME DEFAULT NULL, -- Changed time(6) to TIME
                         `working_date` date DEFAULT NULL,
                         `employee_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`shift_id`)
    -- Foreign key constraint added after table creation
);

-- Add CHECK constraint for ENUM simulation (optional but recommended)
ALTER TABLE `shift` ADD CONSTRAINT `chk_shift_type` CHECK (`shift_type` IN ('FIXED','OPEN','REGULAR'));

-- Create Index (previously KEY)
CREATE INDEX `idx_shift_employee` ON `shift` (`employee_id`);

-- Add Foreign Key
ALTER TABLE `shift` ADD CONSTRAINT `FKg9ycreft1sv2jjvkno3dn3fqy` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Dumping data for table `shift`
--

INSERT INTO `shift` (`shift_id`, `available`, `end_time`, `is_active`, `max_registration`, `shift_name`, `shift_type`, `start_time`, `working_date`, `employee_id`) VALUES
                                                                                                                                                                        (1,0,'11:00:00',FALSE,999,'Morning','REGULAR','07:00:00','2024-12-06',1), -- Changed _binary '\0' to FALSE
                                                                                                                                                                        (2,0,'15:00:00',FALSE,999,'Lunch','REGULAR','11:00:00','2024-12-06',1), -- Changed _binary '\0' to FALSE
                                                                                                                                                                        (3,9,'21:00:00',FALSE,12,'Open Shift 11/12/2024','OPEN','17:00:00','2024-12-10',1), -- Changed _binary '\0' to FALSE
                                                                                                                                                                        (4,0,'21:00:00',FALSE,999,'Evening','REGULAR','17:00:00','2024-12-06',1), -- Changed _binary '\0' to FALSE
                                                                                                                                                                        (5,8,'11:00:00',FALSE,9,'Open Shift 11/12/2024','OPEN','09:00:00','2024-12-11',1), -- Changed _binary '\0' to FALSE
                                                                                                                                                                        (6,999,'09:00:00',FALSE,999,'Fixed 1','FIXED','07:00:00','2024-12-06',1); -- Changed _binary '\0' to FALSE
-- ALTER TABLE `shift` ALTER COLUMN `shift_id` RESTART WITH 7;


--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
                            `schedule_id` bigint NOT NULL AUTO_INCREMENT,
                            `end_time` TIME DEFAULT NULL, -- Changed time(6) to TIME
                            `start_time` TIME DEFAULT NULL, -- Changed time(6) to TIME
                            `status` varchar(255) DEFAULT NULL,
                            `working_date` date DEFAULT NULL,
                            `employee_id` bigint DEFAULT NULL,
                            `shift_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`schedule_id`)
    -- Foreign key constraints added after table creation
);

-- Create Indexes (previously KEY)
CREATE INDEX `idx_schedule_employee` ON `schedule` (`employee_id`);
CREATE INDEX `idx_schedule_shift` ON `schedule` (`shift_id`);

-- Add Foreign Keys
ALTER TABLE `schedule` ADD CONSTRAINT `FKajqm53soraqn6bpdsfvxtbv52` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`shift_id`);
ALTER TABLE `schedule` ADD CONSTRAINT `FKsodsj8c282vagj766ll9g8tdc` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`schedule_id`, `end_time`, `start_time`, `status`, `working_date`, `employee_id`, `shift_id`) VALUES
                                                                                                                          (482,'11:00:00','07:00:00','DRAFF','2024-12-16',5,1),(483,'21:00:00','17:00:00','DRAFF','2024-12-16',5,4),
                                                                                                                               (562,'11:00:00','07:00:00','DRAFF','2024-12-21',11,1),(563,'21:00:00','17:00:00','DRAFF','2024-12-21',11,4),
                                                                                                                          (564,'11:00:00','07:00:00','DRAFF','2024-12-22',11,1),(565,'21:00:00','17:00:00','DRAFF','2024-12-22',11,4);
-- ALTER TABLE `schedule` ALTER COLUMN `schedule_id` RESTART WITH 566;


-- Final cleanup commands not needed in H2
-- SET TIME_ZONE=@OLD_TIME_ZONE;
-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT;
-- SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS;
-- SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION;
-- SET SQL_NOTES=@OLD_SQL_NOTES;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: real_estate_agency
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `property_id` int(11) NOT NULL AUTO_INCREMENT,
  `property_type_id` int(11) NOT NULL,
  `owner` int(11) NOT NULL,
  `city` varchar(128) NOT NULL,
  `street` varchar(128) NOT NULL,
  `house_number` int(11) NOT NULL,
  `block_number` int(11) DEFAULT NULL,
  `flat_number` int(11) DEFAULT NULL,
  `room_count` int(11) DEFAULT NULL,
  `area` int(11) NOT NULL,
  `distance_to_subway` int(11) DEFAULT NULL,
  `distance_to_transport_stop` int(11) DEFAULT NULL,
  `has_furniture` tinyint(1) NOT NULL,
  `has_internet` tinyint(1) NOT NULL,
  `has_phone` tinyint(1) NOT NULL,
  `has_tv` tinyint(1) NOT NULL,
  `has_fridge` tinyint(1) NOT NULL,
  `has_stove` tinyint(1) NOT NULL,
  `description` mediumtext NOT NULL,
  PRIMARY KEY (`property_id`),
  KEY `fk_property_type_idx` (`property_type_id`),
  CONSTRAINT `fk_property_type` FOREIGN KEY (`property_type_id`) REFERENCES `property_type` (`property_type_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
INSERT INTO `property` VALUES (1,1,1,'Минск','Ленина',6,NULL,104,5,105,150,120,1,1,1,1,1,1,'Уютный загородный дом'),(2,0,1,'Лида','Советская',14,NULL,NULL,9,120,40,30,1,1,0,0,0,1,'Крутой офис'),(3,2,1,'Минск','Гайа',104,NULL,17,2,40,NULL,NULL,1,1,1,0,0,0,'Новостройка, огонь'),(4,2,2,'Барановичи','Зеленый луг',35,NULL,26,3,74,NULL,NULL,1,1,1,1,0,1,'Эта квартира осталась от бабушки, срочно нужно продать'),(5,3,4,'Минск','Сосновый бор',84,NULL,NULL,NULL,150,1045,50,0,1,1,0,0,0,'Отличное место, чтобы построить многофункциональный торговый комплекс!'),(6,1,1,'Lida','Gastello',47,NULL,36,3,40,NULL,50,0,0,1,1,0,0,'hkjhjkhkj'),(7,1,1,'Lida','Gastello',47,NULL,36,3,40,NULL,50,0,0,1,1,0,0,'hkjhjkhkj'),(8,0,1,'Лида','Гастелло',47,4,36,3,50,NULL,200,1,1,1,1,1,1,'Ну бывает собственность такая');
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-28 13:34:26

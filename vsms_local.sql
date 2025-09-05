CREATE DATABASE  IF NOT EXISTS `vsms_local` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `vsms_local`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: vsms_local
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` int NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'NIROSHAN','PERERA','MALE',771234567,'niroshan.perera@example.com'),(2,'SAMANTHA','RATHNAYAKE','FEMALE',78567890,'samantha.rathnayake@example.com'),(3,'HARSHA','JAYASINGHE','MALE',773456786,'harsha.jayasinghe@example.com'),(4,'THARINDU','GUNARATNE','MALE',782345678,'tharindu.gunaratne@example.com'),(5,'SHANIKA','KUMARI','FEMALE',773456789,'shanika.kumari@example.com'),(6,'IRESH','FERNANDO','MALE',787890124,'iresh.fernando@example.com'),(7,'AD','AD','MALE',435,'asd'),(8,'A','A','FEMALE',54164,'sfgfg'),(9,'CHULANI','RAJAPAKSE','FEMALE',772345678,'chulani.rajapakse@example.com'),(10,'IRO','','MALE',772537349,''),(11,'PRASANNA','FERNANDO','MALE',784567890,'prasanna.fernando@example.com'),(12,'CHALANA','JAYOD','MALE',778559197,'chalanajayod@gmail.com'),(13,'ANUSHKA','RAJAPAKSHA','FEMALE',772345678,'anushka.rajapaksha@example.com'),(14,'RUVINI','PERERA','FEMALE',786789012,'ruvini.perera@example.com'),(15,'SUREN','WICKRAMASINGHE','MALE',773456789,'suren.wickramasinghe@example.com'),(16,'DILINI','JAYAWARDENA','FEMALE',786789012,'dilini.jayawardena@example.com'),(17,'KASUN','PERERA','MALE',771234567,'kasun.perera@example.com'),(18,'THILINI','SAMARASINGHE','FEMALE',787890123,'thilini.samarasinghe@example.com'),(19,'CHATHURI','MENDIS','FEMALE',782345678,'chathuri.mendis@example.com'),(20,'SAMAN','SILVA','MALE',773456789,'saman.silva@example.com'),(21,'NADEESHA','DISSANAYAKE','FEMALE',787890123,'nadeesha.dissanayake@example.com'),(22,'RUWAN','SENEVIRATNE','MALE',772345678,'ruwan.seneviratne@example.com'),(23,'KASUNI','WIJESURIYA','FEMALE',786789012,'kasuni.wijesuriya@example.com'),(24,'PRADEEP','KARUNARATNE','MALE',772345678,'pradeep.karunaratne@example.com'),(25,'A','A','MALE',34,'sdf'),(26,'ASD','ASD','MALE',123,'asd'),(27,'ASD','ASD','FEMALE',234,'sad'),(28,'SDF','JKH','FEMALE',46575,'dfgdf'),(29,'A','A','FEMALE',345,'sdf');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `invoiceNo` int NOT NULL AUTO_INCREMENT,
  `Date` date DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `total` double(8,2) DEFAULT NULL,
  `discount` double(8,2) DEFAULT NULL,
  `subTotal` double(8,2) DEFAULT NULL,
  `userID` int NOT NULL,
  PRIMARY KEY (`invoiceNo`,`userID`),
  KEY `fk_invoice_user1_idx` (`userID`),
  CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_has_job`
--

DROP TABLE IF EXISTS `invoice_has_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_has_job` (
  `invoiceNo` int NOT NULL,
  `jobNo` int NOT NULL,
  PRIMARY KEY (`invoiceNo`,`jobNo`),
  KEY `fk_invoice_has_job_job1_idx` (`jobNo`),
  KEY `fk_invoice_has_job_invoice1_idx` (`invoiceNo`),
  CONSTRAINT `fk_invoice_has_job_invoice1` FOREIGN KEY (`invoiceNo`) REFERENCES `invoice` (`invoiceNo`),
  CONSTRAINT `fk_invoice_has_job_job1` FOREIGN KEY (`jobNo`) REFERENCES `job` (`jobNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_has_job`
--

LOCK TABLES `invoice_has_job` WRITE;
/*!40000 ALTER TABLE `invoice_has_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_has_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_has_stock`
--

DROP TABLE IF EXISTS `invoice_has_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_has_stock` (
  `invoiceNo` int NOT NULL,
  `partID` int NOT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`invoiceNo`,`partID`),
  KEY `fk_invoice_has_stock_stock1_idx` (`partID`),
  KEY `fk_invoice_has_stock_invoice1_idx` (`invoiceNo`),
  CONSTRAINT `fk_invoice_has_stock_invoice1` FOREIGN KEY (`invoiceNo`) REFERENCES `invoice` (`invoiceNo`),
  CONSTRAINT `fk_invoice_has_stock_stock1` FOREIGN KEY (`partID`) REFERENCES `stock` (`partID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_has_stock`
--

LOCK TABLES `invoice_has_stock` WRITE;
/*!40000 ALTER TABLE `invoice_has_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_has_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `jobNo` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `vehicleNo` varchar(10) NOT NULL,
  `customerID` int NOT NULL,
  PRIMARY KEY (`jobNo`,`vehicleNo`,`customerID`),
  KEY `fk_job_vehicle1_idx` (`vehicleNo`,`customerID`),
  CONSTRAINT `fk_job_vehicle1` FOREIGN KEY (`vehicleNo`, `customerID`) REFERENCES `vehicle` (`vehicleNo`, `customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (12,'2025-04-24','AB-1234',1);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_has_service`
--

DROP TABLE IF EXISTS `job_has_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_has_service` (
  `jobNo` int NOT NULL,
  `serviceID` int NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`jobNo`,`serviceID`),
  KEY `fk_service_has_job_job1_idx` (`jobNo`),
  KEY `fk_service_has_job_service1_idx` (`serviceID`),
  CONSTRAINT `fk_service_has_job_job1` FOREIGN KEY (`jobNo`) REFERENCES `job` (`jobNo`),
  CONSTRAINT `fk_service_has_job_service1` FOREIGN KEY (`serviceID`) REFERENCES `service` (`serviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_has_service`
--

LOCK TABLES `job_has_service` WRITE;
/*!40000 ALTER TABLE `job_has_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_has_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `serviceID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` double(8,2) NOT NULL,
  `discount` double(8,2) DEFAULT NULL,
  `subPrice` double(8,2) NOT NULL,
  PRIMARY KEY (`serviceID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'Oil Change',50.00,5.00,45.00),(2,'Tire Rotation',40.00,0.00,40.00),(3,'Wheel Alignment',80.00,10.00,70.00),(4,'Brake Inspection',60.00,5.00,55.00),(5,'BATTERY REPLACEMENT',150.00,10.00,140.00),(6,'AIR FILTER REPLACEMENT',100.00,20.00,80.00),(7,'Cabin Filter Replacement',45.00,3.00,42.00),(8,'Fuel System Cleaning',150.00,15.00,135.00),(9,'COOLANT FLUSH',100.00,5.00,95.00),(10,'TRANSMISSION FLUID CHANGE',500.00,20.00,480.00),(11,'SPARK PLUG REPLACEMENT',85.00,5.00,80.00),(12,'Suspension Check',100.00,10.00,90.00),(13,'Steering Check',110.00,10.00,100.00),(14,'AC Service',130.00,10.00,120.00),(15,'Headlight Restoration',70.00,5.00,65.00),(16,'Car Wash',20.00,0.00,20.00),(17,'Interior Cleaning',50.00,5.00,45.00),(18,'Paint Protection',250.00,20.00,230.00),(19,'Wheel Balancing',60.00,5.00,55.00),(20,'Engine Diagnostic',100.00,10.00,90.00),(21,'A',11000.00,100.00,10900.00),(22,'B',100.00,50.00,50.00);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_has_stock`
--

DROP TABLE IF EXISTS `service_has_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_has_stock` (
  `serviceID` int NOT NULL,
  `partID` int NOT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`serviceID`,`partID`),
  KEY `fk_service_has_stock_stock1_idx` (`partID`),
  KEY `fk_service_has_stock_service1_idx` (`serviceID`),
  CONSTRAINT `fk_service_has_stock_service1` FOREIGN KEY (`serviceID`) REFERENCES `service` (`serviceID`),
  CONSTRAINT `fk_service_has_stock_stock1` FOREIGN KEY (`partID`) REFERENCES `stock` (`partID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_has_stock`
--

LOCK TABLES `service_has_stock` WRITE;
/*!40000 ALTER TABLE `service_has_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_has_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `partID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `brand` varchar(45) NOT NULL,
  `price` double(8,2) NOT NULL,
  `discount` double(8,2) DEFAULT NULL,
  `subPrice` double(8,2) NOT NULL,
  `qty` int NOT NULL,
  PRIMARY KEY (`partID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'Oil Filter','Bosch',15.00,2.00,13.00,50),(2,'Air Filter','K&N',25.00,3.00,22.00,34),(3,'Brake Pad Set','Brembo',80.00,10.00,70.00,30),(4,'BATTERY','EXIDE',120.00,20.00,100.00,30),(5,'Spark Plug','NGK',10.00,1.00,9.00,93),(6,'WIPER BLADE','BOSCH',200.00,2.50,197.50,25),(7,'Radiator Coolant','Prestone',30.00,5.00,25.00,5),(8,'Engine Oil','Castrol',40.00,5.00,35.00,60),(9,'TRANSMISSION OIL','MOBILE 1',50.00,8.00,42.00,43),(10,'Fuel Filter','Mann',18.00,2.00,16.00,33),(11,'SHOCK ABSORBER','MONROE',100.00,15.00,85.00,30),(12,'TIMING BELT','GATES',100.00,7.00,93.00,20),(13,'Headlight Bulb','Philips',15.00,2.00,13.00,32),(14,'Taillight Assembly','Hella',90.00,10.00,80.00,10),(15,'Clutch Kit','Valeo',200.00,25.00,175.00,3),(16,'WATER PUMP','BOSCH',100.00,10.00,90.00,50),(17,'Brake Disc','Brembo',150.00,20.00,130.00,12),(18,'Car Battery','Amaron',110.00,12.00,98.00,18),(19,'Wheel Bearing','SKF',45.00,5.00,40.00,25),(20,'Fan Belt','Dayco',25.00,3.00,22.00,30),(21,'A','A',1000.00,100.00,900.00,50);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` int NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT 'asdf',
  `password` varchar(45) DEFAULT 'asdf',
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'CHALANA','JAYOD','MALE',778559197,'','admin','admin','ADMIN'),(2,'CHAMATHKA','','FEMALE',774523958,'','1234','1234','CASHIER'),(3,'SAMAN','','MALE',77458926,'','abcd','abcd','MECHANIC'),(4,'CHAMARA','','MALE',77546464,'','a','zxcv','MECHANIC'),(5,'PETHUMI','SAMEERA','FEMALE',77855132,'sdfsf','asdf','asdf','CASHIER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `vehicleNo` varchar(10) NOT NULL,
  `type` varchar(15) NOT NULL,
  `make` varchar(20) NOT NULL,
  `model` varchar(20) DEFAULT NULL,
  `customerID` int NOT NULL,
  PRIMARY KEY (`vehicleNo`,`customerID`),
  KEY `fk_vehicle_customer1_idx` (`customerID`),
  CONSTRAINT `fk_vehicle_customer1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES ('AB-1234','CAR','TOYOTA','CAMRY',1),('AB-5678','CAR','MAZDA','CX-5',2),('CD-3456','TIPPER','CHEVROLET','SILVERADO',3),('CD-5678','TRCTOR','FORD','F-150',4),('DF-2345','CAB','GHJIOP','IOP',29),('DF-3567','JEEP','QWEWQE','QWE',1),('EF-3456','CAR','HONDA','CIVIC',5),('EF-7890','CAR','HYUNDAI','ELANTRA',6),('ER-4523','HEAVY-DUTY','SDF','SDF',1),('FG-3478','SUV','LAYLAND','',7),('FG-5690','MOTORCYCLE','ASD','ASD',28),('FG-7823','SUV','DSF','SDF',8),('GH-2345','SUV','TOYOTA','RAV4',9),('GH-2378','DOUBLE CAB','','',10),('GH-7890','MOTORCYCLE','HARLEY-DAVIDSON','IRON 883',11),('GK-4700','BUS','LAYLAND','',12),('IJ-2345','CAR','CHEVROLET','MALIBU',13),('IJ-6789','CAB','RAM','2500',14),('KL-3456','THREE WHEEL','KAWASAKI','NINJA 400',15),('KL-6789','SUV','JEEP','GRAND CHEROKEE',16),('MN-1234','TIPPER','RAM','1500',17),('MN-7890','BUS','FORD','FOCUS',18),('OP-5678','SUV','NISSAN','ALTIMA',19),('QR-3456','MOTORCYCLE','YAMAHA','YZF-R3',20),('QW-3456','DOUBLE CAB','ASD','ADS',24),('ST-7891','CAR','BMW','X5',21),('TY-4589','JEEP','SDF','SDF',1),('UV-2345','TIPPER','GMC','SIERRA',22),('WE-4523','MOTORCYCLE','DFSSDF','SDF',1),('WX-6789','SUV','FORD','EXPLORER',23),('YZ-2345','MOTORCYCLE','SUZUKI','GSX-R600',24);
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-24 18:35:39

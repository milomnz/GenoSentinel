CREATE DATABASE  IF NOT EXISTS `microdjango_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `microdjango_db`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: microdjango_db
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `gene`
--

DROP TABLE IF EXISTS `gene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gene` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `symbol` varchar(20) NOT NULL,
  `fullName` varchar(255) NOT NULL,
  `functionSummary` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol` (`symbol`),
  UNIQUE KEY `idx_gene_symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `geneticvariant`
--

DROP TABLE IF EXISTS `geneticvariant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `geneticvariant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `geneId` bigint(20) NOT NULL,
  `chromosome` varchar(10) NOT NULL,
  `position` bigint(20) NOT NULL,
  `referenceBase` varchar(10) NOT NULL,
  `alternateBase` varchar(10) NOT NULL,
  `impact` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_genetic_variant_gene` (`geneId`),
  KEY `idx_genetic_variant_position` (`chromosome`,`position`),
  CONSTRAINT `geneticvariant_ibfk_1` FOREIGN KEY (`geneId`) REFERENCES `gene` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patientvariantreport`
--

DROP TABLE IF EXISTS `patientvariantreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patientvariantreport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `patientId` bigint(20) NOT NULL,
  `variantId` bigint(20) NOT NULL,
  `detectionDate` date NOT NULL,
  `alleleFrequency` decimal(5,4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_patient_variant_patient` (`patientId`),
  KEY `idx_patient_variant_variant` (`variantId`),
  KEY `idx_patient_variant_date` (`detectionDate`),
  CONSTRAINT `patientvariantreport_ibfk_1` FOREIGN KEY (`variantId`) REFERENCES `geneticvariant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-29 11:57:06

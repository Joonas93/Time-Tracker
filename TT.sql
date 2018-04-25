-- --------------------------------------------------------
-- Host:                         eu-cdbr-azure-west-b.cloudapp.net
-- Server version:               5.5.56-log - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for information_schema

-- Dumping database structure for eskonheimo_joonas
CREATE DATABASE IF NOT EXISTS `eskonheimo_joonas` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eskonheimo_joonas`;

-- Dumping structure for table eskonheimo_joonas.project
CREATE TABLE IF NOT EXISTS `project` (
  `projectID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `projectName` varchar(50) NOT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `workTime` varchar(50) DEFAULT '0',
  `onPause` tinyint(4) NOT NULL DEFAULT '0',
  `finished` tinyint(4) NOT NULL DEFAULT '0',
  KEY `projectID` (`projectID`),
  KEY `FK_project_user` (`userID`),
  CONSTRAINT `FK_project_user` FOREIGN KEY (`userID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8;

-- Dumping data for table eskonheimo_joonas.project: ~7 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`projectID`, `userID`, `projectName`, `startTime`, `endTime`, `workTime`, `onPause`, `finished`) VALUES
	(284, 54, 'Testi-projekti', '2018-04-25 14:25:21', NULL, '61', 0, 0),
	(294, 54, 'Testi2', '2018-04-25 14:25:28', NULL, '47', 0, 0);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dumping structure for table eskonheimo_joonas.task
CREATE TABLE IF NOT EXISTS `task` (
  `taskID` int(11) NOT NULL AUTO_INCREMENT,
  `projectID` int(11) NOT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `pauseTime` timestamp NULL DEFAULT NULL,
  `continueTime` timestamp NULL DEFAULT NULL,
  `workTime` varchar(50) NOT NULL DEFAULT '0',
  `taskName` varchar(50) NOT NULL,
  `finished` tinyint(4) NOT NULL DEFAULT '0',
  KEY `taskID` (`taskID`),
  KEY `FK__project` (`projectID`),
  CONSTRAINT `FK__project` FOREIGN KEY (`projectID`) REFERENCES `project` (`projectID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8;

-- Dumping data for table eskonheimo_joonas.task: ~9 rows (approximately)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`taskID`, `projectID`, `startTime`, `endTime`, `pauseTime`, `continueTime`, `workTime`, `taskName`, `finished`) VALUES
	(324, 284, '2018-04-25 14:25:40', NULL, NULL, NULL, '1', 'Testi1', 0),
	(334, 284, '2018-04-25 14:28:05', NULL, NULL, NULL, '60', 'Testi2', 0),
	(374, 294, '2018-04-25 14:28:00', NULL, NULL, NULL, '19', 'Testi1', 0),
	(384, 294, '2018-04-25 14:28:18', NULL, NULL, NULL, '28', 'Testi2', 0);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;

-- Dumping structure for table eskonheimo_joonas.user
CREATE TABLE IF NOT EXISTS `user` (
  `UserID` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  KEY `UserID` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table eskonheimo_joonas.user: ~6 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`UserID`, `firstName`, `lastName`) VALUES
	(54, 'Joonas', 'Eskonheimo'),
	(64, 'Testi', 'Käyttäjä'),
	(1, 'System', 'Admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

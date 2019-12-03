BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `Users` (
	`Username`	TEXT NOT NULL UNIQUE,
	`PIN`	INTEGER NOT NULL UNIQUE,
	`LicensePlate`	TEXT NOT NULL UNIQUE,
	`BookedSpot`	INTEGER,
	PRIMARY KEY(`Username`),
	FOREIGN KEY(`BookedSpot`) REFERENCES `Lot`(`SpotNumber`)
);
INSERT INTO `Users` VALUES ('Schramm',1234,'ABCD123',NULL);
INSERT INTO `Users` VALUES ('User',1111,'QWER111',NULL);
CREATE TABLE IF NOT EXISTS `Lot` (
	`SpotNumber`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`Occupancy`	INTEGER NOT NULL,
	`BookTime`	REAL
);
INSERT INTO `Lot` VALUES (1,0,NULL);
INSERT INTO `Lot` VALUES (2,0,NULL);
INSERT INTO `Lot` VALUES (3,0,NULL);
INSERT INTO `Lot` VALUES (4,0,NULL);
INSERT INTO `Lot` VALUES (5,0,NULL);
INSERT INTO `Lot` VALUES (6,0,NULL);
INSERT INTO `Lot` VALUES (7,0,NULL);
INSERT INTO `Lot` VALUES (8,0,NULL);
INSERT INTO `Lot` VALUES (9,0,NULL);
COMMIT;
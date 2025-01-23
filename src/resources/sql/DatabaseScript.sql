DROP DATABASE IF EXISTS tatskatytot;
CREATE DATABASE tatskatytot;
USE tatskatytot;

CREATE TABLE USERS (
    id int NOT NULL AUTO_INCREMENT,
    phoneNumber int(16) NOT NULL,
    email VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'maailmanilmaa';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON tatskatytot.* TO 'appuser'@'localhost';

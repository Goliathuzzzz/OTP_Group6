DROP DATABASE IF EXISTS tatskatytottestdb;
CREATE DATABASE tatskatytottestdb;
use tatskatytottestdb;


DROP USER IF EXISTS 'testuser'@'localhost';
CREATE USER 'testuser'@'localhost' IDENTIFIED by 'testpassword';
GRANT SELECT,INSERT,UPDATE,DELETE ON tatskatytottestdb.* TO 'testuser'@'localhost';
GRANT CREATE, DROP ON tatskatytottestdb.* TO 'testuser'@'localhost';
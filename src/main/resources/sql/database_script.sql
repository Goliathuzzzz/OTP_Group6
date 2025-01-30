DROP DATABASE IF EXISTS tatskatytot;
CREATE DATABASE tatskatytot;
use tatskatytot;


DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED by 'maailmanilmaa';
GRANT SELECT,INSERT,UPDATE,DELETE ON tatskatytot.* TO 'appuser'@'localhost';
GRANT CREATE, DROP ON tatskatytot.* TO 'appuser'@'localhost';
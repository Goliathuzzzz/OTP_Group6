DROP DATABASE IF EXISTS tatskatytot;
CREATE DATABASE tatskatytot CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use tatskatytot;


DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED by 'maailmanilmaa';
GRANT SELECT,INSERT,UPDATE,ALTER,DELETE ON tatskatytot.* TO 'appuser'@'localhost';
GRANT CREATE, DROP ON tatskatytot.* TO 'appuser'@'localhost';
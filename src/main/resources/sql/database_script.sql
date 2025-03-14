DROP DATABASE IF EXISTS tatskatytot;
CREATE DATABASE tatskatytot;
use tatskatytot;


DROP USER IF EXISTS 'appuser'@'%';
CREATE USER 'appuser'@'%' IDENTIFIED by 'maailmanilmaa';
GRANT SELECT,INSERT,UPDATE,ALTER,DELETE ON tatskatytot.* TO 'appuser'@'%';
GRANT CREATE, DROP ON tatskatytot.* TO 'appuser'@'%';
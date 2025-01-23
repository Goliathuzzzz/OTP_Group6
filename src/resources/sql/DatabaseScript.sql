DROP DATABASE IF EXISTS tatskatytot;
CREATE DATABASE tatskatytot;
USE tatskatytot;

CREATE TABLE SESSIONS (
                          session_id int NOT NULL AUTO_INCREMENT,
                          session_code int(4) NOT NULL,
                          status CHAR NOT NULL DEFAULT 'N',
                          session_date DATETIME,
                          primary key (session_id)
);

CREATE TABLE USERS (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    phoneNumber int(16) NOT NULL,
    email VARCHAR(50) NOT NULL,
    sessionNumber int,
    PRIMARY KEY (id),
    FOREIGN KEY (sessionNumber) references SESSIONS (session_id) ON DELETE CASCADE
);

CREATE TABLE MATCHES (
    match_id int NOT NULL AUTO_INCREMENT,
    user1_id int NOT NULL,
    user2_id int NOT NULL,
    match_date DATETIME,
    PRIMARY KEY (match_id),
    foreign key (user1_id) references USERS (id) ON DELETE CASCADE,
    foreign key (user2_id) references USERS (id) ON DELETE CASCADE
);

DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'maailmanilmaa';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON tatskatytot.* TO 'appuser'@'localhost';

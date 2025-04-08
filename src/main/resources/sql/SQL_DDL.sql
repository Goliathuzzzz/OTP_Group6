CREATE TABLE `users`
(
    `user_id`      INT UNSIGNED    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`         VARCHAR(255)    NOT NULL,
    `phone_number` BIGINT UNSIGNED NOT NULL,
    `email`        VARCHAR(255)    NOT NULL,
    `join_date`    DATETIME        NOT NULL,
    `profile_pic`  VARCHAR(255)    NULL
);
CREATE TABLE `sessions`
(
    `session_id`   INT UNSIGNED    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `session_code` INT(4) UNSIGNED NOT NULL,
    `status`       CHAR(255)       NOT NULL DEFAULT 'N',
    `session_date` DATETIME        NOT NULL,
    `type`         VARCHAR(255)    NOT NULL DEFAULT 'Kaikki deittaa kaikkia',
    `description`  VARCHAR(255)    NULL
);
CREATE TABLE `matches`
(
    `match_id`   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `session_id` INT UNSIGNED NOT NULL,
    `user1_id`   INT UNSIGNED NOT NULL,
    `user2_id`   INT          NOT NULL
);
CREATE TABLE `registrations`
(
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `session_id` INT UNSIGNED NOT NULL,
    `user_id`    INT UNSIGNED NOT NULL
);
CREATE TABLE `chat_logs`
(
    `log_id`             INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `message`            VARCHAR(255) NOT NULL,
    `message_date`       DATETIME     NOT NULL,
    `session_id`         INT UNSIGNED NOT NULL,
    `user_id`            INT UNSIGNED NOT NULL,
    `guest_phone_number` BIGINT       NULL
);
CREATE TABLE `guests`
(
    `guest_id`     INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phone_number` BIGINT       NOT NULL,
    `join_date`    DATETIME     NOT NULL
);
ALTER TABLE
    `registrations`
    ADD CONSTRAINT `registrations_session_id_foreign` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`session_id`);
ALTER TABLE
    `chat_logs`
    ADD CONSTRAINT `chat_logs_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
ALTER TABLE
    `chat_logs`
    ADD CONSTRAINT `chat_logs_session_id_foreign` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`session_id`);
ALTER TABLE
    `matches`
    ADD CONSTRAINT `matches_user1_id_foreign` FOREIGN KEY (`user1_id`) REFERENCES `guests` (`guest_id`);
ALTER TABLE
    `matches`
    ADD CONSTRAINT `matches_session_id_foreign` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`session_id`);
ALTER TABLE
    `matches`
    ADD CONSTRAINT `matches_user2_id_foreign` FOREIGN KEY (`user2_id`) REFERENCES `guests` (`guest_id`);
ALTER TABLE
    `registrations`
    ADD CONSTRAINT `registrations_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `guests` (`guest_id`);
ALTER TABLE
    `registrations`
    ADD CONSTRAINT `registrations_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
ALTER TABLE
    `matches`
    ADD CONSTRAINT `matches_user1_id_foreign` FOREIGN KEY (`user1_id`) REFERENCES `users` (`user_id`);
ALTER TABLE
    `guests`
    ADD CONSTRAINT `guests_phone_number_foreign` FOREIGN KEY (`phone_number`) REFERENCES `chat_logs` (`guest_phone_number`);
ALTER TABLE
    `matches`
    ADD CONSTRAINT `matches_user2_id_foreign` FOREIGN KEY (`user2_id`) REFERENCES `users` (`user_id`);
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`(
    `id`        int(10)         unsigned    NOT NULL AUTO_INCREMENT,
    `email`     varchar(255)                NOT NULL,
    `password`  varchar(255)                NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs`(
    `id`        int(10)         unsigned    NOT NULL AUTO_INCREMENT,
    `user_id`   int(10)         unsigned    NOT NULL,
    `location`  geometry                    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `logs` ADD KEY (`user_id`);
ALTER TABLE `logs` ADD FOREIGN KEY (`user_id`) REFERENCES `users`(`id`);

CREATE TABLE `users` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `user_id` varchar(64) NOT NULL,
                         `username` varchar(45) NOT NULL UNIQUE ,
                         `firstname` varchar(45) NOT NULL,
                         `lastname` varchar(45) NOT NULL,
                         `password` varchar(64) NOT NULL,
                         `enabled` BOOLEAN DEFAULT FALSE,
                         `token_expired` BOOLEAN DEFAULT FALSE,
                         `created_at` TIMESTAMP,
                         PRIMARY KEY (`id`)
);
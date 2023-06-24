CREATE TABLE `user_roles` (
                               `user_id` int(11) NOT NULL,
                               `role_id` int(11) NOT NULL,
                               KEY `user_fk_idx` (`user_id`),
                               KEY `role_fk_idx` (`role_id`),
                               CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
                               CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- CREATE TABLE `user_roles` (
--                               `user_id` int(11) NOT NULL,
--                               `role_id` int(11) NOT NULL,
--                             PRIMARY KEY (user_id, role_id)
-- );
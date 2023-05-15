CREATE TABLE `listing` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `titre` varchar(255) NOT NULL,
                           `statut` tinyint(1) NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
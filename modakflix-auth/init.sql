-- Ensure the correct database is selected
USE modak_flix;

-- Create the table server_user
CREATE TABLE IF NOT EXISTS `server_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `joined` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Insert the sample data into server_user table
INSERT INTO `server_user` (`id`, `first_name`, `joined`, `last_name`, `password`, `role`, `username`) VALUES
(2, 'admin', '2024-11-29 21:18:07.000000', 'admin', '$2b$12$v3ICF/rgVY6ToSOOK0xnbuJz8fhzhtPLaQV0c2ue9revwbPme/SgW', 'ADMIN', 'admin');

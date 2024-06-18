CREATE TABLE IF NOT EXISTS `history` (
                                         `id` INT AUTO_INCREMENT PRIMARY KEY,
                                         `description` VARCHAR(255),
    `date_and_time` TIMESTAMP,
    `document` INT,
    CONSTRAINT `document_fk` FOREIGN KEY (`document`) REFERENCES `documents` (`id`)
    );


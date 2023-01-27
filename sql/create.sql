-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema taxiapp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema taxiapp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `taxiapp` DEFAULT CHARACTER SET utf8;
USE `taxiapp`;

-- -----------------------------------------------------
-- Table `taxiapp`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`status`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`status`
(
    `status_id` INT         NOT NULL AUTO_INCREMENT,
    `status`    VARCHAR(64) NOT NULL,
    PRIMARY KEY (`status_id`),
    UNIQUE INDEX `status_UNIQUE` (`status` ASC) VISIBLE
);


-- -----------------------------------------------------
-- Table `taxiapp`.`car_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`car_type`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`car_type`
(
    `type_id`   INT         NOT NULL AUTO_INCREMENT,
    `type_name` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`type_id`),
    UNIQUE INDEX `type_name_UNIQUE` (`type_name` ASC) VISIBLE
);


-- -----------------------------------------------------
-- Table `taxiapp`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`roles`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`roles`
(
    `role_id`   INT         NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE
);


-- -----------------------------------------------------
-- Table `taxiapp`.`cars`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`cars`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`cars`
(
    `car_id`     INT                  NOT NULL AUTO_INCREMENT,
    `car_name`   VARCHAR(255)         NOT NULL,
    `passengers` INT UNSIGNED         NOT NULL,
    `cost`       DECIMAL(10) UNSIGNED NOT NULL,
    `type_id`    INT                  NOT NULL,
    `status_id`  INT                  NOT NULL,
    PRIMARY KEY (`car_id`),
    INDEX `fk_car_type_idx` (`type_id` ASC) VISIBLE,
    INDEX `fk_car_status1_idx` (`status_id` ASC) VISIBLE,
    CONSTRAINT `fk_car_type`
        FOREIGN KEY (`type_id`)
            REFERENCES `taxiapp`.`car_type` (`type_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_car_status1`
        FOREIGN KEY (`status_id`)
            REFERENCES `taxiapp`.`status` (`status_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table `taxiapp`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`users`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`users`
(
    `user_id`   INT           NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(32)   NOT NULL,
    `email`     VARCHAR(255)  NULL,
    `password`  VARCHAR(1024) NOT NULL,
    `role_id`   INT           NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_role1`
        FOREIGN KEY (`role_id`)
            REFERENCES `taxiapp`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table `taxiapp`.`locations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`locations`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`locations`
(
    `location_id`   INT          NOT NULL AUTO_INCREMENT,
    `location_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`location_id`),
    INDEX `fk_orders_locations1_idx` (`location_id` ASC) VISIBLE,
    INDEX `fk_orders_locations2_idx` (`location_name` ASC) VISIBLE
);


-- -----------------------------------------------------
-- Table `taxiapp`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`orders`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`orders`
(
    `id`            INT                  NOT NULL AUTO_INCREMENT,
    `car_id`        INT                  NOT NULL,
    `user_id`       INT                  NOT NULL,
    `order_date`    DATE                 NOT NULL,
    `cost`          DECIMAL(10) UNSIGNED NOT NULL,
    `passengers`    INT UNSIGNED         NOT NULL,
    `location_to`   VARCHAR(255)         NOT NULL,
    `location_from` VARCHAR(255)         NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_order_car1_idx` (`car_id` ASC) VISIBLE,
    INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_order_location1_idx` (`location_to` ASC) VISIBLE,
    INDEX `fk_order_location2_idx` (`location_from` ASC) VISIBLE,
    CONSTRAINT `fk_order_car1`
        FOREIGN KEY (`car_id`)
            REFERENCES `taxiapp`.`cars` (`car_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_order_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `taxiapp`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_order_location1`
        FOREIGN KEY (`location_to`)
            REFERENCES `taxiapp`.`locations` (`location_name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_order_location2`
        FOREIGN KEY (`location_from`)
            REFERENCES `taxiapp`.`locations` (`location_name`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table `taxiapp`.`distances`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiapp`.`distances`;

CREATE TABLE IF NOT EXISTS `taxiapp`.`distances`
(
    `distance_id` INT                  NOT NULL AUTO_INCREMENT,
    `distance`    DECIMAL(10) UNSIGNED NOT NULL,
    `location_1`  INT                  NOT NULL,
    `location_2`  INT                  NOT NULL,
    PRIMARY KEY (`distance_id`),
    INDEX `fk_distances_locations1_idx` (`location_1` ASC) VISIBLE,
    INDEX `fk_distances_locations2_idx` (`location_2` ASC) VISIBLE,
    CONSTRAINT `fk_distances_locations1`
        FOREIGN KEY (`location_1`)
            REFERENCES `taxiapp`.`locations` (`location_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_distances_locations2`
        FOREIGN KEY (`location_2`)
            REFERENCES `taxiapp`.`locations` (`location_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema menudb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema menudb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `menudb` DEFAULT CHARACTER SET utf8 ;
USE `menudb` ;

-- -----------------------------------------------------
-- Table `menudb`.`restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`restaurant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menudb`.`menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `restaurant_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_menu_restaurant1_idx` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `fk_menu_restaurant1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `menudb`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `menudb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`category` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `image_path` VARCHAR(255) NULL DEFAULT NULL,
  `menu_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_menu1_idx` (`menu_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_menu1`
    FOREIGN KEY (`menu_id`)
    REFERENCES `menudb`.`menu` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `menudb`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `category_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `menudb`.`category` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `menudb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `user_type` INT(11) NOT NULL,
  `restaurant_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_restaurant1_idx` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_restaurant1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `menudb`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `menudb`.`user_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`user_profile` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(100) NULL DEFAULT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_profile_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_profile_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `menudb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `menudb`.`restaurante_profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`restaurante_profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(45) NULL,
  `restaurant_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_restaurante_profile_restaurant1_idx` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `fk_restaurante_profile_restaurant1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `menudb`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

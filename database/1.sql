ALTER TABLE restaurant ADD COLUMN approved BIT DEFAULT 0;

-- -----------------------------------------------------
-- Table `menudb`.`restaurant_approvement_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menudb`.`restaurant_approvement_request` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `restaurant_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_restaurant_approvement_request_restaurant1_idx` (`restaurant_id` ASC) VISIBLE,
  CONSTRAINT `fk_restaurant_approvement_request_restaurant1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `menudb`.`restaurant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


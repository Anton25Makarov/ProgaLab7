CREATE TABLE `psp_lab7_1`.`medicine` (
  `name` VARCHAR(45) NOT NULL,
  `issue_year` INT NOT NULL,
  `shelf_life` INT NOT NULL,
  `price`  DECIMAL(5,2) NOT NULL,
  `disease` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`))

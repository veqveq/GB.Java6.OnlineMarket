//Инициализация таблицы с продуктами
CREATE TABLE `products_bd`.`products_tbl` (
  `id_fld` BIGINT NOT NULL AUTO_INCREMENT,
  `title_fld` VARCHAR(255) NOT NULL,
  `cost_fld` INT NOT NULL,
  `created_at_fld` DATETIME NOT NULL,
  `updated_at_fld` DATETIME NULL,
  PRIMARY KEY (`id_fld`));
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 1', '100');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 2', '120');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 3', '80');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 4', '50');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 5', '95');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 6', '19');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 7', '36');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 8', '116');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 9', '195');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 10', '37');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 11', '75');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 12', '69');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 13', '14');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 14', '41');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 15', '45');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 16', '80');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 17', '98');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 18', '66');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 19', '25');
  INSERT INTO `products_bd`.`products_tbl` (`title_fld`, `cost_fld`) VALUES ('Product 20', '110');

CREATE TABLE `db_travel`.`tb_numeracion` (
  `id_numeracion` INT NOT NULL AUTO_INCREMENT,
  `id_gestion` INT NOT NULL,
  `formulario` VARCHAR(45) NOT NULL,
  `valor` INT NOT NULL,
  PRIMARY KEY (`id_numeracion`),
  INDEX `fk_tb_numeracion_1_idx` (`id_gestion` ASC),
  CONSTRAINT `fk_tb_numeracion_1`
    FOREIGN KEY (`id_gestion`)
    REFERENCES `db_travel`.`tb_gestion` (`id_gestion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
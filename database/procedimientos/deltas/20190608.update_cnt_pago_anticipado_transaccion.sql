ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
ADD COLUMN `id_devolucion` INT(11) NULL AFTER `tipo`,
ADD INDEX `fk_cnt_pago_anticipado_transaccion_3_idx` (`id_devolucion` ASC);
ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
ADD CONSTRAINT `fk_cnt_pago_anticipado_transaccion_3`
  FOREIGN KEY (`id_devolucion`)
  REFERENCES `db_travel`.`cnt_devolucion` (`id_devolucion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
ADD COLUMN `id_gestion` INT(11) NULL AFTER `nombre`,
ADD INDEX `fk_cnt_comprobante_contable_8_idx` (`id_gestion` ASC);
ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
ADD CONSTRAINT `fk_cnt_comprobante_contable_8`
  FOREIGN KEY (`id_gestion`)
  REFERENCES `db_travel`.`tb_gestion` (`id_gestion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `db_travel`.`cnt_nota_debito` 
ADD COLUMN `id_gestion` INT(11) NULL AFTER `estado`,
ADD INDEX `fk_cnt_cnt_nota_debito_id_gestion` (`id_gestion` ASC);
ALTER TABLE `db_travel`.`cnt_nota_debito` 
ADD CONSTRAINT `fk_cnt_cnt_nota_debito_id_gestion`
  FOREIGN KEY (`id_gestion`)
  REFERENCES `db_travel`.`tb_gestion` (`id_gestion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `db_travel`.`cnt_ingreso_caja` 
ADD COLUMN `id_gestion` INT(11) NULL AFTER `estado`,
ADD INDEX `fk_cnt_cnt_ingreso_caja_id_gestion` (`id_gestion` ASC);
ALTER TABLE `db_travel`.`cnt_ingreso_caja` 
ADD CONSTRAINT `fk_cnt_cnt_ingreso_caja_id_gestion`
  FOREIGN KEY (`id_gestion`)
  REFERENCES `db_travel`.`tb_gestion` (`id_gestion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `db_travel`.`cnt_nota_credito` 
ADD COLUMN `id_gestion` INT(11) NULL AFTER `estado`,
ADD INDEX `fk_cnt_cnt_nota_credito_id_gestion` (`id_gestion` ASC);
ALTER TABLE `db_travel`.`cnt_nota_credito` 
ADD CONSTRAINT `fk_cnt_cnt_nota_credito_id_gestion`
  FOREIGN KEY (`id_gestion`)
  REFERENCES `db_travel`.`tb_gestion` (`id_gestion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `db_travel`.`cnt_pago_anticipado` 
ADD COLUMN `id_gestion` INT(11) NULL AFTER `estado`,
ADD INDEX `fk_cnt_cnt_pago_anticipado_id_gestion` (`id_gestion` ASC);
ALTER TABLE `db_travel`.`cnt_pago_anticipado` 
ADD CONSTRAINT `fk_cnt_pago_anticipado_id_gestiocnt_pago_anticipadon`
  FOREIGN KEY (`id_gestion`)
  REFERENCES `db_travel`.`tb_gestion` (`id_gestion`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `db_travel`.`cnt_ingreso_transaccion` 
ADD COLUMN `factor_cambiario` DECIMAL(12,2) NULL AFTER `estado`;

ALTER TABLE `db_travel`.`cnt_nota_debito_transaccion` 
CHANGE COLUMN `descripcion` `descripcion` VARCHAR(512) NULL DEFAULT NULL ;

ALTER TABLE `db_travel`.`cnt_cargo_boleto` 
CHANGE COLUMN `concepto` `concepto` VARCHAR(511) NULL DEFAULT NULL ;

ALTER TABLE `db_travel`.`cnt_plan_cuentas` 
ADD COLUMN `tipo_regularizacion` VARCHAR(1) NULL AFTER `comodin` ,
ADD COLUMN `id_cuenta_regularizacion` INT(11) NULL AFTER `tipo_regularizacion`;

update cnt_plan_cuentas set tipo_regularizacion = 'N' ;
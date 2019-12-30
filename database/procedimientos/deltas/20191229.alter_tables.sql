ALTER TABLE `db_travel`.`cnt_nota_debito` 
ADD COLUMN `numeracion` INT NULL AFTER `id_gestion`;

ALTER TABLE `db_travel`.`cnt_ingreso_caja` 
ADD COLUMN `numeracion` INT NULL AFTER `id_gestion`;

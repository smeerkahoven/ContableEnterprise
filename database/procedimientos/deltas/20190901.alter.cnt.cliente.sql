ALTER TABLE `db_travel`.`cnt_cliente` 
CHANGE COLUMN `nit` `nit` VARCHAR(128) NULL DEFAULT NULL ,
CHANGE COLUMN `telefono_fijo` `telefono_fijo` VARCHAR(128) NULL DEFAULT NULL ,
CHANGE COLUMN `telefono_celular` `telefono_celular` VARCHAR(128) NULL DEFAULT NULL ;

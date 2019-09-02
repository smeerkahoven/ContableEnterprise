ALTER TABLE `db_travel`.`cnt_cliente` 
CHANGE COLUMN `nit` `nit` VARCHAR(128) NULL DEFAULT NULL ,
CHANGE COLUMN `telefono_fijo` `telefono_fijo` VARCHAR(128) NULL DEFAULT NULL ,
CHANGE COLUMN `telefono_celular` `telefono_celular` VARCHAR(128) NULL DEFAULT NULL ;
ALTER TABLE `db_travel`.`cnt_cliente` 
CHANGE COLUMN `ci` `ci` VARCHAR(128) NULL DEFAULT NULL ;

ALTER TABLE `db_travel`.`cnt_cliente` 
CHANGE COLUMN `representante_direccion` `representante_direccion` VARCHAR(128) NULL DEFAULT NULL ,
CHANGE COLUMN `representante_telefono` `representante_telefono` VARCHAR(128) NULL DEFAULT NULL ,
CHANGE COLUMN `representante_celular` `representante_celular` VARCHAR(128) NULL DEFAULT NULL ,
ADD COLUMN `representante_nacimiento` VARCHAR(10) NULL DEFAULT NULL,
ADD COLUMN `representante_email` VARCHAR(128) NULL DEFAULT NULL AFTER `representante_nacimiento`;

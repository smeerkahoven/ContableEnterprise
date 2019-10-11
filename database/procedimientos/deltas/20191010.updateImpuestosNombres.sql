ALTER TABLE `db_travel`.`cnt_boleto` 
CHANGE COLUMN `impuesto_1_nombre` `impuesto_1_nombre` VARCHAR(32) NULL DEFAULT NULL ,
CHANGE COLUMN `impuesto_2_nombre` `impuesto_2_nombre` VARCHAR(32) NULL DEFAULT NULL ,
CHANGE COLUMN `impuesto_3_nombre` `impuesto_3_nombre` VARCHAR(32) NULL DEFAULT NULL ,
CHANGE COLUMN `impuesto_4_nombre` `impuesto_4_nombre` VARCHAR(32) NULL DEFAULT NULL COMMENT 'OTROS IMPUESTOS' ,
CHANGE COLUMN `impuesto_5_nombre` `impuesto_5_nombre` VARCHAR(32) NULL DEFAULT NULL ;

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

# alter comprobante
ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
CHANGE COLUMN `concepto` `concepto` VARCHAR(512) NULL DEFAULT NULL ;

# la transaccion
ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
ADD COLUMN `monto_bs` DECIMAL(12,2) NULL AFTER `id_devolucion`,
ADD COLUMN `monto_usd` DECIMAL(12,2) NULL AFTER `monto_bs`;



USE `db_travel`;
DROP procedure IF EXISTS `updateMontosPagoAnticipado`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosPagoAnticipado`(IN in_id_pago_anticipado INT)
BEGIN
DECLARE v_total_acreditado_bs DECIMAL(12,2);
DECLARE v_total_acreditado_usd DECIMAL(12,2);
DECLARE v_monto_anticipado DECIMAL(12,2);
DECLARE v_moneda VARCHAR(1);
DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto_bs) monto_total_bs, sum(monto_usd) monto_total_usd, monto_anticipado, nc.moneda
FROM cnt_pago_anticipado_transaccion it
	INNER JOIN cnt_pago_anticipado nc on nc.id_pago_anticipado = it.id_pago_anticipado
WHERE it.estado in( 'E', 'C','P') and it.id_pago_anticipado = in_id_pago_anticipado;

OPEN cur_transacciones ;

FETCH cur_transacciones INTO v_total_acreditado_bs, v_total_acreditado_usd, v_monto_anticipado, v_moneda ;

IF v_moneda = 'B' THEN
	IF v_total_acreditado_bs = v_monto_anticipado THEN
		UPDATE cnt_pago_anticipado SET
			monto_total_acreditado = v_total_acreditado_bs,
			estado = 'S'
		WHERE id_pago_anticipado = in_id_pago_anticipado;
	ELSE 
		UPDATE cnt_pago_anticipado SET
			monto_total_acreditado  = v_total_acreditado_bs
		WHERE id_pago_anticipado = in_id_pago_anticipado;
    END IF;
ELSE 
	IF v_total_acreditado_usd = v_monto_anticipado THEN
		UPDATE cnt_pago_anticipado SET
			monto_total_acreditado = v_total_acreditado_usd,
			estado = 'S'
		WHERE id_pago_anticipado = in_id_pago_anticipado;
	ELSE 
		UPDATE cnt_pago_anticipado SET
			monto_total_acreditado  = v_total_acreditado_usd
		WHERE id_pago_anticipado = in_id_pago_anticipado;
    END IF;
END IF ;

/*
IF v_total_acreditado = v_monto_anticipado THEN
	UPDATE cnt_pago_anticipado SET
		monto_total_acreditado = v_total_acreditado,
        estado = 'S'
	WHERE id_pago_anticipado = in_id_pago_anticipado;
else
	UPDATE cnt_pago_anticipado SET
		monto_total_acreditado  = v_total_acreditado
	WHERE id_pago_anticipado = in_id_pago_anticipado;
END IF ;*/

CLOSE cur_transacciones ;

 
END$$

DELIMITER ;




USE `db_travel`;
DROP procedure IF EXISTS `tmp_updatePagoAnticipado`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`%` PROCEDURE `tmp_updatePagoAnticipado`()
BEGIN

declare v_id numeric ;
DECLARE v_monto DECIMAL (12,2);
DECLARE v_moneda varchar(1);
DECLARE finished 			INTEGER DEFAULT 0 ;

DECLARE cur_data cursor for 
	select id_pago_anticipado_transaccion, monto, moneda from cnt_pago_anticipado_transaccion ;

declare continue handler for not found set finished = 1 ;

open cur_data ;

	loop_cur: LOOP 

		fetch cur_data into v_id, v_monto, v_moneda ;

		if v_moneda = 'B' then 
			update cnt_pago_anticipado_transaccion set monto_bs = v_monto
			where id_pago_anticipado_transaccion = v_id ;
		end if ;
    
		if finished = 1 then 
            leave loop_cur ;
		end if ;
            
	end loop loop_cur ;
  
close cur_data ;


END$$

DELIMITER ;




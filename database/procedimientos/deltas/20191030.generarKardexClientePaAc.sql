USE `db_travel`;
DROP procedure IF EXISTS `generarKardexClientePaAc`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClientePaAc`(in in_id_pago_anticipado int, 
in in_id_selected int)
BEGIN

DECLARE v_id_documento		INT(11);
DECLARE v_glosa 			VARCHAR(512) ;
DECLARE v_fecha 			VARCHAR(16) ;
DECLARE v_vencimiento 		VARCHAR(16);
DECLARE v_forma_pago 		VARCHAR(2);
DECLARE v_monto_debe_nac	DECIMAL(12,2); 
DECLARE v_monto_haber_nac	DECIMAL(12,2);
DECLARE v_saldo_nac    		DECIMAL(12,2);
DECLARE v_monto_debe_ext	DECIMAL(12,2);
DECLARE v_monto_haber_ext	DECIMAL(12,2);
DECLARE v_saldo_ext   	    DECIMAL(12,2);
DECLARE v_tipo_documento	VARCHAR(2);
DECLARE v_estado			VARCHAR(1);
DECLARE v_row				VARCHAR(8);
DECLARE v_selected			VARCHAR(1);

DECLARE finished 			INTEGER DEFAULT 0 ;

DECLARE cur_pago_transaccion CURSOR FOR
		select 
			patr.id_pago_anticipado_transaccion 		id_documento 
            , patr.descripcion glosa 
			, date_format(pa.fecha_emision,'%d/%m/%Y')			fecha
			, null 						fecha_vencimiento	
			, pa.forma_pago				forma_pago
			, case pa.moneda when 'B' then patr.monto end monto_debe_nac
			, 0 						monto_haber_nac
			, 0 saldo_nac
			, case pa.moneda when 'U' then patr.monto end monto_debe_ext 
			, 0							monto_haber_ext
			, 0 saldo_ext
			, patr.tipo
			, patr.estado
			, 'deposito' v_row
            , case patr.id_pago_anticipado_transaccion when in_id_selected then 'S' else 'N' end v_selected
		from cnt_pago_anticipado_transaccion patr
		inner join cnt_pago_anticipado pa on pa.id_pago_anticipado = patr.id_pago_anticipado
		where pa.id_pago_anticipado = in_id_pago_anticipado;
    
declare continue handler for not found set finished = 1 ;


open cur_pago_transaccion ;

	loop_cur_pago_transaccion: LOOP
			fetch cur_pago_transaccion into
						 v_id_documento,
                         v_glosa ,
						 v_fecha 		,
						 v_vencimiento 	,
						 v_forma_pago 	,
						 v_monto_debe_nac,
						 v_monto_haber_nac,
						 v_saldo_nac    ,
						 v_monto_debe_ext,
						 v_monto_haber_ext,
						 v_saldo_ext   	  ,
                         v_tipo_documento,
                         v_estado,
                         v_row,
                         v_selected;
			if finished = 1 then 
            leave loop_cur_pago_transaccion ;
            end if ;
						 
						 
			insert into tmp_kardex_cliente values (
						 v_id_documento,
                         v_glosa ,
						 v_fecha 		,
						 v_vencimiento 	,
						 v_forma_pago 	,
						 v_monto_debe_nac,
						 v_monto_haber_nac,
						 v_saldo_nac    ,
						 v_monto_debe_ext,
						 v_monto_haber_ext,
						 v_saldo_ext   	  ,
                         v_tipo_documento,
                         v_estado,
						 v_row, 
                         v_selected
			);
            
	END LOOP loop_cur_pago_transaccion ;

close cur_pago_transaccion ;


END$$

DELIMITER ;


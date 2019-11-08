USE `db_travel`;
DROP procedure IF EXISTS `generarKardexClienteNdIc`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClienteNdIc`(in in_id_nota_debito_transaccion int, 
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

DECLARE cur_nota_ingreso CURSOR FOR
	select 
		   ic.id_ingreso_caja id_documento
           , ictr.descripcion glosa 
           , date_format(ic.fecha_emision ,'%d/%m/%Y') fecha  
           , null vencimiento
           , ic.forma_pago
           , 0 monto_debe_nac
           , ictr.monto_bs monto_haber_nac 
           , 0 saldo_nac
           , 0 monto_debe_ext
           , ictr.monto_usd monto_haber_ext
           , 0 saldo_ext
           , 'IC' tipo_documento
           , ictr.estado 
           , 'debito' v_row
           , case ic.id_ingreso_caja when in_id_selected then 'S' else 'N' end v_selected
		   from cnt_ingreso_transaccion ictr 
	inner join cnt_ingreso_caja ic on ictr.id_ingreso_caja = ic.id_ingreso_caja
	inner join cnt_nota_debito_transaccion ndtr on ndtr.id_nota_debito_transaccion = ictr.id_nota_transaccion
		where ndtr.id_nota_debito_transaccion = in_id_nota_debito_transaccion
        and ic.estado = 'E'
        ;
    
declare continue handler for not found set finished = 1 ;


open cur_nota_ingreso ;

	loop_cur_nota_ingreso: LOOP
			fetch cur_nota_ingreso into
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
            leave loop_cur_nota_ingreso ;
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
            
	END LOOP loop_cur_nota_ingreso ;

close cur_nota_ingreso ;


END$$

DELIMITER ;


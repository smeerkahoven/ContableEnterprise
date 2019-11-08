USE `db_travel`;
DROP procedure IF EXISTS `generarKardexClienteNdPa`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClienteNdPa`(in in_id_nota_debito_transaccion int,
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
	select distinct 
		   ic.id_pago_anticipado id_documento
           , ndtr.descripcion glosa 
           , date_format(ic.fecha_emision, '%d/%m/%Y') fecha
           , null vencimiento
           , 'C'
           , 0 monto_debe_nac
           , case ic.moneda when 'B' then ictr.monto end monto_haber_nac 
           , 0 saldo_nac
           , 0 monto_debe_ext
           , case ic.moneda when 'U' then ictr.monto end monto_haber_ext
           , 0 saldo_ext
           , 'PA' tipo_documento
           , ictr.estado
           , 'debito' v_row
           , case ic.id_pago_anticipado when in_id_selected then 'S' else 'N' end v_selected
		   from cnt_pago_anticipado ic
	inner join cnt_pago_anticipado_transaccion ictr on ictr.id_pago_anticipado = ic.id_pago_anticipado
	inner join cnt_nota_debito_transaccion ndtr on ndtr.id_nota_debito_transaccion = ictr.id_nota_debito_transaccion
		where ndtr.id_nota_debito_transaccion = in_id_nota_debito_transaccion
        and ic.estado = 'E' 
        ;
    
declare continue handler for not found set finished = 1 ;


open cur_nota_ingreso ;

	loop_cur_nota_ingreso: LOOP
			fetch cur_nota_ingreso into
						 v_id_documento,
                         v_glosa,
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


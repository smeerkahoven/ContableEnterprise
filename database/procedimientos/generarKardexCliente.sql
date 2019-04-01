CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexCliente`(in in_id_cliente int,
 in in_id_selected int)
BEGIN

DECLARE v_id_documento		INT(11);
DECLARE v_id_transaccion 	INT (11);
DECLARE v_glosa				VARCHAR(128);
DECLARE v_fecha 			VARCHAR(16) ;
DECLARE v_vencimiento 		VARCHAR(16) ;
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



DECLARE cur_nota_debito CURSOR FOR
	select 
		nd.id_nota_debito 			id_documento
        , ndtr.id_nota_debito_transaccion 	id_transaccion
        , ndtr.descripcion glosa
        , date_format(nd.fecha_emision, '%d/%m/%Y') 			fecha
        , date_format(nd.credito_vencimiento,'%d/%m/%Y') 	   vencimiento
		, nd.forma_pago 			forma_pago
        , ndtr.monto_bs				monto_debe_nac
        , 0							monto_haber_nac
        , coalesce(ndtr.monto_adeudado_bs,0) saldo_nac
        , ndtr.monto_usd 		monto_debe_ext
        , 0							monto_haber_ext
        , coalesce(ndtr.monto_adeudado_usd,0) saldo_ext
        , 'ND' tipo_documento
        , nd.estado
        , 'debito' v_row
        , case nd.id_nota_debito when in_id_selected then 'S' else 'N' end v_selected
	from cnt_nota_debito nd 
    inner join cnt_nota_debito_transaccion ndtr on ndtr.id_nota_debito = nd.id_nota_debito
    where nd.id_cliente = in_id_cliente and nd.estado in ( 'E' , 'M', 'D') ;
	
    


DECLARE cur_pago_anticipado CURSOR FOR
	select 
		pa.id_pago_anticipado 		id_documento 
        , pa.concepto glosa
		, date_format(pa.fecha_emision,'%d/%m/%Y')			fecha
		, null 						fecha_vencimiento	
		, pa.forma_pago				forma_pago
		, 0 						monto_debe_nac
		, case pa.moneda when 'B' then pa.monto_anticipado end monto_haber_nac
		, case pa.moneda when 'B' then ( pa.monto_anticipado - coalesce(pa.monto_total_acreditado,0)) end saldo_nac
		, 0							monto_debe_ext
		, case pa.moneda when 'U' then pa.monto_anticipado end monto_haber_ext
		, case pa.moneda when 'U' then ( pa.monto_anticipado - coalesce(pa.monto_total_acreditado,0)) end saldo_ext
		, 'PA' tipo_documento
		, pa.estado
        , 'deposito' v_row
        , case pa.id_pago_anticipado when in_id_selected then 'S' else 'N' end v_selected
	from cnt_pago_anticipado pa
    where pa.id_cliente = in_id_cliente and pa.estado in ( 'D' )  ;
    

    
declare continue handler for not found set finished = 1 ;


drop temporary table if exists tmp_kardex_cliente ;
create temporary table tmp_kardex_cliente (
	 v_id_documento		INT(11),
     v_glosa			varchar(128),
	 v_fecha 			varchar(16) ,
	 v_vencimiento 		varchar(16) ,
	 v_forma_pago 		VARCHAR(2),
	 v_monto_debe_nac	DECIMAL(12,2),
	 v_monto_haber_nac	DECIMAL(12,2),
	 v_saldo_nac    		DECIMAL(12,2),
	 v_monto_debe_ext	DECIMAL(12,2),
	 v_monto_haber_ext	DECIMAL(12,2),
	 v_saldo_ext   	    DECIMAL(12,2),
     v_tipo_documento	VARCHAR(2),
     v_estado			VARCHAR(1),
     v_row				VARCHAR(8),
     v_selected			VARCHAR(1)
) ;


open cur_nota_debito ;

	loop_cur_nota_debito: LOOP
			fetch cur_nota_debito into
						 v_id_documento ,
                         v_id_transaccion,
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
            leave loop_cur_nota_debito ;
            end if ;
						 
						 
			insert into tmp_kardex_cliente values (
						 v_id_documento,
                         v_glosa		,
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
            
            
			 CALL  generarKardexClienteNdIc(v_id_transaccion, in_id_selected);
            
             CALL  generarKardexClienteNdNc(v_id_transaccion, in_id_selected);
            
             CALL  generarKardexClienteNdPa(v_id_transaccion, in_id_selected);
            
	END LOOP loop_cur_nota_debito ;

close cur_nota_debito ;



set finished = 0 ;
open cur_pago_anticipado ;

	loop_cur_pago_anticipado: LOOP
			fetch cur_pago_anticipado into
						 v_id_documento,
                         v_glosa	   ,
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
            leave loop_cur_pago_anticipado ;
            end if ;

			insert into tmp_kardex_cliente values (
						 v_id_documento,
                         v_glosa	    ,
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
            
            
			 CALL generarKardexClientePaAc (v_id_documento, in_id_selected);
			
            
            
	END LOOP loop_cur_pago_anticipado ;

close cur_pago_anticipado ;






select * from tmp_kardex_cliente ;


END
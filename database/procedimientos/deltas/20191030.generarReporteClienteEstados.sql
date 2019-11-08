USE `db_travel`;
DROP procedure IF EXISTS `generarReporteClienteEstados`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarReporteClienteEstados`( 
in in_id_empresa int ,
in in_start_date date, 
in in_end_date date)
BEGIN

DECLARE v_id_documento		INT(11);
DECLARE v_id_transaccion 	INT (11);
DECLARE v_glosa				VARCHAR(512);
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
DECLARE v_id_cliente		int(10);
DECLARE v_nombre_cliente	varchar(128);
DECLARE v_telefono_fijo		varchar(32);
declare v_telefono_celular	varchar(32);
declare v_email				varchar(128);

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
        , cl.id_cliente
        , cl.nombre nombre_cliente
        , cl.telefono_fijo
        , cl.telefono_celular
        , cl.email
	from cnt_nota_debito nd 
    inner join cnt_nota_debito_transaccion ndtr on ndtr.id_nota_debito = nd.id_nota_debito
    inner join cnt_cliente cl on cl.id_cliente = nd.id_cliente
    where  nd.estado in ( 'E' , 'M') 
		and nd.fecha_emision between in_start_date and in_end_date  
		and nd.id_empresa = in_id_empresa;

declare continue handler for not found set finished = 1 ;


drop temporary table if exists tmp_estado_cliente ;
create temporary table tmp_estado_cliente (
	 v_id_documento		INT(11),
     v_glosa			varchar(512),
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
     v_id_cliente		int(10),
     v_nombre_cliente	varchar(128),
     v_telefono_fijo	varchar(32),
     v_telefono_celular varchar(32),
     v_email			varchar(128)
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
                         v_id_cliente,
                         v_nombre_cliente,
                         v_telefono_fijo,
                         v_telefono_celular,
                         v_email
                         ;
			if finished = 1 then 
            leave loop_cur_nota_debito ;
            end if ;
						 
						 
			insert into tmp_estado_cliente values (
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
                         v_id_cliente,
                         v_nombre_cliente,
                         v_telefono_fijo,
                         v_telefono_celular,
                         v_email
			);
            
            
			-- CALL  generarKardexClienteNdIc(v_id_transaccion, in_id_selected);
            
            -- CALL  generarKardexClienteNdNc(v_id_transaccion, in_id_selected);
            
            -- CALL  generarKardexClienteNdPa(v_id_transaccion, in_id_selected);
            
	END LOOP loop_cur_nota_debito ;

close cur_nota_debito ;


select * from tmp_estado_cliente order by v_fecha, v_id_cliente;


END$$

DELIMITER ;


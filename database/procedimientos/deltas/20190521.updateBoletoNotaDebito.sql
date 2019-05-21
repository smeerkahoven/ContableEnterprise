CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateBoletoNotaDebito`(in in_id_boleto int, 
in in_id_nota_transaccion int)
BEGIN
DECLARE v_iata VARCHAR(4);
DECLARE v_tipo_cupon VARCHAR(2);
DECLARE v_numero bigint(18);
DECLARE v_tipo_boleto VARCHAR(2);
DECLARE v_ruta_1 VARCHAR(4);
DECLARE v_ruta_2 VARCHAR(4);
DECLARE v_ruta_3 VARCHAR(4);
DECLARE v_ruta_4 VARCHAR(4);
DECLARE v_ruta_5 VARCHAR(4);
DECLARE v_nombre_pasajero VARCHAR(64);
DECLARE v_moneda VARCHAR(2);
DECLARE v_gestion INT;
DECLARE v_total_boleto DECIMAL(12,2);
DECLARE v_id_transaccion INT;

DECLARE cur_boleto CURSOR FOR
	select a.numero, 
    b.tipo_cupon,
    b.numero,
    b.tipo_boleto,
	coalesce(b.id_ruta_1,'') id_ruta_1, 
	coalesce(b.id_ruta_2,'') id_ruta_2,
	coalesce(b.id_ruta_3,'') id_ruta_3,
	coalesce(b.id_ruta_4,'') id_ruta_4,
	coalesce(b.id_ruta_5,'') id_ruta_5,
	coalesce(b.nombre_pasajero,''), 
    b.moneda, 
    b.total_monto_cobrar,
    b.gestion
	from cnt_boleto b
	inner join cnt_aerolinea a on a.id_aerolinea=b.id_aerolinea
	where b.id_boleto = in_id_boleto;

OPEN cur_boleto ;

FETCH cur_boleto into 
	v_iata, 
    v_tipo_cupon, 
    v_numero, 
    v_tipo_boleto,
	v_ruta_1, 
    v_ruta_2, 
    v_ruta_3, 
    v_ruta_4, 
    v_ruta_5,
	v_nombre_pasajero, 
    v_moneda, 
    v_total_boleto, 
    v_gestion;

IF v_tipo_boleto = 'SV' OR v_tipo_boleto = 'AV' OR  v_tipo_boleto = 'MV' THEN
	IF v_moneda = 'U' THEN
		UPDATE cnt_nota_debito_transaccion SET
            descripcion = concat( coalesce(v_iata,''),'/','#',coalesce(v_numero,''),'/',coalesce(v_nombre_pasajero,'')),
            monto_usd   = v_total_boleto,
            monto_bs 	= null,
            monto_adeudado_usd   = v_total_boleto,
            monto_adeudado_bs	 = null
            
		WHERE  id_nota_debito_transaccion = in_id_nota_transaccion;

	ELSEIF v_moneda = 'B' THEN
		UPDATE cnt_nota_debito_transaccion SET
            descripcion = concat(coalesce(v_iata,''),'/','#',coalesce(v_numero,''),'/',coalesce(v_nombre_pasajero,'')),
            monto_bs    = v_total_boleto,
            monto_usd	= null ,
            monto_adeudado_bs    = v_total_boleto,
            monto_adeudado_usd = null
		WHERE  id_nota_debito_transaccion = in_id_nota_transaccion;
		
    END IF ;
ELSEIF v_tipo_boleto = 'SA' OR v_tipo_boleto = 'AM' OR  v_tipo_boleto = 'MA' THEN
IF v_moneda = 'U' THEN
		UPDATE cnt_nota_debito_transaccion SET
            descripcion	= concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5),
            monto_usd 	= v_total_boleto,
            monto_bs 	= null,
            monto_adeudado_usd 	= v_total_boleto,
            monto_adeudado_bs 	= null
		WHERE id_nota_debito_transaccion = in_id_nota_transaccion;
        
	ELSEIF v_moneda = 'B' THEN
		UPDATE cnt_nota_debito_transaccion SET			
            descripcion	= concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5), 
            monto_bs 	= v_total_boleto,
            monto_usd	= null,
            monto_adeudado_bs 	= v_total_boleto,
            monto_adeudado_usd	= null
		WHERE id_nota_debito_transaccion = in_id_nota_transaccion;
        
    END IF ;
END IF ;

CLOSE cur_boleto ;


END



--
-- Dumping events for database 'db_travel'
--

--
-- Dumping routines for database 'db_travel'
--

SET GLOBAL log_bin_trust_function_creators = 1 ;

/*!50003 DROP FUNCTION IF EXISTS `number_to_string` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` FUNCTION `number_to_string`(n decimal (12,2)) RETURNS varchar(100) CHARSET latin1
BEGIN
    -- This function returns the string representation of a number.
    -- It's just an example... I'll restrict it to hundreds, but
    -- it can be extended easily.
    -- The idea is: 
    --      For each digit you need a position,
    --      For each position, you assign a string
    declare ans varchar(100);
    declare dig1, dig2, dig3, dig4, dig5, dig6, dig7 int; -- (one variable per digit)
	declare actual_value decimal(12,2);
    declare mil bool ;
    
    set mil = false ;
    set ans = '';
    set actual_value = truncate (n,0) ;	
    
    set dig7 = floor(actual_value/1000000);
    set actual_value = mod(actual_value,1000000);
    
    if dig7>0 then 
		case 
			when dig7 = 1 then set ans=concat(ans,' UN MILLON');
            when dig7 = 2 then set ans=concat(ans,' DOS MILLONES');
            when dig7 = 3 then set ans=concat(ans,' TRES MILLONES');
            when dig7 = 4 then set ans=concat(ans,' CUATRO MILLONES');
            when dig7 = 5 then set ans=concat(ans,' CINCO MILLONES');
            when dig7 = 6 then set ans=concat(ans,' SEIS MILLONES');
            when dig7 = 7 then set ans=concat(ans,' SIETE MILLONES');
            when dig7 = 8 then set ans=concat(ans,' OCHO MILLONES');
            when dig7 = 9 then set ans=concat(ans,' NUEVE MILLONES');		
        end case ;
    end if ;
    
    /*cien mil, diez mil, mil*/
    set dig6 = floor(actual_value / 100000);
    set actual_value = mod(actual_value,100000);
    
    if dig6 > 0 then
		set mil = true ;
        case
            when dig6=1 then 
				if actual_value >999 then
					set ans=concat(ans, ' CIENTO');
				else 
					set ans=concat(ans, ' CIEN');
				end if;
                
            when dig6=2 then set ans=concat(ans, ' DOSCIENTOS');
            when dig6=3 then set ans=concat(ans, ' TRESCIENTOS');
            when dig6=4 then set ans=concat(ans, ' CUATROCIENTOS');
            when dig6=5 then set ans=concat(ans, ' QUINIENTOS');
            when dig6=6 then set ans=concat(ans, ' SEISCIENTOS');
            when dig6=7 then set ans=concat(ans, ' SETECIENTOS');
            when dig6=8 then set ans=concat(ans, ' OCHOCIENTOS');
            when dig6=9 then set ans=concat(ans, ' NOVECIENTOS');
            else set ans = ans;
        end case;
                
    end if;
    
    set dig5 = floor(actual_value/10000);
    
    set actual_value = mod(actual_value,10000);    
    
    set dig4 = floor(actual_value/1000);

    if dig5 = 1 then
		set mil = true ;
        case
            when dig4 = 0 then set ans=concat(ans,' DIEZ');
            when dig4 = 1 then set ans=concat(ans,' ONCE');
            when dig4 = 2 then set ans=concat(ans,' DOCE');
            when dig4 = 3 then set ans=concat(ans,' TRECE');
            when dig4 = 4 then set ans=concat(ans,' CATORCE');
            when dig4 = 5 then set ans=concat(ans,' QUINCE');
            when dig4 = 6 then set ans=concat(ans,' DIECISEIS');
            when dig4 = 7 then set ans=concat(ans,' DIECISIETE');
            when dig4 = 8 then set ans=concat(ans,' DIECIOCHO');
            when dig4 = 9 then set ans=concat(ans,' DIECINUEVE');
            else set ans=ans;
        end case;
        
    else
        if dig5 > 0 then
			set mil = true ;
            case
                when dig5=2 then 
					if (dig4 > 0 ) then
						set ans=concat(ans, ' VEINTI');
					else 
						set ans=concat(ans, ' VEINTE');
					end if ;
                when dig5=3 then set ans=concat(ans, ' TREINTA');
                when dig5=4 then set ans=concat(ans, ' CUARENTA');
                when dig5=5 then set ans=concat(ans, ' CINCUENTA');
                when dig5=6 then set ans=concat(ans, ' SESENTA');
                when dig5=7 then set ans=concat(ans, ' SETENTA');
                when dig5=8 then set ans=concat(ans, ' OCHENTA');
                when dig5=9 then set ans=concat(ans, ' NOVENTA');
                else set ans=ans;
            end case;
            if dig4 > 0 then
				if dig5 > 2 then
					set ans =concat(ans , ' Y');
                end if ;
			end if ;
        end if;
        if dig4 > 0 then
			set mil = true ;
            case
                when dig4=1 then set ans=concat(ans, ' UN');
                when dig4=2 then set ans=concat(ans, ' DOS');
                when dig4=3 then set ans=concat(ans, ' TRES ');
                when dig4=4 then set ans=concat(ans, ' CUATRO');
                when dig4=5 then set ans=concat(ans, ' CINCO');
                when dig4=6 then set ans=concat(ans, ' SEIS');
                when dig4=7 then set ans=concat(ans, ' SIETE');
                when dig4=8 then set ans=concat(ans, ' OCHO');
                when dig4=9 then set ans=concat(ans, ' NUEVE');
                else set ans=ans;
            end case;
        end if;
        
        
        
    end if;

	if (mil = true) then
		set ans = concat(ans, ' MIL') ;
	end if ;
        
	set actual_value = mod(actual_value,1000);    
	/**
		centena, decena, unidad
    */
    set dig3 = floor(actual_value / 100);
    set actual_value = mod(actual_value,100);
    set dig2 = floor(actual_value/10);
    
    if dig3 > 0 then
        case
            when dig3=1 then 
				if actual_value>0  then
					set ans=concat(ans, ' CIENTO');
                else 
					set ans=concat(ans, ' CIEN');
				end if ;
            when dig3=2 then set ans=concat(ans, ' DOSCIENTOS');
            when dig3=3 then set ans=concat(ans, ' TRESCIENTOS');
            when dig3=4 then set ans=concat(ans, ' CUATROCIENTOS');
            when dig3=5 then set ans=concat(ans, ' QUINIENTOS');
            when dig3=6 then set ans=concat(ans, ' SEISCIENTOS');
            when dig3=7 then set ans=concat(ans, ' SETECIENTOS');
            when dig3=8 then set ans=concat(ans, ' OCHOCIENTOS');
            when dig3=9 then set ans=concat(ans, ' NOVECIENTOS');
            else set ans = ans;
        end case;
    end if;
    
    
    
    set actual_value = mod(actual_value,10);    
    set dig1 = actual_value;

    if dig2 = 1 then
        case
            when dig1 = 0 then set ans=concat(ans,' DIEZ');
            when dig1 = 1 then set ans=concat(ans,' ONCE');
            when dig1 = 2 then set ans=concat(ans,' DOCE');
            when dig1 = 3 then set ans=concat(ans,' TRECE');
            when dig1 = 4 then set ans=concat(ans,' CATORCE');
            when dig1 = 5 then set ans=concat(ans,' QUINCE');
            when dig1 = 6 then set ans=concat(ans,' DIECISEIS');
            when dig1 = 7 then set ans=concat(ans,' DIECISIETE');
            when dig1 = 8 then set ans=concat(ans,' DIECIOCHO');
            when dig1 = 9 then set ans=concat(ans,' DIECINUEVE');
            else set ans=ans;
        end case;
        
    else
        if dig2 > 0 then
            case
                when dig2=2 then 
					if (dig1 > 0 ) then
						set ans=concat(ans, ' VEINTI');
					else 
						set ans=concat(ans, ' VEINTE');
					end if ;
                when dig2=3 then set ans=concat(ans, ' TREINTA');
                when dig2=4 then set ans=concat(ans, ' CUARENTA');
                when dig2=5 then set ans=concat(ans, ' CINCUENTA');
                when dig2=6 then set ans=concat(ans, ' SESENTA');
                when dig2=7 then set ans=concat(ans, ' SETENTA');
                when dig2=8 then set ans=concat(ans, ' OCHENTA');
                when dig2=9 then set ans=concat(ans, ' NOVENTA');
                else set ans=ans;
            end case;
            if dig1 > 0 then
				if dig2 > 2 then
					set ans =concat(ans , ' Y');
                end if ;
			end if ;
        end if;
        if dig1 > 0 then
            case
                when dig1=1 then set ans=concat(ans, ' UNO');
                when dig1=2 then set ans=concat(ans, ' DOS');
                when dig1=3 then set ans=concat(ans, ' TRES');
                when dig1=4 then set ans=concat(ans, ' CUATRO');
                when dig1=5 then set ans=concat(ans, ' CINCO');
                when dig1=6 then set ans=concat(ans, ' SEIS');
                when dig1=7 then set ans=concat(ans, ' SIETE');
                when dig1=8 then set ans=concat(ans, ' OCHO');
                when dig1=9 then set ans=concat(ans, ' NUEVE');
                else set ans=ans;
            end case;
        end if;
    end if;

    return trim(ans);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `asociarBoletoNotaDebito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `asociarBoletoNotaDebito`(in in_id_nota_debito int 
, in in_id_boleto int, in in_id_cliente int , in in_id_counter int , in in_factor decimal(12,2) ,
in in_usuario_creador varchar(16),
out out_id_transacion int)
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
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_usd,
            monto_adeudado_usd,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert,
            id_usuario)
		VALUES (
			in_id_nota_debito,
            concat( coalesce(v_iata,''),'/','#',coalesce(v_numero,''),'/',coalesce(v_nombre_pasajero,'')),
            v_total_boleto,
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now(),
            in_usuario_creador
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
	ELSEIF v_moneda = 'B' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_bs,
            monto_adeudado_bs,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert,
            id_usuario)
		VALUES (
			in_id_nota_debito,
            concat(coalesce(v_iata,''),'/','#',coalesce(v_numero,''),'/',coalesce(v_nombre_pasajero,'')),
            v_total_boleto,
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now(),
            in_usuario_creador
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
    END IF ;
ELSEIF v_tipo_boleto = 'SA' OR v_tipo_boleto = 'AM' OR  v_tipo_boleto = 'MA' THEN
IF v_moneda = 'U' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_usd,
            monto_adeudado_usd,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert,
            id_usuario)
		VALUES (
			in_id_nota_debito,
			concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5),            
            v_total_boleto,
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now(),
            in_usuario_creador
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
	ELSEIF v_moneda = 'B' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_bs,
            monto_adeudado_bs,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert,
            id_usuario)
		VALUES (
			in_id_nota_debito,
            concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5),
            v_total_boleto,
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now(),
            in_usuario_creador
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
    END IF ;
END IF ;

CLOSE cur_boleto ;

UPDATE cnt_boleto 
	SET id_nota_debito_transaccion = v_id_transaccion ,
		estado 					   = 'P' ,
        id_cliente				   = in_id_cliente,
		id_promotor				   = in_id_counter,
        factor_cambiario		   = in_factor,
        id_nota_debito			   = in_id_nota_debito ,
        id_usuario_creador		   = in_usuario_creador
WHERE id_boleto = in_id_boleto ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarKardexCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexCliente`(in in_id_cliente int,
 in in_id_selected int)
BEGIN
-- VARIABLES
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
-- CURSORES

-- SElecciona todas las notas de debito que no esten anuladas
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
    where nd.id_cliente = in_id_cliente and nd.estado in ( 'E' , 'M') ;
	--  EMITIDO, MORA
    
-- CURSOR PAGO ANTICIPADO
-- selecciona todos los pagos que no esten anulados
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
    -- CON SALDO

    
declare continue handler for not found set finished = 1 ;

-- TEMPORA TABLE
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

-- NOTA DEBITO
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
            
            -- hacemos un cursor de la Nota de Debito para el ingreso de caja
			 CALL  generarKardexClienteNdIc(v_id_transaccion, in_id_selected);
            
             CALL  generarKardexClienteNdNc(v_id_transaccion, in_id_selected);
            
             CALL  generarKardexClienteNdPa(v_id_transaccion, in_id_selected);
            
	END LOOP loop_cur_nota_debito ;

close cur_nota_debito ;


-- PAGO ANTICIPADO BEGIN
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
            
            -- hacemos un cursor del pago anticipado para la acreditacion
			 CALL generarKardexClientePaAc (v_id_documento, in_id_selected);
			-- hacemos un cursor del pago anticipado para la devolucion
            -- CALL generarKardexClientePaDe(v_id_documento);
            
	END LOOP loop_cur_pago_anticipado ;

close cur_pago_anticipado ;


-- PAGO ANTICIPADO END

-- RESULTADO TABLA TEMPORAL

select * from tmp_kardex_cliente ;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarKardexClienteNdIc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClienteNdIc`(in in_id_nota_debito_transaccion int, 
in in_id_selected int)
BEGIN
-- VARIABLES
DECLARE v_id_documento		INT(11);
DECLARE v_glosa 			VARCHAR(128) ;
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
-- CURSORES
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
		where ndtr.id_nota_debito_transaccion = in_id_nota_debito_transaccion;
    
declare continue handler for not found set finished = 1 ;

-- NOTA DEBITO
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


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarKardexClienteNdNc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClienteNdNc`(in in_id_nota_debito_transaccion int,
in in_id_selected int)
BEGIN
-- VARIABLES
DECLARE v_id_documento		INT(11);
DECLARE v_glosa				VARCHAR(128);
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
-- CURSORES
DECLARE cur_nota_ingreso CURSOR FOR
	select  
		   ic.id_nota_credito id_documento
           , ndtr.descripcion glosa
           , date_format(ic.fecha_emision,'%d/%y/%M') fecha
           , null vencimiento
           , 'C'
           , 0 monto_debe_nac
           , ictr.monto_bs monto_haber_nac 
           , 0 saldo_nac
           , 0 monto_debe_ext
           , ictr.monto_usd monto_haber_ext
           , 0 saldo_ext
           , 'NC' tipo_documento
           , ic.estado
           , 'debito' v_row
           , case ic.id_nota_credito when in_id_selected then 'S' else 'N' end v_selected
		   from cnt_nota_credito_transaccion ictr 
	inner join cnt_nota_credito ic on ictr.id_nota_credito = ic.id_nota_credito
	inner join cnt_nota_debito_transaccion ndtr on ndtr.id_nota_debito_transaccion = ictr.id_nota_transaccion
		where ndtr.id_nota_debito_transaccion = in_id_nota_debito_transaccion;
    
declare continue handler for not found set finished = 1 ;

-- NOTA DEBITO
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
                         v_selected
			);
            
	END LOOP loop_cur_nota_ingreso ;

close cur_nota_ingreso ;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarKardexClienteNdPa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClienteNdPa`(in in_id_nota_debito_transaccion int,
in in_id_selected int)
BEGIN
-- VARIABLES
DECLARE v_id_documento		INT(11);
DECLARE v_glosa 			VARCHAR(128) ;
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
-- CURSORES
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
		where ndtr.id_nota_debito_transaccion = in_id_nota_debito_transaccion;
    
declare continue handler for not found set finished = 1 ;

-- NOTA DEBITO
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


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarKardexClientePaAc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClientePaAc`(in in_id_pago_anticipado int, 
in in_id_selected int)
BEGIN
-- VARIABLES
DECLARE v_id_documento		INT(11);
DECLARE v_glosa 			VARCHAR(128) ;
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
-- CURSORES
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

-- NOTA DEBITO
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


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `generarKardexClientePaDe` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `generarKardexClientePaDe`(in in_id_pago_anticipado int)
BEGIN
-- VARIABLES
DECLARE v_id_documento		INT(11);
DECLARE v_fecha 			VARCHAR(10);
DECLARE v_vencimiento 		VARCHAR(10) ;
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
-- CURSORES
DECLARE cur_pago_transaccion CURSOR FOR
	select 
		de.id_devolucion 		id_documento 
		, date_format(de.fecha_emision,'%d/%m/%Y')			fecha
		, null 						fecha_vencimiento	
		, de.tipo_devolucion				forma_pago
		, case de.moneda when 'B' then de.monto end monto_debe_nac
		, 0 						monto_haber_nac
		, 0 saldo_nac
		, case de.moneda when 'U' then de.monto end monto_debe_ext 
		, 0							monto_haber_ext
		, 0 saldo_ext
		, 'DE' tipo
		, de.estado
		, 'deposito'
        , case de.id_devolucion when in_id_selected then 'S' else 'N' end v_selected
	from cnt_devolucion de
	inner join cnt_pago_anticipado pa on pa.id_pago_anticipado = de.id_pago_anticipado
	where pa.id_pago_anticipado = in_id_pago_anticipado ;
    
declare continue handler for not found set finished = 1 ;

-- NOTA DEBITO
open cur_pago_transaccion ;

	loop_cur_pago_transaccion: LOOP
			fetch cur_pago_transaccion into
						 v_id_documento,
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


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateBoletoNotaDebito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
            monto_adeudado_usd   = v_total_boleto
		WHERE  id_nota_debito_transaccion = in_id_nota_transaccion;

	ELSEIF v_moneda = 'B' THEN
		UPDATE cnt_nota_debito_transaccion SET
            descripcion = concat(coalesce(v_iata,''),'/','#',coalesce(v_numero,''),'/',coalesce(v_nombre_pasajero,'')),
            monto_bs    = v_total_boleto,
            monto_adeudado_bs    = v_total_boleto
		WHERE  id_nota_debito_transaccion = in_id_nota_transaccion;
		
    END IF ;
ELSEIF v_tipo_boleto = 'SA' OR v_tipo_boleto = 'AM' OR  v_tipo_boleto = 'MA' THEN
IF v_moneda = 'U' THEN
		UPDATE cnt_nota_debito_transaccion SET
            descripcion	= concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5),
            monto_usd 	= v_total_boleto,
            monto_adeudado_usd 	= v_total_boleto
		WHERE id_nota_debito_transaccion = in_id_nota_transaccion;
        
	ELSEIF v_moneda = 'B' THEN
		UPDATE cnt_nota_debito_transaccion SET			
            descripcion	= concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5), 
            monto_bs 	= v_total_boleto,
            monto_adeudado_bs 	= v_total_boleto
		WHERE id_nota_debito_transaccion = in_id_nota_transaccion;
        
    END IF ;
END IF ;

CLOSE cur_boleto ;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateComprobanteContable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateComprobanteContable`(IN in_id_boleto INT)
BEGIN
DECLARE v_id_libro INT ;
DECLARE v_total_debe_nac DECIMAL(12,2);
DECLARE v_total_haber_nac DECIMAL(12,2);
DECLARE v_total_debe_ext DECIMAL(12,2);
DECLARE v_total_haber_ext DECIMAL(12,2);
DECLARE done_boleto INT DEFAULT FALSE;
DECLARE hay_transacciones INT DEFAULT FALSE;

-- Los Comprobantes que pertenezcan al boleto y que esten EMITIDOS
DECLARE cur_libros_boleto CURSOR FOR
	SELECT DISTINCT id_libro 
    FROM cnt_asiento_contable 
    WHERE id_boleto = in_id_boleto 
    ORDER BY id_libro;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_boleto = TRUE;

OPEN cur_libros_boleto;

LOOP_BOLETO : LOOP 
	
	FETCH cur_libros_boleto into v_id_libro;
    -- INSERT INTO tmp_output values(concat('INGRESO LOOP BOLETO:', v_id_libro));
	BLOCK_ASIENTOS : BEGIN
		
		DECLARE done_asientos INT DEFAULT FALSE;
        DECLARE cur_asientos CURSOR FOR
		SELECT 
			SUM(monto_debe_nac) total_debe_nac ,
			SUM(monto_haber_nac) total_haber_nac ,
			SUM(monto_debe_ext) total_debe_ext ,
			SUM(monto_haber_ext) total_debe_ext 
		FROM cnt_asiento_contable ac
		INNER JOIN cnt_comprobante_contable cc on cc.id_libro = ac.id_libro
		WHERE ac.estado = 'E' and ac.id_libro = v_id_libro
		GROUP BY cc.tipo,cc.id_libro;
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_asientos = TRUE;
        
		IF done_boleto THEN
		LEAVE LOOP_BOLETO ;
		END IF;
        
        -- INSERT INTO tmp_output values('INGRESO BLOCK_ASIENTOS');	
        
        OPEN cur_asientos;
        LOOP_ASIENTOS :LOOP
			-- INSERT INTO tmp_output values('LOOP_ASIENTOS');	
			FETCH cur_asientos INTO v_total_debe_nac, v_total_haber_nac, v_total_debe_ext, v_total_haber_ext;
            
			-- INSERT INTO tmp_output values(concat('v_total_debe_nac'	, coalesce(v_total_debe_nac,'null')));
            -- INSERT INTO tmp_output values(concat('v_total_haber_nac', coalesce(v_total_haber_nac,'null') ));
            -- INSERT INTO tmp_output values(concat('v_total_debe_ext'	, coalesce(v_total_debe_ext,'null')));
            -- INSERT INTO tmp_output values(concat('v_total_haber_ext', coalesce(v_total_haber_ext,'null')));
            
			IF v_total_debe_nac is null AND v_total_haber_nac is null AND v_total_debe_ext is null AND v_total_haber_ext is null THEN
				-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
				UPDATE cnt_comprobante_contable 
					SET total_debe_nac 	= v_total_debe_nac,
						total_haber_nac = v_total_haber_nac,
						total_debe_ext 	= v_total_debe_ext,
						total_haber_ext = v_total_haber_ext,
						estado = 'A'
					WHERE id_libro = v_id_libro ;
			else
				-- INSERT INTO tmp_output values('UPDATE VALORES');	
				UPDATE cnt_comprobante_contable 
					SET total_debe_nac 	= v_total_debe_nac,
						total_haber_nac = v_total_haber_nac,
						total_debe_ext 	= v_total_debe_ext,
						total_haber_ext = v_total_haber_ext						
					WHERE id_libro = v_id_libro ;
            END IF ;
                    
			IF done_asientos THEN
			LEAVE LOOP_ASIENTOS ;
			END IF;
        
        END LOOP LOOP_ASIENTOS;
        CLOSE cur_asientos;
        
    END BLOCK_ASIENTOS;

END LOOP LOOP_BOLETO;
ClOSE cur_libros_boleto ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateComprobanteContableAnularTransaccion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateComprobanteContableAnularTransaccion`(in in_id_libro int)
BEGIN
DECLARE v_total_debe_nac DECIMAL(12,2);
DECLARE v_total_debe_ext DECIMAL(12,2);
DECLARE v_total_haber_nac DECIMAL(12,2);
DECLARE v_total_haber_ext DECIMAL(12,2);

-- Los Comprobantes que pertenezcan al boleto y que esten EMITIDOS o PENDIENTES
DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto_debe_nac) total_debe_nac , 
    sum(monto_debe_ext) total_debe_ext ,
    sum(monto_haber_nac) total_haber_nac ,
	sum(monto_haber_ext) total_haber_ext 
FROM cnt_asiento_contable ac
WHERE ac.estado in ('E','P') and ac.id_libro= in_id_libro;

OPEN cur_transacciones;
	FETCH cur_transacciones INTO v_total_debe_nac, v_total_debe_ext,
		v_total_haber_nac, v_total_haber_ext;

	IF v_total_debe_nac is null AND v_total_debe_ext is null
		AND v_total_haber_nac is null AND v_total_haber_ext is null
    THEN
		-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
		UPDATE cnt_comprobante_contable 
			SET total_haber_nac = v_total_haber_nac,
				total_haber_ext = v_total_haber_ext,						
                total_debe_nac = v_total_debe_nac,
                total_debe_ext = v_total_debe_ext,
				estado = 'A'
			WHERE id_libro = in_id_libro;
	ELSE
		-- INSERT INTO tmp_output values('UPDATE VALORES');	
		UPDATE cnt_comprobante_contable 
			SET total_haber_nac = v_total_haber_nac,
				total_haber_ext = v_total_haber_ext,						
                total_debe_nac = v_total_debe_nac,
                total_debe_ext = v_total_debe_ext
			WHERE id_libro = in_id_libro ;
	END IF ;

CLOSE cur_transacciones ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateIngresoCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateIngresoCaja`(IN in_id_nota_transaccion INT)
BEGIN

DECLARE v_id_ingreso INT ;
DECLARE v_id_ingreso_transaccion INT ;

DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE done_ingresos INT DEFAULT FALSE;
DECLARE hay_transacciones INT DEFAULT FALSE;

-- Los id de los ingresos
DECLARE cur_ingresos CURSOR FOR
        SELECT id_ingreso_caja
        FROM cnt_ingreso_transaccion
        WHERE id_nota_transaccion = in_id_nota_transaccion ;
        
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_ingresos = TRUE;
        
-- actualizamos la transaccion del ingreso de caja

UPDATE cnt_ingreso_transaccion
SET estado = 'A' 
WHERE id_nota_transaccion = in_id_nota_transaccion ;

INSERT INTO tmp_output values(concat('INGRESO LOOP Transaccion:', in_id_nota_transaccion));
OPEN cur_ingresos;

LOOP_INGRESOS : LOOP 
	
	FETCH cur_ingresos into v_id_ingreso;
    INSERT INTO tmp_output values(concat('updateIngresoCaja:', v_id_ingreso));
    
    IF done_ingresos THEN
		LEAVE LOOP_INGRESOS ;
		END IF;
        
	BLOCK_INGRESOS : BEGIN		
		DECLARE done_transacciones INT DEFAULT FALSE;
        DECLARE cur_transacciones CURSOR FOR
        SELECT 			
            sum(monto_bs) 	monto_total_bs , 
            sum(monto_usd)	monto_total_usd
		FROM cnt_ingreso_transaccion it
			INNER JOIN cnt_ingreso_caja ic on ic.id_ingreso_caja= it.id_ingreso_caja
		WHERE it.estado = 'E' and it.id_ingreso_caja = v_id_ingreso;
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_transacciones = TRUE;
        
        OPEN cur_transacciones;
        LOOP_TRANSACCIONES :LOOP
			FETCH cur_transacciones INTO v_total_bs, v_total_usd;
            INSERT INTO tmp_output values(concat('INGRESO LOOP v_total_bs:', v_total_bs, ' v_total_usd:' , v_total_usd));
            
			IF v_total_bs is null AND v_total_usd is null THEN
				-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
				UPDATE cnt_ingreso_caja
					SET monto_abonado_bs 	= v_total_bs,
						monto_abonado_usd = v_total_usd,						
						estado = 'A'
					WHERE id_ingreso_caja = v_id_ingreso;
			else
				-- INSERT INTO tmp_output values('UPDATE VALORES');	
				UPDATE cnt_ingreso_caja 
					SET monto_abonado_bs 	= v_total_bs,
						monto_abonado_usd = v_total_usd						
					WHERE id_ingreso_caja = v_id_ingreso;
            END IF ;
                    
			IF done_transacciones THEN
			LEAVE LOOP_TRANSACCIONES ;
			END IF;
        
        END LOOP LOOP_TRANSACCIONES;
        CLOSE cur_transacciones;        
    END BLOCK_INGRESOS;

END LOOP LOOP_INGRESOS;

ClOSE cur_ingresos ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosAdeudadosNotaDebito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosAdeudadosNotaDebito`(IN in_id_nota_debito INT)
BEGIN
DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE cur_transacciones CURSOR FOR
	SELECT 			
		sum(nt.monto_adeudado_bs) 	monto_total_bs , 
		sum(nt.monto_adeudado_usd)	monto_total_usd 
	FROM cnt_nota_debito_transaccion nt
		INNER JOIN cnt_nota_debito nd on nd.id_nota_debito= nt.id_nota_debito
	WHERE nt.estado = 'E' and nd.id_nota_debito = in_id_nota_debito;
    
OPEN cur_transacciones ;

	FETCH cur_transacciones INTO
		v_total_bs, v_total_usd;
        
	UPDATE cnt_nota_debito SET 
		monto_adeudado_bs  = v_total_bs,
        monto_adeudado_usd = v_total_usd
	WHERE id_nota_debito = in_id_nota_debito ;

CLOSE cur_transacciones ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosComprobanteContableFromAsientos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosComprobanteContableFromAsientos`(IN in_id_libro INT)
BEGIN
DECLARE v_total_debe_nac DECIMAL(12,2);
DECLARE v_total_haber_nac DECIMAL(12,2);
DECLARE v_total_debe_ext DECIMAL(12,2);
DECLARE v_total_haber_ext DECIMAL(12,2);

DECLARE cur_montos CURSOR for
select sum( monto_debe_nac) total_debe_nac, sum(monto_haber_nac) total_haber_nac,
sum(monto_debe_ext) total_debe_ext, sum(monto_haber_ext) total_haber_ext
from cnt_asiento_contable where id_libro = in_id_libro and estado = 'E' ;

OPEN cur_montos ;

FETCH cur_montos into v_total_debe_nac, v_total_haber_nac, v_total_debe_ext, v_total_haber_ext;

UPDATE cnt_comprobante_contable 
	SET total_debe_nac  = v_total_debe_nac,
		total_haber_nac = v_total_haber_nac,
        total_debe_ext  = v_total_debe_ext,
        total_haber_ext = v_total_haber_ext
	WHERE id_libro = in_id_libro ;

close cur_montos ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosIngresoCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosIngresoCaja`(IN in_id_ingreso_caja INT)
BEGIN
DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto_bs) 	monto_total_bs , 
	sum(monto_usd)	monto_total_usd
FROM cnt_ingreso_transaccion it
	INNER JOIN cnt_ingreso_caja ic on ic.id_ingreso_caja= it.id_ingreso_caja
WHERE it.estado in( 'E', 'C','P') and it.id_ingreso_caja = in_id_ingreso_caja;

OPEN cur_transacciones ;

FETCH cur_transacciones INTO v_total_bs, v_total_usd ;


	UPDATE cnt_ingreso_caja SET
		monto_abonado_bs = v_total_bs,
        monto_abonado_usd = v_total_usd
	WHERE id_ingreso_caja = in_id_ingreso_caja;


CLOSE cur_transacciones ;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosIngresoCajaFromFinalizar` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosIngresoCajaFromFinalizar`(IN in_id_ingreso INT)
BEGIN
DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);

DECLARE cur_montos CURSOR for
select sum( monto_bs) total_bs, sum(monto_usd) total_usd
from cnt_ingreso_transaccion where id_ingreso_caja = in_id_ingreso and estado = 'E' ;

OPEN cur_montos ;

FETCH cur_montos into v_total_bs, v_total_usd ;

UPDATE cnt_ingreso_caja 
SET 	monto_abonado_bs 	= v_total_bs,
		monto_abonado_usd	= v_total_usd
WHERE id_ingreso_caja = in_id_ingreso;

close cur_montos ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosNotaCredito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosNotaCredito`(IN in_id_nota_credito INT)
BEGIN
DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto_bs) 	monto_total_bs , 
	sum(monto_usd)	monto_total_usd
FROM cnt_nota_credito_transaccion it
	INNER JOIN cnt_nota_credito nc on nc.id_nota_credito = it.id_nota_credito
WHERE it.estado in( 'E', 'C','P') and it.id_nota_credito = in_id_nota_credito;

OPEN cur_transacciones ;

FETCH cur_transacciones INTO v_total_bs, v_total_usd ;


	UPDATE cnt_nota_credito SET
		monto_abonado_bs = v_total_bs,
        monto_abonado_usd = v_total_usd
	WHERE id_nota_credito = in_id_nota_credito;


CLOSE cur_transacciones ;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosNotaDebitoEmitidos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosNotaDebitoEmitidos`(in in_id_nota_debito INT)
BEGIN

DECLARE v_monto_bs 	DECIMAL(12,2);
DECLARE v_monto_usd DECIMAL(12,2);
DECLARE v_monto_adeudado_bs dECIMAL(12,2);
DECLARE v_monto_adeudado_usd dECIMAL(12,2);
DECLARE done INT DEFAULT FALSE;
DECLARE cur_transaccion CURSOR FOR
select coalesce(sum(monto_bs),0)  monto_bs, 
	   coalesce(sum(monto_usd),0) monto_usd ,
	   coalesce(sum(monto_adeudado_bs),0) monto_adeudado_bs, 
       coalesce(sum(monto_adeudado_usd),0) monto_adeudado_usd
	from cnt_nota_debito_transaccion
	where id_nota_debito= in_id_nota_debito and estado in('E','M','D');
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN cur_transaccion ;

FETCH cur_transaccion INTO v_monto_bs, v_monto_usd, v_monto_adeudado_bs, v_monto_adeudado_usd;
-- INSERT INTO tmp_output values( concat(done,  ' monto_Bs :', coalesce (v_monto_bs,'') ) );
-- IF done THEN
	IF v_monto_adeudado_bs = 0 and v_monto_adeudado_usd = 0 THEN
		UPDATE cnt_nota_debito 
		SET monto_total_bs = v_monto_bs,
			monto_total_usd = v_monto_usd,
            monto_adeudado_bs= v_monto_adeudado_bs,
            monto_adeudado_usd = v_monto_adeudado_usd,
			estado = 'D'  -- CANCELADO
		WHERE id_nota_debito = in_id_nota_debito ;
    ELSE
		UPDATE cnt_nota_debito 
		SET monto_total_bs = v_monto_bs,
			monto_total_usd = v_monto_usd,
            monto_adeudado_bs= v_monto_adeudado_bs,
            monto_adeudado_usd = v_monto_adeudado_usd
		WHERE id_nota_debito = in_id_nota_debito ;
    END IF;
-- END IF ;
CLOSE cur_transaccion ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosNotaDebitoEnPendiente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosNotaDebitoEnPendiente`(in in_id_nota_debito INT)
BEGIN

DECLARE v_monto_bs 	DECIMAL(12,2);
DECLARE v_monto_usd DECIMAL(12,2);
DECLARE v_monto_adeudado_bs DECIMAL(12,2);
DECLARE v_monto_adeudado_usd DECIMAL(12,2);

DECLARE done INT DEFAULT FALSE;
DECLARE cur_transaccion CURSOR FOR
	select 	sum(monto_bs) monto_bs, 
			sum(monto_usd)monto_usd,
            sum(monto_adeudado_bs) monto_adeudado_bs,
            sum(monto_adeudado_usd) monto_adeudado_usd
	from cnt_nota_debito_transaccion
	where id_nota_debito= in_id_nota_debito and estado in('P');
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN cur_transaccion ;

FETCH cur_transaccion INTO v_monto_bs, v_monto_usd, v_monto_adeudado_bs, v_monto_adeudado_usd;
-- INSERT INTO tmp_output values( concat(done,  ' monto_Bs :', coalesce (v_monto_bs,'') ) );
-- IF done THEN
	UPDATE cnt_nota_debito 
	SET monto_total_bs 		= v_monto_bs,
		monto_total_usd 	= v_monto_usd,
        monto_adeudado_bs 	= v_monto_adeudado_bs,
        monto_adeudado_usd	= v_monto_adeudado_usd
	WHERE id_nota_debito = in_id_nota_debito ;
-- END IF ;
CLOSE cur_transaccion ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosPagoAnticipado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosPagoAnticipado`(IN in_id_pago_anticipado INT)
BEGIN
DECLARE v_total_acreditado DECIMAL(12,2);
DECLARE v_monto_anticipado DECIMAL(12,2);
DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto) 	monto_total, monto_anticipado
FROM cnt_pago_anticipado_transaccion it
	INNER JOIN cnt_pago_anticipado nc on nc.id_pago_anticipado = it.id_pago_anticipado
WHERE it.estado in( 'E', 'C','P') and it.id_pago_anticipado = in_id_pago_anticipado;

OPEN cur_transacciones ;

FETCH cur_transacciones INTO v_total_acreditado, v_monto_anticipado ;

IF v_total_acreditado = v_monto_anticipado THEN
-- SIN_SALDO  S
	UPDATE cnt_pago_anticipado SET
		monto_total_acreditado = v_total_acreditado,
        estado = 'S'
	WHERE id_pago_anticipado = in_id_pago_anticipado;
else
	UPDATE cnt_pago_anticipado SET
		monto_total_acreditado  = v_total_acreditado
	WHERE id_pago_anticipado = in_id_pago_anticipado;
END IF ;

CLOSE cur_transacciones ;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateNotaDebito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateNotaDebito`(IN in_id_boleto INT)
BEGIN
DECLARE v_id_nota_debito INT ;
DECLARE v_id_nota_debito_transaccion INT ;

DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE done_nota_debito INT DEFAULT FALSE;
DECLARE hay_transacciones INT DEFAULT FALSE;

-- Los Comprobantes que pertenezcan al boleto y que esten EMITIDOS
DECLARE cur_nota_debito CURSOR FOR
SELECT DISTINCT id_nota_debito, id_nota_debito_transaccion
    FROM cnt_nota_debito_transaccion 
    WHERE id_boleto = in_id_boleto
    ORDER BY id_nota_debito;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_nota_debito = TRUE;

OPEN cur_nota_debito;

LOOP_NOTA_DEBITO : LOOP 
	
	FETCH cur_nota_debito into v_id_nota_debito, v_id_nota_debito_transaccion;
    -- INSERT INTO tmp_output values(concat('INGRESO LOOP BOLETO:', v_id_libro));
    IF done_nota_debito THEN
		LEAVE LOOP_NOTA_DEBITO ;
		END IF;
        
	BLOCK_NOTAS : BEGIN		
		DECLARE done_transacciones INT DEFAULT FALSE;
        DECLARE cur_transacciones CURSOR FOR
		SELECT 			
            sum(monto_bs) 	monto_total_bs , 
            sum(monto_usd)	monto_total_usd
		FROM cnt_nota_debito_transaccion nt
			INNER JOIN cnt_nota_debito nd on nd.id_nota_debito= nt.id_nota_debito
		WHERE nt.estado = 'E' and nd.id_nota_debito = v_id_nota_debito;
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_transacciones = TRUE;
        
        OPEN cur_transacciones;
        LOOP_TRANSACCIONES :LOOP
			FETCH cur_transacciones INTO v_total_bs, v_total_usd;
            
			IF v_total_bs is null AND v_total_usd is null THEN
				-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
				UPDATE cnt_nota_debito 
					SET monto_total_bs 	= v_total_bs,
						monto_total_usd = v_total_usd,						
						estado = 'A'
					WHERE id_nota_debito = v_id_nota_debito;
			else
				-- INSERT INTO tmp_output values('UPDATE VALORES');	
				UPDATE cnt_nota_debito 
					SET monto_total_bs 	= v_total_bs,
						monto_total_usd = v_total_usd						
					WHERE id_nota_debito = v_id_nota_debito ;
            END IF ;
                    
			IF done_transacciones THEN
			LEAVE LOOP_TRANSACCIONES ;
			END IF;
        
        END LOOP LOOP_TRANSACCIONES;
        CLOSE cur_transacciones;        
    END BLOCK_NOTAS;
    
    /* Para los ingresos*/
    INSERT INTO tmp_output values(concat('CALL updateIngresoCaja:'));
   CALL updateIngresoCaja (v_id_nota_debito_transaccion) ;
   
END LOOP LOOP_NOTA_DEBITO;
ClOSE cur_nota_debito ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateNotaDebitoAnularTransaccion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateNotaDebitoAnularTransaccion`(in in_id_nota_debito int)
BEGIN

DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);

-- Los Comprobantes que pertenezcan al boleto y que esten EMITIDOS o PENDIENTES
DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto_bs) 	monto_total_bs , 
	sum(monto_usd)	monto_total_usd
FROM cnt_nota_debito_transaccion nt
	INNER JOIN cnt_nota_debito nd on nd.id_nota_debito= nt.id_nota_debito
WHERE nt.estado in ('E','P') and nd.id_nota_debito = in_id_nota_debito;
	OPEN cur_transacciones;
	FETCH cur_transacciones INTO v_total_bs, v_total_usd;

	IF v_total_bs is null AND v_total_usd is null THEN
		-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
		UPDATE cnt_nota_debito 
			SET monto_total_bs 	= v_total_bs,
				monto_total_usd = v_total_usd					
			WHERE id_nota_debito = in_id_nota_debito;
	ELSE
		-- INSERT INTO tmp_output values('UPDATE VALORES');	
		UPDATE cnt_nota_debito 
			SET monto_total_bs 	= v_total_bs,
				monto_total_usd = v_total_usd						
			WHERE id_nota_debito = in_id_nota_debito ;
	END IF ;

CLOSE cur_transacciones ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-09  9:32:22

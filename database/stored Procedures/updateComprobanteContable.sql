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
END
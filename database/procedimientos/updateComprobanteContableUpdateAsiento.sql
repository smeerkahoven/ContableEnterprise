CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateComprobanteContableUpdateAsiento`(IN in_id_libro INT)
BEGIN
DECLARE v_total_debe_nac DECIMAL(12,2);
DECLARE v_total_haber_nac DECIMAL(12,2);
DECLARE v_total_debe_ext DECIMAL(12,2);
DECLARE v_total_haber_ext DECIMAL(12,2);

DECLARE done_asientos INT DEFAULT FALSE;

DECLARE cur_asientos CURSOR FOR
SELECT 
	SUM(monto_debe_nac) total_debe_nac ,
	SUM(monto_haber_nac) total_haber_nac ,
	SUM(monto_debe_ext) total_debe_ext ,
	SUM(monto_haber_ext) total_debe_ext 
FROM cnt_asiento_contable ac
INNER JOIN cnt_comprobante_contable cc on cc.id_libro = ac.id_libro
WHERE ac.estado = 'E' and ac.id_libro = in_id_libro
GROUP BY cc.tipo,cc.id_libro;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_asientos = TRUE;

OPEN cur_asientos;
LOOP_ASIENTOS :LOOP
	
	FETCH cur_asientos INTO v_total_debe_nac, v_total_haber_nac, v_total_debe_ext, v_total_haber_ext;

	
	IF v_total_debe_nac is null AND v_total_haber_nac is null AND v_total_debe_ext is null AND v_total_haber_ext is null THEN
		-- anulado
		UPDATE cnt_comprobante_contable 
			SET total_debe_nac 	= v_total_debe_nac,
				total_haber_nac = v_total_haber_nac,
				total_debe_ext 	= v_total_debe_ext,
				total_haber_ext = v_total_haber_ext,
				estado = 'A'
			WHERE id_libro = v_id_libro ;
	else
		
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
	
END
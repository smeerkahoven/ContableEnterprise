USE `db_travel`;
DROP procedure IF EXISTS `updateMontosComprobanteContableFromAsientos`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosComprobanteContableFromAsientos`(IN in_id_libro INT)
BEGIN
DECLARE v_total_debe_nac DECIMAL(12,2);
DECLARE v_total_haber_nac DECIMAL(12,2);
DECLARE v_total_debe_ext DECIMAL(12,2);
DECLARE v_total_haber_ext DECIMAL(12,2);

DECLARE cur_montos CURSOR for
select sum( monto_debe_nac) total_debe_nac, sum(monto_haber_nac) total_haber_nac,
sum(monto_debe_ext) total_debe_ext, sum(monto_haber_ext) total_haber_ext
from cnt_asiento_contable where id_libro = in_id_libro and estado in ('E', 'P') ;

OPEN cur_montos ;

FETCH cur_montos into v_total_debe_nac, v_total_haber_nac, v_total_debe_ext, v_total_haber_ext;

UPDATE cnt_comprobante_contable 
	SET total_debe_nac  = v_total_debe_nac,
		total_haber_nac = v_total_haber_nac,
        total_debe_ext  = v_total_debe_ext,
        total_haber_ext = v_total_haber_ext
	WHERE id_libro = in_id_libro ;

close cur_montos ;

END$$

DELIMITER ;


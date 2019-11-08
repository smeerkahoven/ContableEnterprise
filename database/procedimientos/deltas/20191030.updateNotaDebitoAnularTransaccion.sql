USE `db_travel`;
DROP procedure IF EXISTS `updateNotaDebitoAnularTransaccion`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateNotaDebitoAnularTransaccion`(in in_id_nota_debito int)
BEGIN

DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE v_monto_adeudado_bs DECIMAL(12,2);
DECLARE v_monto_adeudado_usd DECIMAL(12,2);

DECLARE cur_transacciones CURSOR FOR
SELECT 			
	sum(monto_bs) 	monto_total_bs 	, 
	sum(monto_usd)	monto_total_usd ,
    sum(nt.monto_adeudado_bs) monto_adeudado_bs,
    sum(nt.monto_adeudado_usd) monto_adeudado_usd
FROM cnt_nota_debito_transaccion nt
	INNER JOIN cnt_nota_debito nd on nd.id_nota_debito= nt.id_nota_debito
WHERE nt.estado in ('E','P') and nd.id_nota_debito = in_id_nota_debito;
	OPEN cur_transacciones;
	FETCH cur_transacciones INTO v_total_bs, v_total_usd, v_monto_adeudado_bs, v_monto_adeudado_usd;

	IF v_total_bs is null AND v_total_usd is null THEN
		
		UPDATE cnt_nota_debito 
			SET monto_total_bs 	= v_total_bs,
				monto_total_usd = v_total_usd,
                monto_adeudado_bs = v_monto_adeudado_bs,
                monto_adeudado_usd = v_monto_adeudado_usd
			WHERE id_nota_debito = in_id_nota_debito;
	ELSE
		
		UPDATE cnt_nota_debito 
			SET monto_total_bs 	= v_total_bs,
				monto_total_usd = v_total_usd,
                monto_adeudado_bs = v_monto_adeudado_bs,
                monto_adeudado_usd = v_monto_adeudado_usd
			WHERE id_nota_debito = in_id_nota_debito ;
	END IF ;

CLOSE cur_transacciones ;
END$$

DELIMITER ;


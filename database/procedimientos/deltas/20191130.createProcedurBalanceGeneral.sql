USE `db_travel`;
DROP procedure IF EXISTS `balanceGeneral`;

DELIMITER $$
USE `db_travel`$$
CREATE DEFINER=`web_contabilidad`@`%` PROCEDURE `balanceGeneral`(
	in in_start_date date,
    in in_end_date date,
    in in_moneda varchar(1),
    in in_id_empresa int
)
BEGIN  

call sumasYsaldosNivel(in_start_date,in_end_date, in_moneda, in_id_empresa, 5);

select v_id_plan_cuenta, v_nro_plan_cuenta, v_nro_plan_cuenta_padre,
v_nivel, pc.id_cuenta_regularizacion, v_cuenta, v_saldo_debe, v_saldo_haber
  from tmp_sumas_saldos tmp
inner join cnt_plan_cuentas pc on pc.id_plan_cuentas = tmp.v_id_plan_cuenta
where v_nro_plan_cuenta like '1%' or v_nro_plan_cuenta like '2%' ; 

END$$

DELIMITER ;


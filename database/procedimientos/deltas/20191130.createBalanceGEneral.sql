USE `db_travel`;
DROP procedure IF EXISTS `balanceGeneralUtilidadGestion_SYNTAX_ERROR`;

DELIMITER $$
USE `db_travel`$$
CREATE PROCEDURE `balanceGeneralUtilidadGestion` ()
BEGIN

	DECLARE v_id_plan_cuenta_gestion 	INT(11) ;
    DECLARE v_nro_plan_cuenta_gestion	BIGINT(20) ;
    DECLARE v_cuenta_gestion			VARCHAR(64);
    DECLARE v_saldo						DECIMAL(16,2);
	DECLARE v_saldo_monto				DECIMAL(16,2);
    DECLARE v_nivel_gestion				INT(11);
    DECLARE finished 					INTEGER DEFAULT 0 ;
    
    ---
    
    DECLARE finished_1 			INTEGER DEFAULT 0 ;

	DECLARE vx_id_plan_cuenta			INT(11) ;
	DECLARE vx_cuenta					VARCHAR(40) ;
	DECLARE vx_nro_plan_cuenta			BIGINT(20) ;
	DECLARE vx_nro_plan_cuenta_padre	BIGINT(20)  ;
	DECLARE vx_saldo_debe				DECIMAL(16,2) ;
	DECLARE vx_saldo_haber				DECIMAL(16,2) ;
	DECLARE vx_nivel					INT ;

	declare cur_sumas_saldos cursor for
	select 
      v_id_plan_cuentas
	, v_cuenta
    , v_nro_plan_cuenta
    , v_nro_plan_cuenta_padre
    , v_aldo_debe
    , v_saldo_haber
    , v_nivel
    from tmp_sumas_saldos pc 
 
	declare continue handler for not found set finished_1 = 1 ;
    
    DECLARE cur_gestion cursor for
	SELECT 
		id_plan_cuentas, 
        nro_plan_cuenta, 
        cuenta, 
        tmp.v_saldo,
        tmp.v_saldo_monto,
        pc.nivel
	from cnt_plan_cuentas pc 
	inner join tb_contabilidad_boletaje cb on cb.utilidad_gestion = pc.id_plan_cuentas
    inner join tmp_estados_resultados tmp on tmp.v_id_plan_cuenta = cb.utilidad_gestion
    
	declare continue handler for not found set finished = 1 ;
    
    open cur_sumas_saldos ;
    
    close cur_sumas_saldos ;
		loop_cur_sumas_saldos : loop
			fetch cur_sumas_saldos into 
				vx_id_plan_cuenta ,
				vx_cuenta ,
				vx_nro_plan_cuenta ,
				vx_nro_plan_cuenta_padre ,
				vx_saldo_debe ,
				vx_saldo_haber ,
				vx_nivel ;
                
                insert into tmp_balance_general (
					v_id_plan_cuenta,
					v_cuenta ,
					v_nro_plan_cuenta,
					v_nro_plan_cuenta_padre	,
					v_saldo_debe ,
					v_saldo_haber ,
					v_nivel	)
				values (
					vx_id_plan_cuenta,
					vx_cuenta ,
					vx_nro_plan_cuenta,
					vx_nro_plan_cuenta_padre	,
					vx_saldo_debe ,
					vx_saldo_haber ,
					vx_nivel	
                )
                
		end loop;

	open cur_gestion ;
		loop_cur_gestion : loop
        
			fetch cur_gestion into 
				v_id_plan_cuenta_gestion,
                v_nro_plan_cuenta_gestion,
				v_cuenta_gestion,
				v_saldo 
                v_saldo_monto,
                v_nivel_gestion
                ;					
        
			if finished = 1 then 
				leave loop_cur_gestion ;
            end if ;        
        end loop ;        
    close cur_gestion ;

END$$

DELIMITER ;


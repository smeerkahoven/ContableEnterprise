CREATE DEFINER=`web_contabilidad`@`%` PROCEDURE `sumasYsaldosNivel3`(
 in in_start_date date 
 , in in_end_date date 
 , in in_moneda varchar(1)
 , in in_id_empresa int
 , in in_nro_plan_cuenta_padre bigint(20)
 , in in_nivel int
 )
BEGIN

DECLARE finished 			INTEGER DEFAULT 0 ;

DECLARE v_id_plan_cuenta		INT(11) ;
DECLARE v_cuenta				VARCHAR(40) ;
DECLARE v_nro_plan_cuenta		BIGINT(20) ;
DECLARE v_nro_plan_cuenta_padre	BIGINT(20)  ;
DECLARE v_suma_debe				DECIMAL(16,2) ;
DECLARE v_suma_haber			DECIMAL(16,2) ;
DECLARE v_saldo_debe			DECIMAL(16,2) ;
DECLARE v_saldo_haber			DECIMAL(16,2) ;
DECLARE v_nivel					INT ;
     
declare cur_sumas_saldos cursor for
	select 
      pc.id_plan_cuentas
	, pc.cuenta
    , pc.nro_plan_cuenta
    , pc.nro_plan_cuenta_padre
    , 0 suma_debe
    , 0 suma_haber
    , 0 saldo_debe
    , 0 saldo_haber
    , pc.nivel
    from cnt_plan_cuentas pc 
    where pc.id_empresa = in_id_empresa 
    and pc.nro_plan_cuenta_padre = in_nro_plan_cuenta_padre
    ;
    
declare continue handler for not found set finished = 1 ;

open cur_sumas_saldos ;

	loop_cur_sumas_saldos : loop 
		fetch cur_sumas_saldos into 
			v_id_plan_cuenta
            , v_cuenta
            , v_nro_plan_cuenta
            , v_nro_plan_cuenta_padre
            , v_suma_debe
            , v_suma_haber
            , v_saldo_debe
            , v_saldo_haber
            , v_nivel ;
            
             if finished = 1 then 
             leave loop_cur_sumas_saldos ;
             end if ;

			-- if v_nivel = 5 then 
				/*call sumasYsaldosNivel5insert (in_start_date
								 , in_end_date
                                 , in_moneda
                                 , in_id_empresa
                                 , v_id_plan_cuenta) ;		*/
            /*else
			begin*/
				insert into tmp_sumas_saldos (
					   v_id_plan_cuenta
					 , v_cuenta
					 , v_nro_plan_cuenta
					 , v_nro_plan_cuenta_padre
					 , v_suma_debe
					 , v_suma_haber
					 , v_saldo_debe
					 , v_saldo_haber
					 , v_nivel
				)values (
					v_id_plan_cuenta
					 , v_cuenta
					 , v_nro_plan_cuenta
					 , v_nro_plan_cuenta_padre
					 , v_suma_debe
					 , v_suma_haber
					 , v_saldo_debe
					 , v_saldo_haber
					 , v_nivel
				) ;
				
				
				call sumasYsaldosNivel4(in_start_date
									 , in_end_date
									 , in_moneda
									 , in_id_empresa
									 , v_nro_plan_cuenta
									 , v_nivel+1) ;
									 
				-- hay q llamar a actualizar la tabka tmp
				call updateSumasYsaldosNivelActual ( v_id_plan_cuenta, v_nro_plan_cuenta );
            /*end ;*/
			-- end if ;	
	end loop loop_cur_sumas_saldos ;
	
close cur_sumas_saldos ;

END
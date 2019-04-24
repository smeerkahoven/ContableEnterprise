CREATE DEFINER=`web_contabilidad`@`%` PROCEDURE `updateSumasYsaldosNivelActual`(
in in_id_plan_cuenta int ,
in in_nro_plan_cuenta bigint(20)
)
BEGIN

DECLARE finished 			INTEGER DEFAULT 0 ;

DECLARE vx_suma_debe			DECIMAL(16,2) ;
DECLARE vx_suma_haber			DECIMAL(16,2) ;
DECLARE vx_saldo_debe			DECIMAL(16,2) ;
DECLARE vx_saldo_haber			DECIMAL(16,2) ;
     
declare cur_sumas_saldos cursor for

select 
	coalesce(A.suma_debe,0)
  , coalesce(A.suma_haber,0)
  , if (A.suma_debe > A.suma_haber , A.suma_debe - A.suma_haber, 0 ) saldo_debe
  , if (A.suma_debe < A.suma_haber , A.suma_haber - A.suma_debe , 0 ) saldo_haber
FROM (
	select
		   sum(coalesce(v_suma_debe,0)) suma_debe
		 , sum(coalesce(v_suma_haber,0)) suma_haber
		 , sum(coalesce(v_saldo_debe,0)) saldo_debe
		 , sum(coalesce(v_saldo_haber,0)) saldo_haber
		from tmp_sumas_saldos
		where v_nro_plan_cuenta_padre =  in_nro_plan_cuenta
) A;
    
declare continue handler for not found set finished = 1 ;

open cur_sumas_saldos ;

	loop_cur_sumas_saldos : loop 
		fetch cur_sumas_saldos into 
              vx_suma_debe
            , vx_suma_haber
            , vx_saldo_debe
            , vx_saldo_haber;
            
            if finished = 1 then 
            leave loop_cur_sumas_saldos ;
            end if ;

			update tmp_sumas_saldos 
            set   v_suma_debe   = vx_suma_debe
                , v_suma_haber  = vx_suma_haber
                , v_saldo_debe  = vx_saldo_debe
                , v_saldo_haber = vx_saldo_haber
			where v_id_plan_cuenta = in_id_plan_cuenta ;

	end loop loop_cur_sumas_saldos ;
	
close cur_sumas_saldos ;

END
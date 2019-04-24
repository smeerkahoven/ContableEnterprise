CREATE DEFINER=`web_contabilidad`@`%` PROCEDURE `sumasYsaldosNivel5insert`(
  in in_start_date date 
, in in_end_date date 
, in in_moneda varchar(1)
, in in_id_empresa int
, in in_id_plan_cuenta int
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
     
declare cur_sumas_saldos_nivel_5 cursor for
select 
	A.id_plan_cuentas
  , A.cuenta
  , A.nro_plan_cuenta
  , A.nro_plan_cuenta_padre
  , A.suma_debe
  , A.suma_haber
  , if (A.suma_debe > A.suma_haber , A.suma_debe - A.suma_haber, 0 ) saldo_debe
  , if (A.suma_debe < A.suma_haber , A.suma_haber - A.suma_debe , 0 ) saldo_haber
  , A.nivel
  FROM (
	 select 
      pc.id_plan_cuentas
    , pc.cuenta
    , pc.nro_plan_cuenta
    , pc.nro_plan_cuenta_padre
    , sum(if ( in_moneda ='B' , coalesce(ac.monto_debe_nac,0), coalesce(ac.monto_debe_ext) )) suma_debe -- true para B  y false para U
    , sum(if ( in_moneda ='B' , coalesce(ac.monto_haber_nac,0), coalesce(ac.monto_haber_ext) )) suma_haber
    , 0 saldo_debe
    , 0 saldo_haber
    , pc.nivel

    from cnt_plan_cuentas pc 
    inner join cnt_asiento_contable ac on ac.id_plan_cuenta = pc.id_plan_cuentas
    where pc.id_plan_cuentas = in_id_plan_cuenta
    and fecha_movimiento between in_start_date and in_end_date
    group by pc.id_plan_cuentas
    , pc.cuenta
    , pc.nro_plan_cuenta
    , pc.nro_plan_cuenta_padre
    
)A;
    
declare continue handler for not found set finished = 1 ;

open cur_sumas_saldos_nivel_5 ;

	loop_cur_sumas_saldos : loop 
		fetch cur_sumas_saldos_nivel_5 into 
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
	

	end loop loop_cur_sumas_saldos ;
	
close cur_sumas_saldos_nivel_5 ;

END
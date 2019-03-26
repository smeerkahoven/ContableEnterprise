select * from PLANCTAS ;
select * from PLANCTAS where nivel = 6;
select count(*) from cnt_plan_cuentas ; -- 275 + 397

INSERT into cnt_plan_cuentas (
id_empresa
,nro_plan_cuenta
,cuenta
,moneda
,marco
-- ,cta_itb
,nivel
,aplica_movimiento
)
select 
  1
 , NUMERO nro_plan_cuenta
 , CUENTA cuenta
 , MONEDA moneda
 , MARCO marco
-- , CTAITB cta_itb
 , NIVEL nivel
 , case NIVEL  when '5' then 'T' else 'M' end aplica_movimiento
FROM PLANCTAS; 

select * from cnt_plan_cuentas ;

select
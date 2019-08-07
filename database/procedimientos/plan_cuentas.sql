INSERT into cnt_plan_cuentas (
id_empresa
,nro_plan_cuenta
,cuenta
,moneda
,marco
-- ,cta_itb
,nivel
,aplica_movimiento
,comodin
,tipo_regularizacion
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
 , 'N'
 , 'N'
FROM CUENTAS; 

update cnt_plan_cuentas set comodin='N';

UPDATE cnt_plan_cuentas SET nro_plan_cuenta_padre = substr(nro_plan_cuenta ,1,1) where nivel = 2 ;
UPDATE cnt_plan_cuentas SET nro_plan_cuenta_padre = substr(nro_plan_cuenta ,1,3) where nivel = 3 ;
UPDATE cnt_plan_cuentas SET nro_plan_cuenta_padre = substr(nro_plan_cuenta ,1,5) where nivel = 4 ;
UPDATE cnt_plan_cuentas SET nro_plan_cuenta_padre = substr(nro_plan_cuenta ,1,7) where nivel = 5 ;
select * from cnt_plan_cuentas ;

select * from cnt_parametros ;
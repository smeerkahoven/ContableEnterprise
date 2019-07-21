CREATE DEFINER=`web_contabilidad`@`%` PROCEDURE `estadoResultado`(
 in in_start_date date 
 , in in_end_date date 
 , in in_moneda varchar(1)
 , in in_id_empresa int
)
BEGIN

	DECLARE v_id_plan_cuenta		INT(11) ;
	DECLARE v_cuenta				VARCHAR(40) ;
	DECLARE v_nro_plan_cuenta		BIGINT(20) ;
	DECLARE v_nro_plan_cuenta_padre	BIGINT(20)  ;
	DECLARE v_suma_debe				DECIMAL(16,2) ;
	DECLARE v_suma_haber			DECIMAL(16,2) ;
	DECLARE v_saldo_debe_ingreso	DECIMAL(16,2) ;
    DECLARE v_saldo_haber_egreso	DECIMAL(16,2) ;
	DECLARE v_saldo_haber			DECIMAL(16,2) ;
	DECLARE v_nivel					INT ;
	
	call estadoResultadoSumaSaldo (in_start_date, in_end_date, in_moneda, in_id_empresa, 5) ;

	select coalesce(f.v_saldo_haber,0) saldo_haber into @v_saldo_haber_egreso
    from tmp_sumas_saldos f
    where f.v_nro_plan_cuenta = 3 ;

	select coalesce(f.v_saldo_debe,0) saldo_debe into @v_saldo_debe_ingreso
    from tmp_sumas_saldos f
    where f.v_nro_plan_cuenta = 4 ;
    
	insert into tmp_sumas_saldos(
		v_id_plan_cuenta,
		v_cuenta,
		v_nro_plan_cuenta,
		v_nro_plan_cuenta_padre,
		v_suma_debe,
		v_suma_haber,
		v_saldo_haber,
        v_saldo_debe,
		v_nivel
    )values (
		0,
        'UTILIDAD DE LA GESTION',
        4,
        0,        
        0.001,
        0.001,        
        if (@v_saldo_haber_egreso > @v_saldo_debe_ingreso, @v_saldo_haber_egreso - @v_saldo_debe_ingreso, 0 ),        
        if (@v_saldo_debe_ingreso > @v_saldo_haber_egreso, @v_saldo_debe_ingreso - @v_saldo_haber_egreso, 0 ),
        0
    );
    
select * from tmp_sumas_saldos f
where (f.v_nro_plan_cuenta like '3%' or f.v_nro_plan_cuenta like '4%')
 and (f.v_saldo_debe > 0 or f.v_saldo_haber > 0 or f.v_id_plan_cuenta = 0);

END
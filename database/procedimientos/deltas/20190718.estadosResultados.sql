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
	DECLARE v_saldo_ingreso			DECIMAL(16,2) ;
    DECLARE v_saldo_egreso			DECIMAL(16,2) ;    
	DECLARE v_nivel					INT ;
	
	call estadoResultadoSumaSaldo (in_start_date, in_end_date, in_moneda, in_id_empresa, 5) ;
    
    drop temporary table if exists tmp_estados_resultados ;
	create temporary table tmp_estados_resultados (
	 v_id_plan_cuenta			INT(11),
     v_cuenta					varchar(40),
     v_nro_plan_cuenta			bigint(20),
	 v_nro_plan_cuenta_padre	bigint(20) ,
	 v_suma_debe				decimal(16,2) ,
	 v_suma_haber				decimal(16,2) ,
	 v_saldo_debe				decimal(16,2) ,
	 v_saldo_haber				decimal(16,2) ,
	 v_nivel					int,
     v_saldo					varchar(64), 
     v_saldo_monto				decimal(16,2)
) ; 
    
 -- solo se toma INGRESOS y EGRESOS    
	insert into tmp_estados_resultados(
		v_id_plan_cuenta, 
        v_cuenta,
        v_nro_plan_cuenta, 
        v_nro_plan_cuenta_padre,
        v_suma_debe,
        v_suma_haber, 
        v_saldo_debe,
        v_saldo_haber,
        v_nivel,
        v_saldo, 
        v_saldo_monto)
	select 
		f.v_id_plan_cuenta, 
        f.v_cuenta,
        f.v_nro_plan_cuenta, 
        f.v_nro_plan_cuenta_padre,
        f.v_suma_debe,
        f.v_suma_haber, 
        f.v_saldo_debe,
        f.v_saldo_haber,
        f.v_nivel,
        
        IF(f.v_nro_plan_cuenta REGEXP '^[3]',  
			if (f.v_saldo_haber > f.v_saldo_debe, f.v_saldo_haber-f.v_saldo_debe,  concat('(', f.v_saldo_debe-f.v_saldo_haber,')') ),
		
			IF(f.v_nro_plan_cuenta REGEXP '^[4]',  
				if (f.v_saldo_debe > f.v_saldo_haber, f.v_saldo_debe-f.v_saldo_haber,  concat('(', f.v_saldo_haber-f.v_saldo_debe,')') ),
				'-'  
			)
        
        ),
        IF(f.v_nro_plan_cuenta REGEXP '^[3]',  
			if (f.v_saldo_haber > f.v_saldo_debe, f.v_saldo_haber-f.v_saldo_debe, (f.v_saldo_debe-f.v_saldo_haber)*-1 ),
		
			IF(f.v_nro_plan_cuenta REGEXP '^[4]',  
				if (f.v_saldo_debe > f.v_saldo_haber, f.v_saldo_debe-f.v_saldo_haber, (f.v_saldo_haber-f.v_saldo_debe)*-1 ),
				0  
			)
        
        )
			
    from tmp_sumas_saldos f
	where (f.v_nro_plan_cuenta like '3%' or f.v_nro_plan_cuenta like '4%')
	and (f.v_saldo_debe > 0 or f.v_saldo_haber > 0 or f.v_id_plan_cuenta = 0);
    
    
    select coalesce(f.v_saldo_monto,0) saldo
		into @v_saldo_ingreso
    from tmp_estados_resultados f
    where f.v_nro_plan_cuenta = 3 ;
    
    select coalesce(f.v_saldo_monto,0) saldo
		into @v_saldo_egreso
    from tmp_estados_resultados f
    where f.v_nro_plan_cuenta = 4 ;


    
	insert into tmp_estados_resultados(
		v_id_plan_cuenta,
		v_cuenta,
		v_nro_plan_cuenta,
		v_nro_plan_cuenta_padre,
		v_suma_debe,
		v_suma_haber,
		v_saldo_debe,
        v_saldo_haber,
		v_nivel,
        v_saldo,
        v_saldo_monto
        
    )values (
		0,
        'UTILIDAD OPERATIVA',
        null,
        null,        
        null,
        null,        
		null,
        null,
        0, 
        if ( (@v_saldo_ingreso- @v_saldo_egreso) > 0, truncate(@v_saldo_ingreso- @v_saldo_egreso,2), concat('(', truncate ( ((@v_saldo_ingreso- @v_saldo_egreso) *-1),2 ),')' ) ),
        @v_saldo_ingreso- @v_saldo_egreso
    );
    
 
	select * from tmp_estados_resultados ;

END
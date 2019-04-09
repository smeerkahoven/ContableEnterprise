insert into cnt_cliente(
id_cliente_grupo
,id_promotor
,id_cliente_corporativo
,nombre
,ci
,nit
,direccion
,telefono_fijo
,estado
,limite_credito
,plazo_maximo
,interes_mora
,calc_automatico_interes
,representante
,fecha_alta
)
select 
	case TIPO when 'J' then 47
			  when 'E' then 48
              else 1
	end id_cliente_grupo
    ,null id_promotor
    ,null id_cliente_corporativo
    ,NOMBRE nombre
    ,CI ci
    ,RUC nit
    ,DIR1 direccion 
    ,substr(TELEF,1,32) telefono_fijo
    ,'A' estado
    ,0 limite_credito
    ,0 plazo_maximo
    ,3 interes_mora
    ,0 calc_automatico_interes
    ,NOMBRE representante
    ,sysdate() fecha_alta
from clientes;
commit ;
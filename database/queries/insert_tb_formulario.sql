insert into tb_formularios (
	id_modulo, nombre, url_acceso , restful_url, status, fecha_alta, full_path
)
values (
	(select id_modulo from tb_modulos where id_modulo = 'Contabilidad')
    ,'Comprobantes-Contador'
    ,'comprobantes-contador'
    , 'http://192.168.0.16:8080/ContableEnterprise-ws/ws-api/comprobantes/contador/'
    ,'ACTIVO'
    ,curdate()
    ,'/pages/contabilidad/comprobantes-contador.xhtml'

)
;

insert into tb_rol_formulario (id_rol,id_formulario, crear,actualizar, eliminar,
	acceder,buscar,editar,anular,ver)
values (

);

select * from tb_rol_formulario;
select * from tb_modulos ;

select * from tb_formularios;
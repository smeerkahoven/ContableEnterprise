-- formulario comprobante-contador
insert into tb_formularios (
id_modulo
, nombre
, url_acceso
, restful_url
, status
, fecha_alta
, full_path
)values 
(
(select id_modulo from tb_modulos where nombre = 'Contabilidad')
, 'Comprobantes-Contador'
, 'comprobantes-contador'
, 'http://172.31.32.112:8080/ContableEnterprise-ws/ws-api/comprobantes-contador/'
, 'ACTIVO'
, curdate()
, '/pages/contabilidad/comprobantes-contador.xhtml'
)
;
-- PErmisos ADMIN
select * from tb_rol_formulario ;
insert into tb_rol_formulario (id_rol,id_formularios, crear, actualizar, eliminar, acceder, buscar, editar, anular, ver)
values (
 (select id_rol from tb_rol where nombre = 'ADMINISTRADOR')
, (select id_formulario from tb_formularios where url_acceso = 'comprobantes-contador')
, 1,1,1,1,1,1,1,1
)
;


-- formulario Sumas y Saldos
insert into tb_formularios (
id_modulo
, nombre
, url_acceso
, restful_url
, status
, fecha_alta
, full_path
)values 
(
(select id_modulo from tb_modulos where nombre = 'Contabilidad')
, 'Sumas y Saldos'
, 'sumas-saldos'
, 'http://172.31.32.112:8080/ContableEnterprise-ws/ws-api/sumas-saldos/'
, 'ACTIVO'
, curdate()
, '/pages/contabilidad/sumas-saldos.xhtml'
)
;
-- PErmisos ADMIN
select * from tb_rol_formulario ;
insert into tb_rol_formulario (id_rol,id_formularios, crear, actualizar, eliminar, acceder, buscar, editar, anular, ver)
values (
 (select id_rol from tb_rol where nombre = 'ADMINISTRADOR')
, (select id_formulario from tb_formularios where url_acceso = 'sumas-saldos')
, 1,1,1,1,1,1,1,1
)
;
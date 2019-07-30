

ALTER TABLE `db_travel`.`cnt_asiento_contable` 
ADD COLUMN `tipo_monto_boleto` VARCHAR(1) NULL AFTER `id_devolucion`;


ALTER TABLE `db_travel`.`cnt_cargo_boleto` 
DROP FOREIGN KEY `FK_cnt_cargo_boleto_id_cuenta_agencia`,
DROP FOREIGN KEY `FK_cnt_cargo_boleto_id_cuenta_promotor`,
DROP FOREIGN KEY `fk_cnt_cargo_boleto_2`,
DROP FOREIGN KEY `fk_cnt_cargo_boleto_3`;
ALTER TABLE `db_travel`.`cnt_cargo_boleto` 
CHANGE COLUMN `id_cuenta_agencia` `id_cuenta_agencia` INT(11) NULL ,
CHANGE COLUMN `id_cuenta_promotor` `id_cuenta_promotor` INT(11) NULL ;
ALTER TABLE `db_travel`.`cnt_cargo_boleto` 
ADD CONSTRAINT `FK_cnt_cargo_boleto_id_cuenta_agencia`
  FOREIGN KEY (`id_cuenta_agencia`)
  REFERENCES `db_travel`.`cnt_plan_cuentas` (`id_plan_cuentas`),
ADD CONSTRAINT `FK_cnt_cargo_boleto_id_cuenta_promotor`
  FOREIGN KEY (`id_cuenta_promotor`)
  REFERENCES `db_travel`.`cnt_plan_cuentas` (`id_plan_cuentas`),
ADD CONSTRAINT `fk_cnt_cargo_boleto_2`
  FOREIGN KEY (`id_cuenta_agencia`)
  REFERENCES `db_travel`.`cnt_plan_cuentas` (`id_plan_cuentas`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_cnt_cargo_boleto_3`
  FOREIGN KEY (`id_cuenta_promotor`)
  REFERENCES `db_travel`.`cnt_plan_cuentas` (`id_plan_cuentas`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


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
, '/ContableEnterprise-ws/ws-api/comprobantes-contador/'
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
, '/ContableEnterprise-ws/ws-api/sumas-saldos/'
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



-- formulario Estados de REsultadso y Saldos
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
, 'Estados de Resultados'
, 'estados-resultados'
, '/ContableEnterprise-ws/ws-api/estados-resultados/'
, 'ACTIVO'
, curdate()
, '/pages/contabilidad/estados-resultados.xhtml'
)
;
-- PErmisos ADMIN
insert into tb_rol_formulario (id_rol,id_formularios, crear, actualizar, eliminar, acceder, buscar, editar, anular, ver)
values (
 (select id_rol from tb_rol where nombre = 'ADMINISTRADOR')
, (select id_formulario from tb_formularios where url_acceso = 'estados-resultados')
, 1,1,1,1,1,1,1,1
)
;


-- formulario Estados de REsultadso y Saldos
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
, 'Balance General'
, 'balance-general'
, '/ContableEnterprise-ws/ws-api/balance-general/'
, 'ACTIVO'
, curdate()
, '/pages/contabilidad/balance-general.xhtml'
)
;
-- PErmisos ADMIN
insert into tb_rol_formulario (id_rol,id_formularios, crear, actualizar, eliminar, acceder, buscar, editar, anular, ver)
values (
 (select id_rol from tb_rol where nombre = 'ADMINISTRADOR')
, (select id_formulario from tb_formularios where url_acceso = 'balance-general')
, 1,1,1,1,1,1,1,1
)
;



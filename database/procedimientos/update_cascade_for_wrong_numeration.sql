
-- notas de debito
ALTER TABLE `db_travel`.`cnt_cargo_boleto` 
DROP FOREIGN KEY `fk_cnt_cargo_boleto_5`;
ALTER TABLE `db_travel`.`cnt_cargo_boleto` 
ADD CONSTRAINT `fk_cnt_cargo_boleto_5`
  FOREIGN KEY (`id_nota_debito`)
  REFERENCES `db_travel`.`cnt_nota_debito` (`id_nota_debito`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
DROP FOREIGN KEY `fk_cnt_comprobante_contable_1`;
ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
ADD CONSTRAINT `fk_cnt_comprobante_contable_1`
  FOREIGN KEY (`id_nota_debito`)
  REFERENCES `db_travel`.`cnt_nota_debito` (`id_nota_debito`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_debito_ingreso` 
DROP FOREIGN KEY `fk_cnt_debito_ingreso_2`;
ALTER TABLE `db_travel`.`cnt_debito_ingreso` 
ADD CONSTRAINT `fk_cnt_debito_ingreso_2`
  FOREIGN KEY (`id_nota_debito`)
  REFERENCES `db_travel`.`cnt_nota_debito` (`id_nota_debito`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_devolucion` 
DROP FOREIGN KEY `fk_cnt_devolucion_2`;
ALTER TABLE `db_travel`.`cnt_devolucion` 
ADD CONSTRAINT `fk_cnt_devolucion_2`
  FOREIGN KEY (`id_nota_debito`)
  REFERENCES `db_travel`.`cnt_nota_debito` (`id_nota_debito`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
  
  -- ingreso caja
  ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
DROP FOREIGN KEY `fk_cnt_comprobante_contable_4`;
ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
ADD CONSTRAINT `fk_cnt_comprobante_contable_4`
  FOREIGN KEY (`id_ingreso_caja`)
  REFERENCES `db_travel`.`cnt_ingreso_caja` (`id_ingreso_caja`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_debito_ingreso` 
DROP FOREIGN KEY `fk_cnt_debito_ingreso_1`;
ALTER TABLE `db_travel`.`cnt_debito_ingreso` 
ADD CONSTRAINT `fk_cnt_debito_ingreso_1`
  FOREIGN KEY (`id_ingreso_caja`)
  REFERENCES `db_travel`.`cnt_ingreso_caja` (`id_ingreso_caja`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `db_travel`.`cnt_ingreso_transaccion` 
DROP FOREIGN KEY `cnt_ingreso_transaccion_ibfk_1`;
ALTER TABLE `db_travel`.`cnt_ingreso_transaccion` 
ADD CONSTRAINT `cnt_ingreso_transaccion_ibfk_1`
  FOREIGN KEY (`id_ingreso_caja`)
  REFERENCES `db_travel`.`cnt_ingreso_caja` (`id_ingreso_caja`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

-- pago anticipado
ALTER TABLE `db_travel`.`cnt_asiento_contable` 
DROP FOREIGN KEY `fk_cnt_asiento_contable_9`;
ALTER TABLE `db_travel`.`cnt_asiento_contable` 
ADD CONSTRAINT `fk_cnt_asiento_contable_9`
  FOREIGN KEY (`id_pago_anticipado`)
  REFERENCES `db_travel`.`cnt_pago_anticipado` (`id_pago_anticipado`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
DROP FOREIGN KEY `fk_cnt_comprobante_contable_6`;
ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
ADD CONSTRAINT `fk_cnt_comprobante_contable_6`
  FOREIGN KEY (`id_pago_anticipado`)
  REFERENCES `db_travel`.`cnt_pago_anticipado` (`id_pago_anticipado`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `db_travel`.`cnt_devolucion` 
DROP FOREIGN KEY `fk_cnt_devolucion_3`;
ALTER TABLE `db_travel`.`cnt_devolucion` 
ADD CONSTRAINT `fk_cnt_devolucion_3`
  FOREIGN KEY (`id_pago_anticipado`)
  REFERENCES `db_travel`.`cnt_pago_anticipado` (`id_pago_anticipado`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
DROP FOREIGN KEY `fk_cnt_pago_anticipado_transaccion_1`;
ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
ADD CONSTRAINT `fk_cnt_pago_anticipado_transaccion_1`
  FOREIGN KEY (`id_pago_anticipado`)
  REFERENCES `db_travel`.`cnt_pago_anticipado` (`id_pago_anticipado`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

  -- pago anticipado transaccion
ALTER TABLE `db_travel`.`cnt_asiento_contable` 
DROP FOREIGN KEY `fk_cnt_asiento_contable_8`;
ALTER TABLE `db_travel`.`cnt_asiento_contable` 
ADD CONSTRAINT `fk_cnt_asiento_contable_8`
  FOREIGN KEY (`id_pago_anticipado_transaccion`)
  REFERENCES `db_travel`.`cnt_pago_anticipado_transaccion` (`id_pago_anticipado_transaccion`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

-- nota credito

ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
DROP FOREIGN KEY `fk_cnt_comprobante_contable_5`;
ALTER TABLE `db_travel`.`cnt_comprobante_contable` 
ADD CONSTRAINT `fk_cnt_comprobante_contable_5`
  FOREIGN KEY (`id_nota_credito`)
  REFERENCES `db_travel`.`cnt_nota_credito` (`id_nota_credito`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `db_travel`.`cnt_nota_credito_transaccion` 
DROP FOREIGN KEY `fk_cnt_nota_credito_transaccion_1`;
ALTER TABLE `db_travel`.`cnt_nota_credito_transaccion` 
ADD CONSTRAINT `fk_cnt_nota_credito_transaccion_1`
  FOREIGN KEY (`id_nota_credito`)
  REFERENCES `db_travel`.`cnt_nota_credito` (`id_nota_credito`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

-- devolucion

ALTER TABLE `db_travel`.`cnt_asiento_contable` 
DROP FOREIGN KEY `fk_cnt_asiento_contable_10`;
ALTER TABLE `db_travel`.`cnt_asiento_contable` 
ADD CONSTRAINT `fk_cnt_asiento_contable_10`
  FOREIGN KEY (`id_devolucion`)
  REFERENCES `db_travel`.`cnt_devolucion` (`id_devolucion`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
DROP FOREIGN KEY `fk_cnt_pago_anticipado_transaccion_3`;
ALTER TABLE `db_travel`.`cnt_pago_anticipado_transaccion` 
ADD CONSTRAINT `fk_cnt_pago_anticipado_transaccion_3`
  FOREIGN KEY (`id_devolucion`)
  REFERENCES `db_travel`.`cnt_devolucion` (`id_devolucion`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;

  
-- este update hace el trabajo de cambiar los ids 
update cnt_nota_debito set id_nota_debito= id_nota_debito + 10000000 ;
update cnt_ingreso_caja set id_ingreso_caja = id_ingreso_caja + 20000000 ;
update cnt_pago_anticipado set id_pago_anticipado = id_pago_anticipado + 30000000 ;
update cnt_pago_anticipado_transaccion set id_pago_anticipado_transaccion = id_pago_anticipado_transaccion + 40000000 ;
update cnt_nota_credito set id_nota_credito = id_nota_credito + 50000000 ;
update cnt_pago_anticipado_transaccion set id_devolucion = id_devolucion + 60000000 ;

-- para dar los valores hay q hacer un 
select max(id_nota_debito)+1 from cnt_nota_debito;

-- y luego anotarlos en el auto_increment
  
alter table cnt_nota_debito auto_increment = 10000041;
alter table cnt_ingreso_caja auto_increment = 20000001;
alter table cnt_pago_anticipado auto_increment = 30000001;
alter table cnt_pago_anticipado_transaccion auto_increment = 40000001;
alter table cnt_nota_credito auto_increment = 50000001;
alter table cnt_devolucion auto_increment = 60000001;
  
  
  
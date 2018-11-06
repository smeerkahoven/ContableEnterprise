-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: db_travel
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `PLANCTAS`
--

DROP TABLE IF EXISTS `PLANCTAS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PLANCTAS` (
  `OFICINA` text,
  `NUMERO` text,
  `CUENTA` text,
  `CTRA_CTA` text,
  `APLICA` text,
  `MONEDA` text,
  `MARCO` text,
  `CTAITB` text,
  `NIVEL` text,
  `FONDOS` text,
  `TIPO` text,
  `SECUENCIA` text,
  `PRESUP` text,
  `T_PRESUP` text,
  `T_PASIVO` text,
  `RUBRO` text,
  `TGIRO` text,
  `CTAGIRO` text,
  `SALDO` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SEQUENCE`
--

DROP TABLE IF EXISTS `SEQUENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_aerolinea`
--

DROP TABLE IF EXISTS `cnt_aerolinea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_aerolinea` (
  `id_aerolinea` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(2) NOT NULL,
  `iata` varchar(4) NOT NULL,
  `nit` varchar(15) NOT NULL,
  `emitir_factura_a` varchar(64) NOT NULL,
  `nombre` varchar(64) NOT NULL,
  `representante` varchar(64) DEFAULT NULL,
  `direccion` varchar(256) DEFAULT NULL,
  `telefono` varchar(32) DEFAULT NULL,
  `celular` varchar(32) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `persona_contacto` varchar(64) DEFAULT NULL,
  `bsp` tinyint(1) DEFAULT NULL,
  `comision_prom_int` decimal(6,2) DEFAULT NULL,
  `comision_prom_int_tipo` varchar(1) DEFAULT NULL COMMENT 'N=NETO\nT=TOTAL',
  `comision_prom_nac` decimal(6,2) DEFAULT NULL,
  `comision_prom_nac_tipo` varchar(1) DEFAULT NULL COMMENT 'N=NETO\nT=TOTAL',
  `round_comision_bob` tinyint(1) DEFAULT NULL,
  `round_comision_usd` tinyint(1) DEFAULT NULL,
  `iva_it_comision` tinyint(1) DEFAULT NULL,
  `boletos_mon_nac` tinyint(1) DEFAULT NULL,
  `boletos_mon_ext` tinyint(1) DEFAULT NULL,
  `moneda` varchar(1) DEFAULT NULL,
  `impuesto_valor_neto` tinyint(1) DEFAULT NULL,
  `impuesto_qm` tinyint(1) DEFAULT NULL,
  `cargo_no_fiscal` tinyint(1) DEFAULT NULL,
  `modalidad_boleto` tinyint(1) DEFAULT NULL,
  `registra_pnr` tinyint(1) DEFAULT NULL,
  `modifica_comision` tinyint(1) DEFAULT NULL,
  `tipo_modalidad` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_aerolinea`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_aerolinea_cuenta`
--

DROP TABLE IF EXISTS `cnt_aerolinea_cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_aerolinea_cuenta` (
  `id_aerolinea_cuenta` int(11) NOT NULL AUTO_INCREMENT,
  `id_aerolinea` int(11) NOT NULL,
  `id_plan_cuentas` int(11) NOT NULL,
  `tipo` varchar(1) DEFAULT NULL,
  `moneda` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_aerolinea_cuenta`),
  KEY `id_aerolinea` (`id_aerolinea`),
  KEY `id_plan_cuentas` (`id_plan_cuentas`),
  CONSTRAINT `cnt_aerolinea_cuenta_ibfk_1` FOREIGN KEY (`id_aerolinea`) REFERENCES `cnt_aerolinea` (`id_aerolinea`) ON DELETE CASCADE,
  CONSTRAINT `cnt_aerolinea_cuenta_ibfk_2` FOREIGN KEY (`id_plan_cuentas`) REFERENCES `cnt_plan_cuentas` (`id_plan_cuentas`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_aerolinea_impuesto`
--

DROP TABLE IF EXISTS `cnt_aerolinea_impuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_aerolinea_impuesto` (
  `id_aerolinea_impuesto` int(11) NOT NULL AUTO_INCREMENT,
  `id_aerolinea` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_aerolinea_impuesto`),
  KEY `id_aerolinea` (`id_aerolinea`),
  CONSTRAINT `cnt_aerolinea_impuesto_ibfk_1` FOREIGN KEY (`id_aerolinea`) REFERENCES `cnt_aerolinea` (`id_aerolinea`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_aeropuerto`
--

DROP TABLE IF EXISTS `cnt_aeropuerto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_aeropuerto` (
  `id_aeropuerto` varchar(4) NOT NULL,
  `nombre` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id_aeropuerto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_archivo_boleto`
--

DROP TABLE IF EXISTS `cnt_archivo_boleto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_archivo_boleto` (
  `id_archivo_boleto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_archivo` varchar(45) NOT NULL,
  `tipo_archivo` varchar(1) NOT NULL,
  `contenido` text NOT NULL,
  `fecha_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `estado` varchar(1) NOT NULL,
  PRIMARY KEY (`id_archivo_boleto`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_asiento_contable`
--

DROP TABLE IF EXISTS `cnt_asiento_contable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_asiento_contable` (
  `id_asiento` int(8) NOT NULL AUTO_INCREMENT,
  `id_plan_cuenta` int(11) NOT NULL,
  `id_libro` int(11) NOT NULL,
  `gestion` int(6) NOT NULL,
  `fecha_movimiento` date NOT NULL,
  `monto_debe_nac` decimal(16,2) DEFAULT NULL,
  `monto_haber_nac` decimal(16,2) DEFAULT NULL,
  `monto_debe_ext` decimal(16,2) DEFAULT NULL,
  `monto_haber_ext` decimal(16,2) DEFAULT NULL,
  `estado` varchar(2) DEFAULT NULL,
  `moneda` varchar(1) DEFAULT NULL,
  `id_boleto` int(11) DEFAULT NULL,
  `id_nota_transaccion` int(11) DEFAULT NULL,
  `id_ingreso_caja_transaccion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_asiento`),
  KEY `fk_cnt_asiento_contable_1_idx` (`id_plan_cuenta`),
  KEY `fk_cnt_asiento_contable_2_idx` (`id_libro`),
  KEY `fk_cnt_asiento_contable_3_idx` (`id_boleto`),
  KEY `fk_cnt_asiento_contable_4_idx` (`id_nota_transaccion`),
  KEY `fk_cnt_asiento_contable_5_idx` (`id_ingreso_caja_transaccion`),
  CONSTRAINT `fk_cnt_asiento_contable_1` FOREIGN KEY (`id_plan_cuenta`) REFERENCES `cnt_plan_cuentas` (`id_plan_cuentas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_asiento_contable_2` FOREIGN KEY (`id_libro`) REFERENCES `cnt_comprobante_contable` (`id_libro`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_asiento_contable_3` FOREIGN KEY (`id_boleto`) REFERENCES `cnt_boleto` (`id_boleto`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_asiento_contable_4` FOREIGN KEY (`id_nota_transaccion`) REFERENCES `cnt_nota_debito_transaccion` (`id_nota_debito_transaccion`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_asiento_contable_5` FOREIGN KEY (`id_ingreso_caja_transaccion`) REFERENCES `cnt_ingreso_transaccion` (`id_transaccion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_bancos`
--

DROP TABLE IF EXISTS `cnt_bancos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_bancos` (
  `id_banco` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  `direccion` varchar(64) DEFAULT NULL,
  `telefono` varchar(32) DEFAULT NULL,
  `celular` varchar(12) DEFAULT NULL,
  `contacto` varchar(40) DEFAULT NULL,
  `abreviacion` varchar(12) DEFAULT NULL,
  `nit` varchar(16) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id_banco`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_boleto`
--

DROP TABLE IF EXISTS `cnt_boleto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_boleto` (
  `id_boleto` int(11) NOT NULL AUTO_INCREMENT,
  `id_boleto_padre` int(11) DEFAULT NULL,
  `id_aerolinea` int(11) NOT NULL,
  `id_promotor` int(11) DEFAULT NULL COMMENT 'Counter',
  `id_ingreso_caja` int(11) DEFAULT NULL COMMENT 'hace referencia a cnt_ingreso_caja\n',
  `id_ingreso_caja_transaccion` int(11) DEFAULT NULL COMMENT 'hace referencia a cnt_ingreso_caja\n',
  `id_libro` int(11) DEFAULT NULL COMMENT 'hace referencia a la nota de debito',
  `id_nota_debito` int(11) DEFAULT NULL,
  `id_nota_debito_transaccion` int(11) DEFAULT NULL,
  `id_empresa` int(11) NOT NULL,
  `emision` varchar(4) DEFAULT NULL COMMENT 'Normal-NORM\nConexion-CNX\nCanje-CHG\nVirtual-MDP\nDebito-ADM\nCredito-ACM\nFuera de Stock-TKT',
  `tipo_boleto` varchar(2) NOT NULL COMMENT 'Multiple M\nSimple S',
  `tipo_cupon` varchar(1) DEFAULT NULL COMMENT 'Internacional I\nNAcional N',
  `numero_origen` bigint(18) DEFAULT NULL,
  `numero` bigint(18) NOT NULL,
  `id_ruta_1` varchar(4) DEFAULT NULL,
  `id_ruta_2` varchar(4) DEFAULT NULL,
  `id_ruta_3` varchar(4) DEFAULT NULL,
  `id_ruta_4` varchar(4) DEFAULT NULL,
  `id_ruta_5` varchar(4) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `nombre_pasajero` varchar(64) DEFAULT NULL,
  `fecha_emision` date DEFAULT NULL,
  `fecha_viaje` date DEFAULT NULL,
  `factor_cambiario` decimal(8,2) DEFAULT NULL,
  `forma_pago` varchar(1) DEFAULT NULL COMMENT 'Credito C\nContado E\nTarjeta T\nPago Combinado P',
  `estado` varchar(1) NOT NULL COMMENT 'PENDIENTE\nCOMPLETADO',
  `fecha_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_usuario_creador` varchar(16) NOT NULL,
  `gestion` int(11) NOT NULL,
  `credito_dias` int(11) DEFAULT NULL COMMENT 'numero de dias de plazo del credito',
  `credito_vencimiento` date DEFAULT NULL COMMENT 'fecha de vencimiento\n',
  `moneda` varchar(1) DEFAULT NULL,
  `importe_neto` decimal(12,2) DEFAULT NULL,
  `impuesto_bob` decimal(12,2) DEFAULT NULL,
  `impuesto_qm` decimal(12,2) DEFAULT NULL,
  `impuesto_1_nombre` varchar(12) DEFAULT NULL,
  `impuesto_1` decimal(12,2) DEFAULT NULL,
  `impuesto_2_nombre` varchar(12) DEFAULT NULL,
  `impuesto_2` decimal(12,2) DEFAULT NULL,
  `impuesto_3_nombre` varchar(12) DEFAULT NULL,
  `impuesto_3` decimal(12,2) DEFAULT NULL,
  `impuesto_4_nombre` varchar(12) DEFAULT NULL,
  `impuesto_4` decimal(12,2) DEFAULT NULL,
  `impuesto_5_nombre` varchar(12) DEFAULT NULL,
  `impuesto_5` decimal(12,2) DEFAULT NULL,
  `total_boleto` decimal(12,2) DEFAULT NULL,
  `comision` decimal(4,2) DEFAULT NULL,
  `monto_comision` decimal(12,2) DEFAULT NULL,
  `fee` decimal(4,2) DEFAULT NULL,
  `monto_fee` decimal(12,2) DEFAULT NULL,
  `descuento` decimal(4,2) DEFAULT NULL,
  `monto_descuento` decimal(12,2) DEFAULT NULL,
  `total_monto_cancelado` decimal(12,2) DEFAULT NULL,
  `contado_tipo` varchar(1) DEFAULT NULL,
  `contado_nro_cheque` varchar(45) DEFAULT NULL,
  `contado_id_banco` int(11) DEFAULT NULL,
  `contado_id_cuenta` int(11) DEFAULT NULL,
  `contado_nro_deposito` varchar(45) DEFAULT NULL,
  `tarjeta_numero` varchar(16) DEFAULT NULL,
  `tarjeta_id` int(11) DEFAULT NULL,
  `combinado_tipo` varchar(1) DEFAULT NULL,
  `combinado_credito` tinyint(4) DEFAULT NULL,
  `combinado_credito_dias` int(4) DEFAULT NULL,
  `combinado_credito_vencimiento` date DEFAULT NULL,
  `combinado_credito_monto` decimal(12,2) DEFAULT NULL,
  `combinado_contado` tinyint(4) DEFAULT NULL,
  `combinado_contado_tipo` varchar(1) DEFAULT NULL,
  `combinado_contado_monto` decimal(12,2) DEFAULT NULL,
  `combinado_contado_nro_cheque` varchar(20) DEFAULT NULL,
  `combinado_contado_id_cuenta` int(11) DEFAULT NULL,
  `combinado_contado_id_banco` int(11) DEFAULT NULL,
  `combinado_contado_nro_deposito` varchar(20) DEFAULT NULL,
  `combinado_tarjeta` tinyint(4) DEFAULT NULL,
  `combinado_tarjeta_id` int(11) DEFAULT NULL,
  `combinado_tarjeta_numero` varchar(16) DEFAULT NULL,
  `combinado_tarjeta_monto` decimal(12,2) DEFAULT NULL,
  `id_archivo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_boleto`),
  KEY `id_aerolinea` (`id_aerolinea`),
  KEY `fk_cnt_boleto_2_idx` (`id_usuario_creador`),
  KEY `fk_cnt_boleto_1_idx` (`id_nota_debito_transaccion`),
  KEY `cnt_boleto_ibfk_3_idx` (`id_ingreso_caja_transaccion`),
  KEY `fk_cnt_boleto_3_idx` (`id_archivo`),
  KEY `cnt_boleto_ibfk_2` (`id_promotor`),
  CONSTRAINT `cnt_boleto_ibfk_1` FOREIGN KEY (`id_aerolinea`) REFERENCES `cnt_aerolinea` (`id_aerolinea`) ON DELETE NO ACTION,
  CONSTRAINT `cnt_boleto_ibfk_2` FOREIGN KEY (`id_promotor`) REFERENCES `cnt_promotor` (`id_promotor`) ON DELETE NO ACTION,
  CONSTRAINT `cnt_boleto_ibfk_3` FOREIGN KEY (`id_ingreso_caja_transaccion`) REFERENCES `cnt_ingreso_transaccion` (`id_transaccion`) ON DELETE CASCADE,
  CONSTRAINT `fk_cnt_boleto_1` FOREIGN KEY (`id_nota_debito_transaccion`) REFERENCES `cnt_nota_debito_transaccion` (`id_nota_debito_transaccion`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_boleto_2` FOREIGN KEY (`id_ingreso_caja_transaccion`) REFERENCES `cnt_ingreso_transaccion` (`id_transaccion`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_boleto_3` FOREIGN KEY (`id_archivo`) REFERENCES `cnt_archivo_boleto` (`id_archivo_boleto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cambio_dolar`
--

DROP TABLE IF EXISTS `cnt_cambio_dolar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cambio_dolar` (
  `fecha` date NOT NULL,
  `valor` decimal(8,4) NOT NULL,
  PRIMARY KEY (`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cambio_ufv`
--

DROP TABLE IF EXISTS `cnt_cambio_ufv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cambio_ufv` (
  `fecha` date NOT NULL,
  `valor` decimal(10,6) NOT NULL,
  PRIMARY KEY (`fecha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cliente`
--

DROP TABLE IF EXISTS `cnt_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente_grupo` int(11) DEFAULT NULL,
  `id_promotor` int(11) DEFAULT NULL,
  `id_cliente_corporativo` int(11) DEFAULT NULL,
  `nombre` varchar(128) DEFAULT NULL,
  `ci` varchar(12) DEFAULT NULL,
  `nit` varchar(12) DEFAULT NULL,
  `direccion` varchar(128) DEFAULT NULL,
  `telefono_fijo` varchar(32) DEFAULT NULL,
  `telefono_celular` varchar(32) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `estado` varchar(1) DEFAULT '0',
  `moneda_credito` varchar(1) DEFAULT 'D',
  `limite_credito` decimal(8,2) DEFAULT '0.00',
  `plazo_maximo` int(4) DEFAULT NULL,
  `interes_mora` decimal(5,2) DEFAULT NULL,
  `calc_automatico_interes` tinyint(1) DEFAULT NULL,
  `interes_desde` varchar(1) DEFAULT NULL,
  `representante` varchar(64) DEFAULT NULL,
  `representante_direccion` varchar(64) DEFAULT NULL,
  `representante_telefono` varchar(32) DEFAULT NULL,
  `representante_celular` varchar(32) DEFAULT NULL,
  `representante_ci` varchar(12) DEFAULT NULL,
  `fecha_alta` date DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `id_cliente_grupo` (`id_cliente_grupo`),
  KEY `id_promotor` (`id_promotor`),
  KEY `cnt_cliente_ibfk_3` (`id_cliente_corporativo`),
  CONSTRAINT `cnt_cliente_ibfk_1` FOREIGN KEY (`id_cliente_grupo`) REFERENCES `cnt_cliente_grupo` (`id_cliente_grupo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cnt_cliente_ibfk_2` FOREIGN KEY (`id_promotor`) REFERENCES `cnt_promotor` (`id_promotor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cnt_cliente_ibfk_3` FOREIGN KEY (`id_cliente_corporativo`) REFERENCES `cnt_cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cliente_grupo`
--

DROP TABLE IF EXISTS `cnt_cliente_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cliente_grupo` (
  `id_cliente_grupo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id_cliente_grupo`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cliente_pasajero`
--

DROP TABLE IF EXISTS `cnt_cliente_pasajero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cliente_pasajero` (
  `id_cliente_pasajero` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) NOT NULL,
  `nombre_pasajero` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_cliente_pasajero`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `cnt_cliente_pasajero_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cnt_cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cliente_solicitado`
--

DROP TABLE IF EXISTS `cnt_cliente_solicitado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cliente_solicitado` (
  `id_cliente_solicitado` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_cliente_solicitado`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `cnt_cliente_solicitado_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cnt_cliente` (`id_cliente`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_com_promotor_aerolinea`
--

DROP TABLE IF EXISTS `cnt_com_promotor_aerolinea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_com_promotor_aerolinea` (
  `id_comision_promotor` int(11) NOT NULL AUTO_INCREMENT,
  `id_promotor` int(11) NOT NULL,
  `id_aerolinea` int(11) NOT NULL,
  `tipo_aerolinea` varchar(1) NOT NULL,
  `monto_comision` decimal(6,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id_comision_promotor`),
  KEY `id_promotor` (`id_promotor`),
  KEY `id_aerolinea` (`id_aerolinea`),
  CONSTRAINT `cnt_com_promotor_aerolinea_ibfk_1` FOREIGN KEY (`id_promotor`) REFERENCES `cnt_promotor` (`id_promotor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cnt_com_promotor_aerolinea_ibfk_2` FOREIGN KEY (`id_aerolinea`) REFERENCES `cnt_aerolinea` (`id_aerolinea`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='comision entre el promotor y las aerolineas\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_comprobante_contable`
--

DROP TABLE IF EXISTS `cnt_comprobante_contable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_comprobante_contable` (
  `id_libro` int(11) NOT NULL AUTO_INCREMENT,
  `id_numero_gestion` int(11) NOT NULL,
  `id_usuario_creador` varchar(16) NOT NULL,
  `id_usuario_anulado` varchar(16) DEFAULT NULL,
  `id_nota_debito` int(11) DEFAULT NULL,
  `gestion` int(6) NOT NULL,
  `fecha` date NOT NULL,
  `nombre` varchar(128) DEFAULT NULL,
  `concepto` varchar(128) DEFAULT NULL,
  `estado` varchar(2) DEFAULT NULL COMMENT 'ANULADO\nPENDIENTE\nRECUPERADO\nAPROBADO',
  `factor_cambiario` decimal(8,2) DEFAULT NULL,
  `tipo` varchar(2) DEFAULT NULL,
  `id_empresa` int(11) DEFAULT NULL,
  `total_debe_nac` decimal(12,2) DEFAULT NULL,
  `total_haber_nac` decimal(12,2) DEFAULT NULL,
  `total_debe_ext` decimal(12,2) DEFAULT NULL,
  `total_haber_ext` decimal(12,2) DEFAULT NULL,
  `fecha_insert` timestamp NULL DEFAULT NULL,
  `con_errores` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_libro`),
  KEY `fk_cnt_comprobante_contable_1_idx` (`id_usuario_creador`),
  KEY `fk_cnt_comprobante_contable_1_idx1` (`id_nota_debito`),
  KEY `fk_cnt_comprobante_contable_2_idx` (`id_empresa`),
  KEY `index5` (`gestion`),
  CONSTRAINT `fk_cnt_comprobante_contable_1` FOREIGN KEY (`id_nota_debito`) REFERENCES `cnt_nota_debito` (`id_nota_debito`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_comprobante_contable_2` FOREIGN KEY (`id_empresa`) REFERENCES `tb_empresa` (`id_empresa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_cuenta_banco`
--

DROP TABLE IF EXISTS `cnt_cuenta_banco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_cuenta_banco` (
  `id_cuenta_banco` int(11) NOT NULL AUTO_INCREMENT,
  `id_plan_cuentas` int(11) NOT NULL,
  `id_banco` int(10) unsigned NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_cuenta_banco`),
  KEY `id_banco` (`id_banco`),
  KEY `id_plan_cuentas` (`id_plan_cuentas`),
  CONSTRAINT `cnt_cuenta_banco_ibfk_1` FOREIGN KEY (`id_banco`) REFERENCES `cnt_bancos` (`id_banco`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `cnt_cuenta_banco_ibfk_2` FOREIGN KEY (`id_plan_cuentas`) REFERENCES `cnt_plan_cuentas` (`id_plan_cuentas`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_emision_boleto`
--

DROP TABLE IF EXISTS `cnt_emision_boleto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_emision_boleto` (
  `id_emision` varchar(4) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_emision`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_factor_cambio`
--

DROP TABLE IF EXISTS `cnt_factor_cambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_factor_cambio` (
  `fecha` text,
  `factor` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_ingreso_caja`
--

DROP TABLE IF EXISTS `cnt_ingreso_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_ingreso_caja` (
  `id_ingreso_caja` int(11) NOT NULL AUTO_INCREMENT,
  `id_nota_debito` int(11) NOT NULL,
  `id_usuario` varchar(16) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_counter` int(11) NOT NULL,
  `fecha_emision` date NOT NULL,
  `fecha_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `moneda` varchar(1) NOT NULL,
  `monto_abonado_bs` decimal(12,2) DEFAULT NULL,
  `monto_abonado_usd` decimal(12,2) DEFAULT NULL,
  `factor_cambiario` decimal(12,2) DEFAULT NULL,
  `forma_pago` varchar(1) DEFAULT NULL,
  `tipo_contado` varchar(1) DEFAULT NULL,
  `id_banco` int(10) DEFAULT NULL,
  `nro_cheque` varchar(45) DEFAULT NULL,
  `id_tarjeta_credito` int(11) DEFAULT NULL,
  `nro_tarjeta` varchar(45) DEFAULT NULL,
  `nro_deposito` varchar(45) DEFAULT NULL,
  `id_cuenta_deposito` int(11) DEFAULT NULL,
  `combinado_contado` tinyint(1) DEFAULT NULL,
  `combinado_tarjeta` tinyint(1) DEFAULT NULL,
  `estado` varchar(1) DEFAULT 'E',
  PRIMARY KEY (`id_ingreso_caja`),
  KEY `fk_cnt_ingreso_caja_1_idx` (`id_usuario`),
  KEY `fk_cnt_ingreso_caja_2_idx` (`id_empresa`),
  KEY `fk_cnt_ingreso_caja_3_idx` (`id_cliente`),
  KEY `fk_cnt_ingreso_caja_3_idx1` (`id_counter`),
  CONSTRAINT `fk_cnt_ingreso_caja_1` FOREIGN KEY (`id_cliente`) REFERENCES `cnt_cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_ingreso_caja_2` FOREIGN KEY (`id_empresa`) REFERENCES `tb_empresa` (`id_empresa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_ingreso_caja_3` FOREIGN KEY (`id_counter`) REFERENCES `cnt_promotor` (`id_promotor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2000009 DEFAULT CHARSET=latin1 COMMENT='cnt_cliente_id_cliente';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_ingreso_transaccion`
--

DROP TABLE IF EXISTS `cnt_ingreso_transaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_ingreso_transaccion` (
  `id_transaccion` int(11) NOT NULL AUTO_INCREMENT,
  `id_ingreso_caja` int(11) NOT NULL,
  `descripcion` varchar(64) DEFAULT NULL,
  `moneda` varchar(45) DEFAULT NULL,
  `monto_bs` decimal(12,2) DEFAULT NULL,
  `monto_usd` decimal(12,2) DEFAULT NULL,
  `fecha_insert` timestamp NULL DEFAULT NULL,
  `id_nota_transaccion` int(11) DEFAULT NULL,
  `estado` varchar(1) DEFAULT 'E',
  PRIMARY KEY (`id_transaccion`),
  KEY `cnt_ingreso_transaccion_ibfk_1` (`id_ingreso_caja`),
  KEY `fk_cnt_ingreso_transaccion_1_idx` (`id_nota_transaccion`),
  CONSTRAINT `cnt_ingreso_transaccion_ibfk_1` FOREIGN KEY (`id_ingreso_caja`) REFERENCES `cnt_ingreso_caja` (`id_ingreso_caja`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_ingreso_transaccion_1` FOREIGN KEY (`id_nota_transaccion`) REFERENCES `cnt_nota_debito_transaccion` (`id_nota_debito_transaccion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_log_archivos`
--

DROP TABLE IF EXISTS `cnt_log_archivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_log_archivos` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NULL DEFAULT NULL,
  `nombre_archivo` varchar(45) NOT NULL,
  `tipo_archivo` varchar(2) NOT NULL,
  `mensaje` varchar(256) NOT NULL,
  `usuario` varchar(16) DEFAULT NULL,
  `numero_boleto` bigint(18) DEFAULT NULL,
  `tipo` varchar(8) DEFAULT NULL COMMENT 'warning\ninfo\nerror',
  PRIMARY KEY (`id_log`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_nota_debito`
--

DROP TABLE IF EXISTS `cnt_nota_debito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_nota_debito` (
  `id_nota_debito` int(11) NOT NULL AUTO_INCREMENT,
  `id_empresa` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `id_counter` int(11) DEFAULT NULL,
  `fecha_emision` date DEFAULT NULL,
  `fecha_insert` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `monto_total_bs` decimal(12,2) DEFAULT NULL,
  `monto_total_usd` decimal(12,2) DEFAULT NULL,
  `monto_adeudado_bs` decimal(12,2) DEFAULT NULL,
  `monto_adeudado_usd` decimal(12,2) DEFAULT NULL,
  `factor_cambiario` decimal(12,2) DEFAULT NULL,
  `gestion` int(6) NOT NULL,
  `forma_pago` varchar(1) DEFAULT NULL,
  `tipo_contado` varchar(1) DEFAULT NULL,
  `id_banco` int(10) DEFAULT NULL,
  `nro_cheque` varchar(45) DEFAULT NULL,
  `id_tarjeta_credito` int(11) DEFAULT NULL,
  `nro_tarjeta` varchar(45) DEFAULT NULL,
  `nro_deposito` varchar(45) DEFAULT NULL,
  `id_cuenta_deposito` int(11) DEFAULT NULL,
  `id_usuario_creador` varchar(16) NOT NULL,
  `credito_dias` int(11) DEFAULT NULL,
  `credito_vencimiento` date DEFAULT NULL,
  `combinado_contado` tinyint(1) DEFAULT NULL,
  `combinado_tarjeta` tinyint(1) DEFAULT NULL,
  `combinado_credito` tinyint(1) DEFAULT NULL,
  `combinado_contado_tipo` varchar(1) DEFAULT NULL,
  `estado` varchar(1) NOT NULL DEFAULT 'E',
  PRIMARY KEY (`id_nota_debito`),
  KEY `id_empresa` (`id_empresa`),
  KEY `id_tarjeta_credito` (`id_tarjeta_credito`),
  KEY `fk_cnt_nota_debito_1_idx` (`id_usuario_creador`),
  KEY `cnt_nota_debito_ibfk_2` (`id_cliente`),
  KEY `cnt_nota_debito_ibfk_3` (`id_counter`),
  CONSTRAINT `cnt_nota_debito_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `tb_empresa` (`id_empresa`) ON DELETE NO ACTION,
  CONSTRAINT `cnt_nota_debito_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `cnt_cliente` (`id_cliente`) ON DELETE NO ACTION,
  CONSTRAINT `cnt_nota_debito_ibfk_3` FOREIGN KEY (`id_counter`) REFERENCES `cnt_promotor` (`id_promotor`) ON DELETE NO ACTION,
  CONSTRAINT `cnt_nota_debito_ibfk_4` FOREIGN KEY (`id_tarjeta_credito`) REFERENCES `cnt_tarjetas_credito` (`id_tarjeta_credito`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1000018 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_nota_debito_transaccion`
--

DROP TABLE IF EXISTS `cnt_nota_debito_transaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_nota_debito_transaccion` (
  `id_nota_debito_transaccion` int(11) NOT NULL AUTO_INCREMENT,
  `id_nota_debito` int(11) NOT NULL,
  `descripcion` varchar(64) DEFAULT NULL,
  `monto_bs` decimal(12,2) DEFAULT NULL,
  `monto_usd` decimal(12,2) DEFAULT NULL,
  `fecha_insert` timestamp NULL DEFAULT NULL,
  `gestion` int(6) NOT NULL,
  `id_boleto` int(11) DEFAULT NULL,
  `id_plan_cuentas` int(11) DEFAULT NULL,
  `estado` varchar(1) NOT NULL DEFAULT 'E',
  `tipo` varchar(1) DEFAULT 'B',
  `moneda` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_nota_debito_transaccion`),
  KEY `cnt_nota_debito_transaccion_ibfk_1` (`id_nota_debito`),
  KEY `fk_cnt_nota_debito_transaccion_1_idx` (`id_boleto`),
  KEY `fk_cnt_nota_debito_transaccion_2_idx` (`id_plan_cuentas`),
  CONSTRAINT `cnt_nota_debito_transaccion_ibfk_1` FOREIGN KEY (`id_nota_debito`) REFERENCES `cnt_nota_debito` (`id_nota_debito`) ON DELETE CASCADE,
  CONSTRAINT `fk_cnt_nota_debito_transaccion_1` FOREIGN KEY (`id_boleto`) REFERENCES `cnt_boleto` (`id_boleto`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_nota_debito_transaccion_2` FOREIGN KEY (`id_plan_cuentas`) REFERENCES `cnt_plan_cuentas` (`id_plan_cuentas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_parametros`
--

DROP TABLE IF EXISTS `cnt_parametros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_parametros` (
  `id_parametro` varchar(64) NOT NULL,
  `valor` varchar(128) NOT NULL,
  PRIMARY KEY (`id_parametro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_plan_cuentas`
--

DROP TABLE IF EXISTS `cnt_plan_cuentas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_plan_cuentas` (
  `id_plan_cuentas` int(11) NOT NULL AUTO_INCREMENT,
  `id_empresa` int(11) NOT NULL,
  `nro_plan_cuenta` bigint(20) DEFAULT NULL,
  `nro_plan_cuenta_padre` bigint(20) DEFAULT NULL,
  `cuenta` varchar(40) DEFAULT NULL,
  `aplica_movimiento` varchar(3) DEFAULT NULL,
  `moneda` varchar(2) DEFAULT NULL,
  `marco` varchar(2) DEFAULT NULL,
  `cta_itb` int(11) unsigned DEFAULT NULL,
  `nivel` int(2) DEFAULT NULL,
  `mantenimiento_valor` varchar(3) DEFAULT NULL,
  `saldo` decimal(16,2) DEFAULT NULL,
  `comodin` varchar(1) DEFAULT NULL COMMENT 'Esta columna sirve para el Comprobante para editar la fila cuando existan inconsistencias',
  PRIMARY KEY (`id_plan_cuentas`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `cnt_plan_cuentas_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `tb_empresa` (`id_empresa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1745 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_promotor`
--

DROP TABLE IF EXISTS `cnt_promotor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_promotor` (
  `id_promotor` int(11) NOT NULL AUTO_INCREMENT,
  `id_empresa` int(11) NOT NULL,
  `nombre` varchar(64) NOT NULL,
  `apellido` varchar(64) NOT NULL,
  `ci` varchar(12) NOT NULL,
  `direccion` varchar(128) DEFAULT NULL,
  `telefono` varchar(16) DEFAULT NULL,
  `celular` varchar(16) DEFAULT NULL,
  `comision_nac` decimal(6,2) DEFAULT NULL,
  `comision_int` decimal(6,2) DEFAULT NULL,
  `comision_nac_tipo` varchar(1) DEFAULT NULL,
  `comision_int_tipo` varchar(1) DEFAULT NULL,
  `reporta_total_ventas` tinyint(1) DEFAULT NULL,
  `observaciones` varchar(45) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `tipo` varchar(1) NOT NULL DEFAULT 'P',
  `sexo` varchar(1) NOT NULL DEFAULT 'H',
  `estado` varchar(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`id_promotor`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `cnt_promotor_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `tb_empresa` (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_relacion`
--

DROP TABLE IF EXISTS `cnt_relacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_relacion` (
  `id_empresa` bigint(20) DEFAULT NULL,
  `marco` bigint(20) DEFAULT NULL,
  `nivel` int(11) DEFAULT NULL,
  `cuenta` int(11) DEFAULT NULL,
  `cuenta_relacion` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_tarjetas_credito`
--

DROP TABLE IF EXISTS `cnt_tarjetas_credito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_tarjetas_credito` (
  `id_tarjeta_credito` int(11) NOT NULL AUTO_INCREMENT,
  `sigla` varchar(4) NOT NULL,
  `denominacion` varchar(45) NOT NULL,
  `plan_cuenta_mon_ext` bigint(20) DEFAULT NULL,
  `plan_cuenta_mon_nac` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_tarjeta_credito`),
  KEY `fk_cnt_tarjetas_credito_cnt_plan_cuentas1_idx` (`plan_cuenta_mon_ext`),
  KEY `fk_cnt_tarjetas_credito_cnt_plan_cuentas2_idx` (`plan_cuenta_mon_nac`),
  CONSTRAINT `fk_cnt_tarjetas_credito_cnt_plan_cuentas1` FOREIGN KEY (`plan_cuenta_mon_ext`) REFERENCES `cnt_plan_cuentas_bk` (`id_plan_cuentas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cnt_tarjetas_credito_cnt_plan_cuentas2` FOREIGN KEY (`plan_cuenta_mon_nac`) REFERENCES `cnt_plan_cuentas_bk` (`id_plan_cuentas`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnt_tipo_comprobante`
--

DROP TABLE IF EXISTS `cnt_tipo_comprobante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnt_tipo_comprobante` (
  `id_tipo_comprobante` varchar(2) NOT NULL,
  `valor` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_comprobante`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prueba_key`
--

DROP TABLE IF EXISTS `prueba_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prueba_key` (
  `id_prueba` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_prueba`)
) ENGINE=InnoDB AUTO_INCREMENT=330 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_contabilidad_boletaje`
--

DROP TABLE IF EXISTS `tb_contabilidad_boletaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_contabilidad_boletaje` (
  `id_empresa` int(11) NOT NULL,
  `id_total_boleto_bs` int(11) DEFAULT NULL,
  `id_total_boleto_us` int(11) DEFAULT NULL,
  `id_cuenta_fee` int(11) DEFAULT NULL,
  `id_descuentos` int(11) DEFAULT NULL COMMENT 'en esta tabla se registran las cuentas a las que estan vinculadas \nen los montos totales de los boletos',
  `emision_bolivianos` varchar(1) DEFAULT NULL,
  `emision_dolares` varchar(1) DEFAULT NULL,
  `cuenta_efectivo_debe_bs` int(11) DEFAULT NULL,
  `cuenta_efectivo_haber_bs` int(11) DEFAULT NULL,
  `cuenta_efectivo_debe_usd` int(11) DEFAULT NULL,
  `cuenta_efectivo_haber_usd` int(11) DEFAULT NULL,
  `cuenta_efectivo_no_bsp_debe_bs` int(11) DEFAULT NULL,
  `cuenta_efectivo_no_bsp_debe_usd` int(11) DEFAULT NULL,
  `tarjeta_credito_bsp_debe_bs` int(11) DEFAULT NULL,
  `tarjeta_credito_bsp_debe_usd` int(11) DEFAULT NULL,
  `cuenta_efectivo_no_bsp_haber_bs` int(11) DEFAULT NULL,
  `cuenta_efectivo_no_bsp_haber_usd` int(11) DEFAULT NULL,
  `tarjeta_credito_bsp_haber_bs` int(11) DEFAULT NULL,
  `tarjeta_credito_bsp_haber_usd` int(11) DEFAULT NULL,
  `deposito_banco_haber_bs` int(11) DEFAULT NULL,
  `deposito_banco_debe_bs` int(11) DEFAULT NULL,
  `deposito_banco_debe_usd` int(11) DEFAULT NULL,
  `deposito_banco_haber_usd` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_empresa`),
  UNIQUE KEY `id_empresa` (`id_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_empleado`
--

DROP TABLE IF EXISTS `tb_empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_empleado` (
  `id_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) NOT NULL,
  `apellido` varchar(64) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `fecha_alta` timestamp NULL DEFAULT NULL,
  `fecha_baja` timestamp NULL DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `id_empresa` int(11) NOT NULL,
  `cargo` varchar(45) DEFAULT NULL,
  `sexo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id_empleado`),
  KEY `fk_tb_empleado_tb_datos_empresa1_idx` (`id_empresa`),
  CONSTRAINT `fk_tb_empleado_tb_datos_empresa1` FOREIGN KEY (`id_empresa`) REFERENCES `tb_empresa` (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_empresa`
--

DROP TABLE IF EXISTS `tb_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_empresa` (
  `id_empresa` int(11) NOT NULL AUTO_INCREMENT,
  `razon_social` varchar(128) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nit` varchar(15) DEFAULT NULL,
  `telefono_fijo` varchar(10) DEFAULT NULL,
  `telefono_celular` varchar(8) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `pagina_web` varchar(128) DEFAULT NULL,
  `nro_iata` varchar(12) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL COMMENT 'principal solo deberia permitir una\nsucursal',
  `logo` mediumblob,
  PRIMARY KEY (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_formularios`
--

DROP TABLE IF EXISTS `tb_formularios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_formularios` (
  `id_modulo` int(11) NOT NULL,
  `id_formulario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) DEFAULT NULL,
  `url_acceso` varchar(128) DEFAULT NULL,
  `restful_url` varchar(128) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `fecha_alta` timestamp NULL DEFAULT NULL,
  `fecha_baja` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_formulario`),
  KEY `fk_tb_formularios_tb_modulos1_idx` (`id_modulo`),
  CONSTRAINT `fk_tb_formularios_tb_modulos1` FOREIGN KEY (`id_modulo`) REFERENCES `tb_modulos` (`id_modulo`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_log`
--

DROP TABLE IF EXISTS `tb_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_log` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_log` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `accion` varchar(16) DEFAULT NULL COMMENT 'CREAR\nACCEDER\nELIMINAR\nACTUALIZAR\nBUSCAR',
  `usuario` varchar(16) DEFAULT NULL,
  `formulario` varchar(64) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id_log`,`fecha_log`)
) ENGINE=InnoDB AUTO_INCREMENT=9613 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_mensajes`
--

DROP TABLE IF EXISTS `tb_mensajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_mensajes` (
  `id_mensaje` varchar(32) NOT NULL,
  `idioma` varchar(2) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_mensaje`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_modulos`
--

DROP TABLE IF EXISTS `tb_modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_modulos` (
  `id_modulo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(64) NOT NULL,
  `status` varchar(32) DEFAULT '0',
  `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_baja` timestamp NULL DEFAULT NULL,
  `class_menu` varchar(16) DEFAULT NULL,
  `url_acceso` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id_modulo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_password_recover`
--

DROP TABLE IF EXISTS `tb_password_recover`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_password_recover` (
  `id_password_recover` varchar(256) NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `fecha_acceso` datetime DEFAULT NULL,
  PRIMARY KEY (`id_password_recover`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_reportes`
--

DROP TABLE IF EXISTS `tb_reportes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_reportes` (
  `id_reporte` int(11) NOT NULL AUTO_INCREMENT,
  `id_formulario` int(11) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `icon` varchar(32) NOT NULL,
  `path` varchar(64) NOT NULL,
  `parametros` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id_reporte`),
  KEY `id_formulario` (`id_formulario`),
  CONSTRAINT `tb_reportes_ibfk_1` FOREIGN KEY (`id_formulario`) REFERENCES `tb_formularios` (`id_formulario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_rol`
--

DROP TABLE IF EXISTS `tb_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_rol` (
  `id_rol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `status` varchar(32) DEFAULT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_baja` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_rol_formulario`
--

DROP TABLE IF EXISTS `tb_rol_formulario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_rol_formulario` (
  `id_rol` int(11) NOT NULL,
  `id_formularios` int(11) NOT NULL,
  `crear` tinyint(4) DEFAULT NULL,
  `actualizar` tinyint(4) DEFAULT NULL,
  `eliminar` tinyint(4) DEFAULT NULL,
  `acceder` tinyint(4) DEFAULT NULL,
  `buscar` tinyint(4) DEFAULT NULL,
  `id_rol_formulario` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_rol_formulario`),
  KEY `fk_tb_rol_formulario_tb_rol1_idx` (`id_rol`),
  KEY `fk_tb_rol_formulario_tb_formularios1_idx` (`id_formularios`),
  CONSTRAINT `fk_tb_rol_formulario_tb_formularios1` FOREIGN KEY (`id_formularios`) REFERENCES `tb_formularios` (`id_formulario`),
  CONSTRAINT `fk_tb_rol_formulario_tb_rol1` FOREIGN KEY (`id_rol`) REFERENCES `tb_rol` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_status`
--

DROP TABLE IF EXISTS `tb_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_status` (
  `id_status` int(11) NOT NULL,
  `status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `user_name` varchar(16) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` varchar(32) DEFAULT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_baja` timestamp NULL DEFAULT NULL,
  `id_rol` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  PRIMARY KEY (`user_name`),
  KEY `fk_tb_user_tb_rol_idx` (`id_rol`),
  KEY `fk_tb_user_tb_empleado1_idx` (`id_empleado`),
  CONSTRAINT `fk_tb_user_tb_empleado1` FOREIGN KEY (`id_empleado`) REFERENCES `tb_empleado` (`id_empleado`),
  CONSTRAINT `fk_tb_user_tb_rol` FOREIGN KEY (`id_rol`) REFERENCES `tb_rol` (`id_rol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_login`
--

DROP TABLE IF EXISTS `tb_user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_login` (
  `id_user_login` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_logout` timestamp NULL DEFAULT NULL,
  `ip` varchar(16) DEFAULT NULL,
  `nro_intento` int(11) DEFAULT NULL,
  `user_name` varchar(16) NOT NULL,
  PRIMARY KEY (`id_user_login`),
  KEY `fk_tb_user_login_tb_user1_idx` (`user_name`),
  CONSTRAINT `fk_tb_user_login_tb_user1` FOREIGN KEY (`user_name`) REFERENCES `tb_user` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1310 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user_token`
--

DROP TABLE IF EXISTS `tb_user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_token` (
  `id_token` varchar(256) NOT NULL,
  `fecha_token` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(32) NOT NULL,
  `user_name` varchar(16) NOT NULL,
  PRIMARY KEY (`id_token`),
  KEY `fk_tb_user_token_tb_user1_idx` (`user_name`),
  CONSTRAINT `fk_tb_user_token_tb_user1` FOREIGN KEY (`user_name`) REFERENCES `tb_user` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_file`
--

DROP TABLE IF EXISTS `tmp_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(45) DEFAULT NULL,
  `content` text,
  `content_2` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_output`
--

DROP TABLE IF EXISTS `tmp_output`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_output` (
  `value` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tpm_plan`
--

DROP TABLE IF EXISTS `tpm_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tpm_plan` (
  `id_plan_cuentas` int(11) NOT NULL DEFAULT '0',
  `nro_plan_cuenta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'db_travel'
--
/*!50003 DROP FUNCTION IF EXISTS `number_to_string` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` FUNCTION `number_to_string`(n INT) RETURNS varchar(100) CHARSET latin1
BEGIN
    -- This function returns the string representation of a number.
    -- It's just an example... I'll restrict it to hundreds, but
    -- it can be extended easily.
    -- The idea is: 
    --      For each digit you need a position,
    --      For each position, you assign a string
    declare ans varchar(100);
    declare dig1, dig2, dig3, dig4, dig5, dig6, dig7 int; -- (one variable per digit)
	declare actual_value int;
    
    set ans = '';
    set actual_value = n ;	
    
    set dig7 = floor(actual_value/1000000);
    set actual_value = mod(actual_value,1000000);
    
    if dig7>0 then 
		case 
			when dig7 = 1 then set ans=concat(ans,' UN MILLON');
            when dig7 = 2 then set ans=concat(ans,' DOS MILLONES');
            when dig7 = 3 then set ans=concat(ans,' TRES MILLONES');
            when dig7 = 4 then set ans=concat(ans,' CUATRO MILLONES');
            when dig7 = 5 then set ans=concat(ans,' CINCO MILLONES');
            when dig7 = 6 then set ans=concat(ans,' SEIS MILLONES');
            when dig7 = 7 then set ans=concat(ans,' SIETE MILLONES');
            when dig7 = 8 then set ans=concat(ans,' OCHO MILLONES');
            when dig7 = 9 then set ans=concat(ans,' NUEVE MILLONES');		
        end case ;
    end if ;
    
    /*cien mil, diez mil, mil*/
    set dig6 = floor(actual_value / 100000);
    set actual_value = mod(actual_value,100000);
    
    if dig6 > 0 then
        case
            when dig6=1 then 
				if actual_value >999 then
					set ans=concat(ans, ' CIENTO');
				else 
					set ans=concat(ans, ' CIEN');
				end if;
                
            when dig6=2 then set ans=concat(ans, ' DOSCIENTOS');
            when dig6=3 then set ans=concat(ans, ' TRESCIENTOS');
            when dig6=4 then set ans=concat(ans, ' CUATROCIENTOS');
            when dig6=5 then set ans=concat(ans, ' QUINIENTOS');
            when dig6=6 then set ans=concat(ans, ' SEISCIENTOS');
            when dig6=7 then set ans=concat(ans, ' SETECIENTOS');
            when dig6=8 then set ans=concat(ans, ' OCHOCIENTOS');
            when dig6=9 then set ans=concat(ans, ' NOVECIENTOS');
            else set ans = ans;
        end case;
                
    end if;
    
    set dig5 = floor(actual_value/10000);
    
    set actual_value = mod(actual_value,10000);    
    
    set dig4 = floor(actual_value/1000);

    if dig5 = 1 then
        case
            when dig4 = 0 then set ans=concat(ans,' DIEZ');
            when dig4 = 1 then set ans=concat(ans,' ONCE');
            when dig4 = 2 then set ans=concat(ans,' DOCE');
            when dig4 = 3 then set ans=concat(ans,' TRECE');
            when dig4 = 4 then set ans=concat(ans,' CATORCE');
            when dig4 = 5 then set ans=concat(ans,' QUINCE');
            when dig4 = 6 then set ans=concat(ans,' DIECISEIS');
            when dig4 = 7 then set ans=concat(ans,' DIECISIETE');
            when dig4 = 8 then set ans=concat(ans,' DIECIOCHO');
            when dig4 = 9 then set ans=concat(ans,' DIECINUEVE');
            else set ans=ans;
        end case;
        
    else
        if dig5 > 0 then
            case
                when dig5=2 then 
					if (dig4 > 0 ) then
						set ans=concat(ans, ' VEINTI');
					else 
						set ans=concat(ans, ' VEINTE');
					end if ;
                when dig5=3 then set ans=concat(ans, ' TREINTA');
                when dig5=4 then set ans=concat(ans, ' CUARENTA');
                when dig5=5 then set ans=concat(ans, ' CINCUENTA');
                when dig5=6 then set ans=concat(ans, ' SESENTA');
                when dig5=7 then set ans=concat(ans, ' SETENTA');
                when dig5=8 then set ans=concat(ans, ' OCHENTA');
                when dig5=9 then set ans=concat(ans, ' NOVENTA');
                else set ans=ans;
            end case;
            if dig4 > 0 then
				if dig5 > 2 then
					set ans =concat(ans , ' Y');
                end if ;
			end if ;
        end if;
        if dig4 > 0 then
            case
                when dig4=1 then set ans=concat(ans, ' UN');
                when dig4=2 then set ans=concat(ans, ' DOS');
                when dig4=3 then set ans=concat(ans, ' TRES ');
                when dig4=4 then set ans=concat(ans, ' CUATRO');
                when dig4=5 then set ans=concat(ans, ' CINCO');
                when dig4=6 then set ans=concat(ans, ' SEIS');
                when dig4=7 then set ans=concat(ans, ' SIETE');
                when dig4=8 then set ans=concat(ans, ' OCHO');
                when dig4=9 then set ans=concat(ans, ' NUEVE');
                else set ans=ans;
            end case;
        end if;
    end if;

	if (actual_value > 1000) then
		set ans = concat(ans, ' MIL') ;
	end if ;
        
	set actual_value = mod(actual_value,1000);    
	/**
		centena, decena, unidad
    */
    set dig3 = floor(actual_value / 100);
    set actual_value = mod(actual_value,100);
    set dig2 = floor(actual_value/10);
    
    if dig3 > 0 then
        case
            when dig3=1 then 
				if actual_value>0  then
					set ans=concat(ans, ' CIENTO');
                else 
					set ans=concat(ans, ' CIEN');
				end if ;
            when dig3=2 then set ans=concat(ans, ' DOSCIENTOS');
            when dig3=3 then set ans=concat(ans, ' TRESCIENTOS');
            when dig3=4 then set ans=concat(ans, ' CUATROCIENTOS');
            when dig3=5 then set ans=concat(ans, ' QUINIENTOS');
            when dig3=6 then set ans=concat(ans, ' SEISCIENTOS');
            when dig3=7 then set ans=concat(ans, ' SETECIENTOS');
            when dig3=8 then set ans=concat(ans, ' OCHOCIENTOS');
            when dig3=9 then set ans=concat(ans, ' NOVECIENTOS');
            else set ans = ans;
        end case;
    end if;
    
    
    
    set actual_value = mod(actual_value,10);    
    set dig1 = actual_value;

    if dig2 = 1 then
        case
            when dig1 = 0 then set ans=concat(ans,' DIEZ');
            when dig1 = 1 then set ans=concat(ans,' ONCE');
            when dig1 = 2 then set ans=concat(ans,' DOCE');
            when dig1 = 3 then set ans=concat(ans,' TRECE');
            when dig1 = 4 then set ans=concat(ans,' CATORCE');
            when dig1 = 5 then set ans=concat(ans,' QUINCE');
            when dig1 = 6 then set ans=concat(ans,' DIECISEIS');
            when dig1 = 7 then set ans=concat(ans,' DIECISIETE');
            when dig1 = 8 then set ans=concat(ans,' DIECIOCHO');
            when dig1 = 9 then set ans=concat(ans,' DIECINUEVE');
            else set ans=ans;
        end case;
        
    else
        if dig2 > 0 then
            case
                when dig2=2 then 
					if (dig1 > 0 ) then
						set ans=concat(ans, ' VEINTI');
					else 
						set ans=concat(ans, ' VEINTE');
					end if ;
                when dig2=3 then set ans=concat(ans, ' TREINTA');
                when dig2=4 then set ans=concat(ans, ' CUARENTA');
                when dig2=5 then set ans=concat(ans, ' CINCUENTA');
                when dig2=6 then set ans=concat(ans, ' SESENTA');
                when dig2=7 then set ans=concat(ans, ' SETENTA');
                when dig2=8 then set ans=concat(ans, ' OCHENTA');
                when dig2=9 then set ans=concat(ans, ' NOVENTA');
                else set ans=ans;
            end case;
            if dig1 > 0 then
				if dig2 > 2 then
					set ans =concat(ans , ' Y');
                end if ;
			end if ;
        end if;
        if dig1 > 0 then
            case
                when dig1=1 then set ans=concat(ans, ' UNO');
                when dig1=2 then set ans=concat(ans, ' DOS');
                when dig1=3 then set ans=concat(ans, ' TRES');
                when dig1=4 then set ans=concat(ans, ' CUATRO');
                when dig1=5 then set ans=concat(ans, ' CINCO');
                when dig1=6 then set ans=concat(ans, ' SEIS');
                when dig1=7 then set ans=concat(ans, ' SIETE');
                when dig1=8 then set ans=concat(ans, ' OCHO');
                when dig1=9 then set ans=concat(ans, ' NUEVE');
                else set ans=ans;
            end case;
        end if;
    end if;

    return trim(ans);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `asociarBoletoNotaDebito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `asociarBoletoNotaDebito`(in in_id_nota_debito int 
, in in_id_boleto int, in in_id_cliente int , in in_id_counter int , in in_factor decimal(12,2) ,out out_id_transacion int)
BEGIN
DECLARE v_iata VARCHAR(4);
DECLARE v_tipo_cupon VARCHAR(2);
DECLARE v_numero bigint(18);
DECLARE v_tipo_boleto VARCHAR(2);
DECLARE v_ruta_1 VARCHAR(4);
DECLARE v_ruta_2 VARCHAR(4);
DECLARE v_ruta_3 VARCHAR(4);
DECLARE v_ruta_4 VARCHAR(4);
DECLARE v_ruta_5 VARCHAR(4);
DECLARE v_nombre_pasajero VARCHAR(64);
DECLARE v_moneda VARCHAR(2);
DECLARE v_gestion INT;
DECLARE v_total_boleto DECIMAL(12,2);
DECLARE v_id_transaccion INT;

DECLARE cur_boleto CURSOR FOR
	select a.numero, 
    b.tipo_cupon,
    b.numero,
    b.tipo_boleto,
	coalesce(b.id_ruta_1,'') id_ruta_1, 
	coalesce(b.id_ruta_2,'') id_ruta_2,
	coalesce(b.id_ruta_3,'') id_ruta_3,
	coalesce(b.id_ruta_4,'') id_ruta_4,
	coalesce(b.id_ruta_5,'') id_ruta_5,
	b.nombre_pasajero, 
    b.moneda, 
    b.total_boleto,
    b.gestion
	from cnt_boleto b
	inner join cnt_aerolinea a on a.id_aerolinea=b.id_aerolinea
	where b.id_boleto = in_id_boleto;

OPEN cur_boleto ;

FETCH cur_boleto into 
	v_iata, 
    v_tipo_cupon, 
    v_numero, 
    v_tipo_boleto,
	v_ruta_1, 
    v_ruta_2, 
    v_ruta_3, 
    v_ruta_4, 
    v_ruta_5,
	v_nombre_pasajero, 
    v_moneda, 
    v_total_boleto, 
    v_gestion;

IF v_tipo_boleto = 'SV' OR v_tipo_boleto = 'AV' OR  v_tipo_boleto = 'MV' THEN
	IF v_moneda = 'U' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_usd,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert)
		VALUES (
			in_id_nota_debito,
            concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero),
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now()
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
	ELSEIF v_moneda = 'B' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_bs,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert)
		VALUES (
			in_id_nota_debito,
            concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero),
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now()
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
    END IF ;
ELSEIF v_tipo_boleto = 'SA' OR v_tipo_boleto = 'AM' OR  v_tipo_boleto = 'MA' THEN
IF v_moneda = 'U' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_usd,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert)
		VALUES (
			in_id_nota_debito,
			concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5),            
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now()
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
	ELSEIF v_moneda = 'B' THEN
		INSERT INTO cnt_nota_debito_transaccion (
			id_nota_debito, 
            descripcion, 
            monto_bs,
            gestion,
            id_boleto,
            estado,
            tipo,
            moneda,
            fecha_insert)
		VALUES (
			in_id_nota_debito,
            concat(v_iata,'/','#',v_numero,'/',v_nombre_pasajero,'/',v_ruta_1,'/',v_ruta_2,'/',v_ruta_3,'/',v_ruta_4,'/',v_ruta_5),
            v_total_boleto,
            v_gestion,
            in_id_boleto,
            'P',
            'B',
            v_moneda,
            now()
        );
		SET v_id_transaccion = LAST_INSERT_ID() ;
    END IF ;
END IF ;

CLOSE cur_boleto ;

UPDATE cnt_boleto 
	SET id_nota_debito_transaccion = v_id_transaccion ,
		estado 					   = 'P' ,
        id_cliente				   = in_id_cliente,
		id_counter				   = in_id_counter,
        factor_cambiario		   = in_id_factor
WHERE id_boleto = in_id_boleto ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateComprobanteContable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateComprobanteContable`(IN in_id_boleto INT)
BEGIN
DECLARE v_id_libro INT ;
DECLARE v_total_debe_nac DECIMAL(12,2);
DECLARE v_total_haber_nac DECIMAL(12,2);
DECLARE v_total_debe_ext DECIMAL(12,2);
DECLARE v_total_haber_ext DECIMAL(12,2);
DECLARE done_boleto INT DEFAULT FALSE;
DECLARE hay_transacciones INT DEFAULT FALSE;

-- Los Comprobantes que pertenezcan al boleto y que esten EMITIDOS
DECLARE cur_libros_boleto CURSOR FOR
	SELECT DISTINCT id_libro 
    FROM cnt_asiento_contable 
    WHERE id_boleto = in_id_boleto 
    ORDER BY id_libro;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_boleto = TRUE;

OPEN cur_libros_boleto;

LOOP_BOLETO : LOOP 
	
	FETCH cur_libros_boleto into v_id_libro;
    -- INSERT INTO tmp_output values(concat('INGRESO LOOP BOLETO:', v_id_libro));
	BLOCK_ASIENTOS : BEGIN
		
		DECLARE done_asientos INT DEFAULT FALSE;
        DECLARE cur_asientos CURSOR FOR
		SELECT 
			SUM(monto_debe_nac) total_debe_nac ,
			SUM(monto_haber_nac) total_haber_nac ,
			SUM(monto_debe_ext) total_debe_ext ,
			SUM(monto_haber_ext) total_debe_ext 
		FROM cnt_asiento_contable ac
		INNER JOIN cnt_comprobante_contable cc on cc.id_libro = ac.id_libro
		WHERE ac.estado = 'E' and ac.id_libro = v_id_libro
		GROUP BY cc.tipo,cc.id_libro;
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_asientos = TRUE;
        
		IF done_boleto THEN
		LEAVE LOOP_BOLETO ;
		END IF;
        
        -- INSERT INTO tmp_output values('INGRESO BLOCK_ASIENTOS');	
        
        OPEN cur_asientos;
        LOOP_ASIENTOS :LOOP
			-- INSERT INTO tmp_output values('LOOP_ASIENTOS');	
			FETCH cur_asientos INTO v_total_debe_nac, v_total_haber_nac, v_total_debe_ext, v_total_haber_ext;
            
			-- INSERT INTO tmp_output values(concat('v_total_debe_nac'	, coalesce(v_total_debe_nac,'null')));
            -- INSERT INTO tmp_output values(concat('v_total_haber_nac', coalesce(v_total_haber_nac,'null') ));
            -- INSERT INTO tmp_output values(concat('v_total_debe_ext'	, coalesce(v_total_debe_ext,'null')));
            -- INSERT INTO tmp_output values(concat('v_total_haber_ext', coalesce(v_total_haber_ext,'null')));
            
			IF v_total_debe_nac is null AND v_total_haber_nac is null AND v_total_debe_ext is null AND v_total_haber_ext is null THEN
				-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
				UPDATE cnt_comprobante_contable 
					SET total_debe_nac 	= v_total_debe_nac,
						total_haber_nac = v_total_haber_nac,
						total_debe_ext 	= v_total_debe_ext,
						total_haber_ext = v_total_haber_ext,
						estado = 'A'
					WHERE id_libro = v_id_libro ;
			else
				-- INSERT INTO tmp_output values('UPDATE VALORES');	
				UPDATE cnt_comprobante_contable 
					SET total_debe_nac 	= v_total_debe_nac,
						total_haber_nac = v_total_haber_nac,
						total_debe_ext 	= v_total_debe_ext,
						total_haber_ext = v_total_haber_ext						
					WHERE id_libro = v_id_libro ;
            END IF ;
                    
			IF done_asientos THEN
			LEAVE LOOP_ASIENTOS ;
			END IF;
        
        END LOOP LOOP_ASIENTOS;
        CLOSE cur_asientos;
        
    END BLOCK_ASIENTOS;

END LOOP LOOP_BOLETO;
ClOSE cur_libros_boleto ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateIngresoCaja` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateIngresoCaja`(IN in_id_nota_transaccion INT)
BEGIN

DECLARE v_id_ingreso INT ;
DECLARE v_id_ingreso_transaccion INT ;

DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE done_ingresos INT DEFAULT FALSE;
DECLARE hay_transacciones INT DEFAULT FALSE;

-- Los id de los ingresos
DECLARE cur_ingresos CURSOR FOR
        SELECT id_ingreso_caja
        FROM cnt_ingreso_transaccion
        WHERE id_nota_transaccion = in_id_nota_transaccion ;
        
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_ingresos = TRUE;
        
-- actualizamos la transaccion del ingreso de caja

UPDATE cnt_ingreso_transaccion
SET estado = 'A' 
WHERE id_nota_transaccion = in_id_nota_transaccion ;

INSERT INTO tmp_output values(concat('INGRESO LOOP Transaccion:', in_id_nota_transaccion));
OPEN cur_ingresos;

LOOP_INGRESOS : LOOP 
	
	FETCH cur_ingresos into v_id_ingreso;
    INSERT INTO tmp_output values(concat('updateIngresoCaja:', v_id_ingreso));
    
    IF done_ingresos THEN
		LEAVE LOOP_INGRESOS ;
		END IF;
        
	BLOCK_INGRESOS : BEGIN		
		DECLARE done_transacciones INT DEFAULT FALSE;
        DECLARE cur_transacciones CURSOR FOR
        SELECT 			
            sum(monto_bs) 	monto_total_bs , 
            sum(monto_usd)	monto_total_usd
		FROM cnt_ingreso_transaccion it
			INNER JOIN cnt_ingreso_caja ic on ic.id_ingreso_caja= it.id_ingreso_caja
		WHERE it.estado = 'E' and it.id_ingreso_caja = v_id_ingreso;
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_transacciones = TRUE;
        
        OPEN cur_transacciones;
        LOOP_TRANSACCIONES :LOOP
			FETCH cur_transacciones INTO v_total_bs, v_total_usd;
            INSERT INTO tmp_output values(concat('INGRESO LOOP v_total_bs:', v_total_bs, ' v_total_usd:' , v_total_usd));
            
			IF v_total_bs is null AND v_total_usd is null THEN
				-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
				UPDATE cnt_ingreso_caja
					SET monto_abonado_bs 	= v_total_bs,
						monto_abonado_usd = v_total_usd,						
						estado = 'A'
					WHERE id_ingreso_caja = v_id_ingreso;
			else
				-- INSERT INTO tmp_output values('UPDATE VALORES');	
				UPDATE cnt_ingreso_caja 
					SET monto_abonado_bs 	= v_total_bs,
						monto_abonado_usd = v_total_usd						
					WHERE id_ingreso_caja = v_id_ingreso;
            END IF ;
                    
			IF done_transacciones THEN
			LEAVE LOOP_TRANSACCIONES ;
			END IF;
        
        END LOOP LOOP_TRANSACCIONES;
        CLOSE cur_transacciones;        
    END BLOCK_INGRESOS;

END LOOP LOOP_INGRESOS;

ClOSE cur_ingresos ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateMontosNotaDebitoEnPendiente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateMontosNotaDebitoEnPendiente`(in in_id_nota_debito INT)
BEGIN

DECLARE v_monto_bs 	DECIMAL(12,2);
DECLARE v_monto_usd DECIMAL(12,2);
DECLARE done INT DEFAULT FALSE;
DECLARE cur_transaccion CURSOR FOR
	select sum(monto_bs) monto_bs, sum(monto_usd)monto_usd 
	from cnt_nota_debito_transaccion
	where id_nota_debito= in_id_nota_debito and estado in('P');
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN cur_transaccion ;

FETCH cur_transaccion INTO v_monto_bs, v_monto_usd;
-- INSERT INTO tmp_output values( concat(done,  ' monto_Bs :', coalesce (v_monto_bs,'') ) );
-- IF done THEN
	UPDATE cnt_nota_debito 
	SET monto_total_bs = v_monto_bs,
		monto_total_usd = v_monto_usd
	WHERE id_nota_debito = in_id_nota_debito ;
-- END IF ;
CLOSE cur_transaccion ;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateNotaDebito` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`web_contabilidad`@`localhost` PROCEDURE `updateNotaDebito`(IN in_id_boleto INT)
BEGIN
DECLARE v_id_nota_debito INT ;
DECLARE v_id_nota_debito_transaccion INT ;

DECLARE v_total_bs DECIMAL(12,2);
DECLARE v_total_usd DECIMAL(12,2);
DECLARE done_nota_debito INT DEFAULT FALSE;
DECLARE hay_transacciones INT DEFAULT FALSE;

-- Los Comprobantes que pertenezcan al boleto y que esten EMITIDOS
DECLARE cur_nota_debito CURSOR FOR
SELECT DISTINCT id_nota_debito, id_nota_debito_transaccion
    FROM cnt_nota_debito_transaccion 
    WHERE id_boleto = in_id_boleto
    ORDER BY id_nota_debito;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_nota_debito = TRUE;

OPEN cur_nota_debito;

LOOP_NOTA_DEBITO : LOOP 
	
	FETCH cur_nota_debito into v_id_nota_debito, v_id_nota_debito_transaccion;
    -- INSERT INTO tmp_output values(concat('INGRESO LOOP BOLETO:', v_id_libro));
    IF done_nota_debito THEN
		LEAVE LOOP_NOTA_DEBITO ;
		END IF;
        
	BLOCK_NOTAS : BEGIN		
		DECLARE done_transacciones INT DEFAULT FALSE;
        DECLARE cur_transacciones CURSOR FOR
		SELECT 			
            sum(monto_bs) 	monto_total_bs , 
            sum(monto_usd)	monto_total_usd
		FROM cnt_nota_debito_transaccion nt
			INNER JOIN cnt_nota_debito nd on nd.id_nota_debito= nt.id_nota_debito
		WHERE nt.estado = 'E' and nd.id_nota_debito = v_id_nota_debito;
        
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_transacciones = TRUE;
        
        OPEN cur_transacciones;
        LOOP_TRANSACCIONES :LOOP
			FETCH cur_transacciones INTO v_total_bs, v_total_usd;
            
			IF v_total_bs is null AND v_total_usd is null THEN
				-- INSERT INTO tmp_output values('UPDATE WITH ESTADO');	
				UPDATE cnt_nota_debito 
					SET monto_total_bs 	= v_total_bs,
						monto_total_usd = v_total_usd,						
						estado = 'A'
					WHERE id_nota_debito = v_id_nota_debito;
			else
				-- INSERT INTO tmp_output values('UPDATE VALORES');	
				UPDATE cnt_nota_debito 
					SET monto_total_bs 	= v_total_bs,
						monto_total_usd = v_total_usd						
					WHERE id_nota_debito = v_id_nota_debito ;
            END IF ;
                    
			IF done_transacciones THEN
			LEAVE LOOP_TRANSACCIONES ;
			END IF;
        
        END LOOP LOOP_TRANSACCIONES;
        CLOSE cur_transacciones;        
    END BLOCK_NOTAS;
    
    /* Para los ingresos*/
    INSERT INTO tmp_output values(concat('CALL updateIngresoCaja:'));
   CALL updateIngresoCaja (v_id_nota_debito_transaccion) ;
   
END LOOP LOOP_NOTA_DEBITO;
ClOSE cur_nota_debito ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-06  1:55:55

package com.agencia.entities;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.Cliente;
import com.agencia.entities.Promotor;
import com.configuracion.entities.ArchivoBoleto;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-07T23:16:51")
@StaticMetamodel(Boleto.class)
public class Boleto_ { 

    public static volatile SingularAttribute<Boleto, BigDecimal> montoFee;
    public static volatile SingularAttribute<Boleto, String> estado;
    public static volatile SingularAttribute<Boleto, Integer> idNotaDebitoTransaccion;
    public static volatile SingularAttribute<Boleto, String> combinadoContadoNroDeposito;
    public static volatile SingularAttribute<Boleto, Integer> combinadoContadoIdBanco;
    public static volatile SingularAttribute<Boleto, BigDecimal> fee;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuesto1;
    public static volatile SingularAttribute<Boleto, Integer> tarjetaId;
    public static volatile SingularAttribute<Boleto, Date> fechaInsert;
    public static volatile SingularAttribute<Boleto, BigDecimal> combinadoCreditoMonto;
    public static volatile SingularAttribute<Boleto, Date> combinadoCreditoVencimiento;
    public static volatile SingularAttribute<Boleto, Integer> contadoIdBanco;
    public static volatile SingularAttribute<Boleto, String> combinadoContadoTipo;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuestoBob;
    public static volatile SingularAttribute<Boleto, String> combinadoTarjetaNumero;
    public static volatile SingularAttribute<Boleto, Aerolinea> idAerolinea;
    public static volatile SingularAttribute<Boleto, Long> numeroOrigen;
    public static volatile SingularAttribute<Boleto, ArchivoBoleto> idArchivo;
    public static volatile SingularAttribute<Boleto, Integer> idEmpresa;
    public static volatile SingularAttribute<Boleto, Integer> creditoDias;
    public static volatile SingularAttribute<Boleto, String> impuesto3nombre;
    public static volatile SingularAttribute<Boleto, Integer> idBoletoPadre;
    public static volatile SingularAttribute<Boleto, Date> fechaViaje;
    public static volatile SingularAttribute<Boleto, BigDecimal> importeNeto;
    public static volatile SingularAttribute<Boleto, String> impuesto2nombre;
    public static volatile SingularAttribute<Boleto, String> tipoCupon;
    public static volatile SingularAttribute<Boleto, String> idRuta4;
    public static volatile SingularAttribute<Boleto, String> emision;
    public static volatile SingularAttribute<Boleto, String> idRuta3;
    public static volatile SingularAttribute<Boleto, Integer> combinadoContadoIdCuenta;
    public static volatile SingularAttribute<Boleto, BigDecimal> totalMontoCobrar;
    public static volatile SingularAttribute<Boleto, String> idRuta5;
    public static volatile SingularAttribute<Boleto, Short> combinadoTarjeta;
    public static volatile SingularAttribute<Boleto, String> idRuta2;
    public static volatile SingularAttribute<Boleto, Date> fechaEmision;
    public static volatile SingularAttribute<Boleto, String> idRuta1;
    public static volatile SingularAttribute<Boleto, BigDecimal> montoComision;
    public static volatile SingularAttribute<Boleto, BigDecimal> montoDescuento;
    public static volatile SingularAttribute<Boleto, BigDecimal> factorCambiario;
    public static volatile SingularAttribute<Boleto, Short> combinadoContado;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuesto5;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuesto4;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuesto3;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuesto2;
    public static volatile SingularAttribute<Boleto, BigDecimal> totalBoleto;
    public static volatile SingularAttribute<Boleto, String> formaPago;
    public static volatile SingularAttribute<Boleto, String> nombrePasajero;
    public static volatile SingularAttribute<Boleto, Integer> combinadoTarjetaId;
    public static volatile SingularAttribute<Boleto, Long> numero;
    public static volatile SingularAttribute<Boleto, Integer> idBoleto;
    public static volatile SingularAttribute<Boleto, Integer> idIngresoCajaTransaccion;
    public static volatile SingularAttribute<Boleto, Integer> idNotaDebito;
    public static volatile SingularAttribute<Boleto, String> tipoBoleto;
    public static volatile SingularAttribute<Boleto, String> impuesto1nombre;
    public static volatile SingularAttribute<Boleto, String> contadoNroDeposito;
    public static volatile SingularAttribute<Boleto, Integer> idIngresoCaja;
    public static volatile SingularAttribute<Boleto, Short> combinadoCredito;
    public static volatile SingularAttribute<Boleto, String> impuesto5nombre;
    public static volatile SingularAttribute<Boleto, String> contadoTipo;
    public static volatile SingularAttribute<Boleto, Date> creditoVencimiento;
    public static volatile SingularAttribute<Boleto, Integer> combinadoCreditoDias;
    public static volatile SingularAttribute<Boleto, Integer> idLibro;
    public static volatile SingularAttribute<Boleto, Cliente> idCliente;
    public static volatile SingularAttribute<Boleto, BigDecimal> comision;
    public static volatile SingularAttribute<Boleto, String> idUsuarioCreador;
    public static volatile SingularAttribute<Boleto, String> moneda;
    public static volatile SingularAttribute<Boleto, BigDecimal> combinadoTarjetaMonto;
    public static volatile SingularAttribute<Boleto, BigDecimal> descuento;
    public static volatile SingularAttribute<Boleto, BigDecimal> combinadoContadoMonto;
    public static volatile SingularAttribute<Boleto, BigDecimal> impuestoQm;
    public static volatile SingularAttribute<Boleto, Integer> gestion;
    public static volatile SingularAttribute<Boleto, Integer> contadoIdCuenta;
    public static volatile SingularAttribute<Boleto, String> contadoNroCheque;
    public static volatile SingularAttribute<Boleto, BigDecimal> montoPagarLineaAerea;
    public static volatile SingularAttribute<Boleto, String> combinadoContadoNroCheque;
    public static volatile SingularAttribute<Boleto, String> tarjetaNumero;
    public static volatile SingularAttribute<Boleto, String> impuesto4nombre;
    public static volatile SingularAttribute<Boleto, String> combinadoTipo;
    public static volatile SingularAttribute<Boleto, Promotor> idPromotor;

}
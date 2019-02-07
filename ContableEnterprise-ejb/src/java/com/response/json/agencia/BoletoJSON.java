/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.agencia.entities.Promotor;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.Contabilidad;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author xeio
 */
public class BoletoJSON implements Serializable {

    //id de la tabla
    private Integer idBoleto;
    private Integer gestion;
    private ComboSelect idAerolinea;
    private Integer idEmpresa;
    private ComboSelect idPromotor;
    private Integer idLibro;
    private Integer idIngresoCajaTransaccion;
    private Integer idIngresoCaja;
    private ComboSelect idCliente;
    private String nombrePasajero;
    private Integer idBoletoPadre;

    private String emision;
    private String tipoBoleto;
    private String tipoCupon;
    private Long numero;
    private Long numeroOrigen;
    //rutas
    private ComboSelect idRuta1;
    private ComboSelect idRuta2;
    private ComboSelect idRuta3;
    private ComboSelect idRuta4;
    private ComboSelect idRuta5;

    private String fechaEmision;
    private String fechaViaje;

    private BigDecimal factorCambiario;
    private String moneda;

    private String impuesto1nombre;
    private String impuesto2nombre;
    private String impuesto3nombre;
    private String impuesto4nombre;
    private String impuesto5nombre;
    // montos
    private BigDecimal importeNetoBs;
    private BigDecimal importeNetoUsd;
    private BigDecimal impuestoBobBs;
    private BigDecimal impuestoBobUsd;
    private BigDecimal impuestoQmBs;
    private BigDecimal impuestoQmUsd;
    private BigDecimal impuesto1Bs;
    private BigDecimal impuesto1Usd;
    private BigDecimal impuesto2Bs;
    private BigDecimal impuesto2Usd;
    private BigDecimal impuesto3Bs;
    private BigDecimal impuesto3Usd;
    private BigDecimal impuesto4Bs;
    private BigDecimal impuesto4Usd;
    private BigDecimal impuesto5Bs;
    private BigDecimal impuesto5Usd;
    private BigDecimal totalBoletoBs;
    private BigDecimal totalBoletoUsd;
    private BigDecimal comision;
    private BigDecimal montoComisionBs;
    private BigDecimal montoComisionUsd;
    private BigDecimal fee;
    private BigDecimal montoFeeBs;
    private BigDecimal montoFeeUsd;
    private BigDecimal descuento;
    private BigDecimal montoDescuentoBs;
    private BigDecimal montoDescuentoUsd;
    private BigDecimal totalMontoCobrarBs;
    private BigDecimal totalMontoCobrarUsd;
    private BigDecimal montoPagarLineaAereaBs;
    private BigDecimal montoPagarLineaAereaUsd;

    private String estado;
    private Integer idNotaDebitoTransaccion;
    private Integer idNotaDebito;
    private String fechaInsert;

    private String idUsuarioCreador;

    //formas de pago
    private String formaPago;
    private Integer creditoDias;
    private String creditoVencimiento;
    private String contadoTipo;
    private String contadoNroCheque;
    private Integer contadoIdBanco;
    private String contadoNroDeposito;
    private ComboSelect contadoIdCuenta;

    private String tarjetaNumero;
    private Integer tarjetaId;
    private String combinadoTipo;
    private boolean combinadoCredito;
    private Integer combinadoCreditoDias;
    private String combinadoCreditoVencimiento;
    private BigDecimal combinadoCreditoMonto;
    private boolean combinadoContado;
    private String combinadoContadoTipo;
    private BigDecimal combinadoContadoMonto;
    private String combinadoContadoNroCheque;
    private Integer combinadoContadoIdBanco;
    private String combinadoContadoNroDeposito;
    private boolean combinadoTarjeta;
    private Integer combinadoTarjetaId;
    private String combinadoTarjetaNumero;
    private BigDecimal combinadoTarjetaMonto;

    public static Boleto toNewBoleto(BoletoJSON bjson) throws CRUDException {
        Boleto boleto = new Boleto();

        boleto.setGestion(Integer.parseInt(DateContable.getPartitionDate(DateContable.getCurrentDateStr(DateContable.LATIN_AMERICA_FORMAT))));;
        boleto.setIdAerolinea(new Aerolinea(((Double) bjson.getIdAerolinea().getId()).intValue()));
        //boleto.setIdAerolinea(((Double) bjson.getIdAerolinea().getId()).intValue());
        boleto.setIdBoleto(bjson.getIdBoleto());
        boleto.setIdEmpresa(bjson.getIdEmpresa());
        boleto.setEmision(bjson.getEmision());
        boleto.setTipoBoleto(bjson.getTipoBoleto());
        boleto.setTipoCupon(bjson.getTipoCupon());
        boleto.setNumero(bjson.getNumero());
        boleto.setNumeroOrigen(bjson.getNumeroOrigen());
        Promotor p = new Promotor(((Double) bjson.getIdPromotor().getId()).intValue());
        boleto.setIdPromotor(p);
        boleto.setEstado(bjson.getEstado());
        boleto.setIdBoletoPadre(bjson.getIdBoletoPadre());
        boleto.setIdLibro(bjson.getIdLibro());
        boleto.setIdIngresoCaja(bjson.getIdIngresoCaja());
        boleto.setIdIngresoCajaTransaccion(bjson.getIdIngresoCajaTransaccion());
        boleto.setIdNotaDebito(bjson.getIdNotaDebito());
        boleto.setIdNotaDebitoTransaccion(bjson.getIdNotaDebitoTransaccion());

        // rutas
        boleto.setIdRuta1(bjson.getIdRuta1() != null ? (String) bjson.getIdRuta1().getId() : null);
        boleto.setIdRuta2(bjson.getIdRuta2() != null ? (String) bjson.getIdRuta2().getId() : null);
        boleto.setIdRuta3(bjson.getIdRuta3() != null ? (String) bjson.getIdRuta3().getId() : null);
        boleto.setIdRuta4(bjson.getIdRuta4() != null ? (String) bjson.getIdRuta4().getId() : null);
        boleto.setIdRuta5(bjson.getIdRuta5() != null ? (String) bjson.getIdRuta5().getId() : null);

        //impuestos
        boleto.setImpuesto1nombre(bjson.getImpuesto1nombre() != null ? bjson.getImpuesto1nombre() : null);
        boleto.setImpuesto2nombre(bjson.getImpuesto2nombre() != null ? bjson.getImpuesto2nombre() : null);
        boleto.setImpuesto3nombre(bjson.getImpuesto3nombre() != null ? bjson.getImpuesto3nombre() : null);
        boleto.setImpuesto4nombre(bjson.getImpuesto4nombre() != null ? bjson.getImpuesto4nombre() : null);
        boleto.setImpuesto5nombre(bjson.getImpuesto5nombre() != null ? bjson.getImpuesto5nombre() : null);
        
       

        //cliente
        //boleto.setIdCliente(((Double) bjson.getIdCliente().getId()).intValue());
        if (bjson.getIdCliente() != null) {
            boleto.setIdCliente(new Cliente((((Double) bjson.getIdCliente().getId()).intValue())));
        }

        if (bjson.getTipoBoleto().equals(Boleto.Tipo.AMADEUS)
                || bjson.getTipoBoleto().equals(Boleto.Tipo.MANUAL)
                || bjson.getTipoBoleto().equals(Boleto.Tipo.SABRE)) {
            if (bjson.getNombrePasajero() != null) {
                boleto.setNombrePasajero(bjson.getNombrePasajero().toUpperCase());
            } else {
                throw new CRUDException("Nombre del Pasajero es requerido");
            }
        }

        boleto.setFechaEmision(DateContable.toLatinAmericaDateFormat(bjson.getFechaEmision()));
        boleto.setFechaViaje(DateContable.toLatinAmericaDateFormat(bjson.getFechaViaje()));
        boleto.setFechaInsert(DateContable.getCurrentDate());

        boleto.setFactorCambiario(bjson.getFactorCambiario());
        boleto.setIdUsuarioCreador(bjson.getIdUsuarioCreador());

        boleto.setComision(bjson.getComision());
        boleto.setFee(bjson.getFee());
        boleto.setDescuento(bjson.getDescuento());
        //Dolares
        if (bjson.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            boleto.setImporteNeto(bjson.getImporteNetoUsd());
            boleto.setImpuestoBob(bjson.getImpuestoBobUsd());
            boleto.setImpuestoQm(bjson.getImpuestoQmUsd());
            boleto.setImpuesto1(bjson.getImpuesto1Usd());
            boleto.setImpuesto2(bjson.getImpuesto2Usd());
            boleto.setImpuesto3(bjson.getImpuesto3Usd());
            boleto.setImpuesto4(bjson.getImpuesto4Usd());
            boleto.setImpuesto5(bjson.getImpuesto5Usd());
            boleto.setTotalBoleto(bjson.getTotalBoletoUsd());
            boleto.setMontoComision(bjson.getMontoComisionUsd());
            boleto.setMontoFee(bjson.getMontoFeeUsd());
            boleto.setMontoDescuento(bjson.getMontoDescuentoUsd());
            boleto.setTotalMontoCobrar(bjson.getTotalMontoCobrarUsd());
             boleto.setMontoPagarLineaAerea(bjson.getMontoPagarLineaAereaUsd());
        } else {//Bolivianos
            boleto.setImporteNeto(bjson.getImporteNetoBs());
            boleto.setImpuestoBob(bjson.getImpuestoBobBs());
            boleto.setImpuestoQm(bjson.getImpuestoQmBs());
            boleto.setImpuesto1(bjson.getImpuesto1Bs());
            boleto.setImpuesto2(bjson.getImpuesto2Bs());
            boleto.setImpuesto3(bjson.getImpuesto3Bs());
            boleto.setImpuesto4(bjson.getImpuesto4Bs());
            boleto.setImpuesto5(bjson.getImpuesto5Bs());
            boleto.setTotalBoleto(bjson.getTotalBoletoBs());
            boleto.setMontoComision(bjson.getMontoComisionBs());
            boleto.setMontoFee(bjson.getMontoFeeBs());
            boleto.setMontoDescuento(bjson.getMontoDescuentoBs());
            boleto.setTotalMontoCobrar(bjson.getTotalMontoCobrarBs());
            boleto.setMontoPagarLineaAerea(bjson.getMontoPagarLineaAereaBs());
        }

        //formas de pago
        boleto.setFormaPago(bjson.getFormaPago());
        boleto.setMoneda(bjson.moneda);

        if (bjson.getFormaPago() != null) {

            switch (bjson.getFormaPago()) {
                case FormasPago.CREDITO:
                    boleto.setCreditoDias(bjson.getCreditoDias());
                    boleto.setCreditoVencimiento(DateContable.toLatinAmericaDateFormat(bjson.getCreditoVencimiento()));
                    break;
                case FormasPago.TARJETA:
                    boleto.setTarjetaId(bjson.getTarjetaId());
                    boleto.setTarjetaNumero(bjson.getTarjetaNumero());
                    break;
                case FormasPago.CONTADO:
                    boleto.setContadoTipo(bjson.getContadoTipo());
                    if (bjson.getContadoTipo().equals(FormasPago.CHEQUE)) {
                        boleto.setContadoNroCheque(bjson.getContadoNroCheque());
                        boleto.setContadoIdBanco(bjson.getContadoIdBanco());
                    } else if (bjson.getContadoTipo().equals(FormasPago.DEPOSITO)) {
                        boleto.setContadoNroDeposito(bjson.getContadoNroDeposito());
                        boleto.setContadoIdCuenta(((Double) bjson.getContadoIdCuenta().getId()).intValue());
                    }
                    break;
                case FormasPago.COMBINADO:
                    boleto.setCombinadoTipo(bjson.getCombinadoTipo());
                    if (bjson.isCombinadoContado()) {
                        boleto.setCombinadoContadoTipo(bjson.getCombinadoContadoTipo());
                        boleto.setCombinadoContadoMonto(bjson.getCombinadoContadoMonto());
                        switch (bjson.getCombinadoContadoTipo()) {
                            case FormasPago.EFECTIVO:
                                break;
                            case FormasPago.CHEQUE:
                                boleto.setCombinadoContadoIdBanco(bjson.getCombinadoContadoIdBanco());
                                boleto.setCombinadoContadoNroCheque(bjson.getCombinadoContadoNroCheque());
                                break;
                            case FormasPago.DEPOSITO:
                                boleto.setCombinadoContadoIdBanco(bjson.getCombinadoContadoIdBanco());
                                boleto.setCombinadoContadoNroDeposito(bjson.getCombinadoContadoNroDeposito());
                                break;
                        }
                    }
                    if (bjson.isCombinadoCredito()) {
                        boleto.setCombinadoCreditoDias(bjson.getCombinadoCreditoDias());
                        boleto.setCombinadoCreditoVencimiento(DateContable.toLatinAmericaDateFormat(bjson.getCombinadoCreditoVencimiento()));
                        boleto.setCombinadoCreditoMonto(bjson.getCombinadoCreditoMonto());
                    }
                    if (bjson.isCombinadoTarjeta()) {
                        boleto.setCombinadoTarjetaId(bjson.getCombinadoTarjetaId());
                        boleto.setCombinadoTarjetaNumero(bjson.getCombinadoTarjetaNumero());
                        boleto.setCombinadoTarjetaMonto(bjson.getCombinadoTarjetaMonto());
                    }
                    break;
            }
        }

        return boleto;
    }

    public static BoletoJSON toBoletoJSON(Boleto boleto) {
        BoletoJSON bjson = new BoletoJSON();

        bjson.setGestion(boleto.getGestion());
        if (boleto.getIdAerolinea() != null) {
            bjson.setIdAerolinea(new ComboSelect(boleto.getIdAerolinea().getIdAerolinea(),
                    boleto.getIdAerolinea().getNombre(),
                    boleto.getIdAerolinea().getNumero()));
        }
        bjson.setIdEmpresa(boleto.getIdEmpresa());
        bjson.setEmision(boleto.getEmision());
        bjson.setTipoBoleto(boleto.getTipoBoleto());
        bjson.setTipoCupon(boleto.getTipoCupon());
        bjson.setNumero(boleto.getNumero());
        bjson.setNumeroOrigen(boleto.getNumeroOrigen());
        if (boleto.getIdPromotor() != null) {
            bjson.setIdPromotor(new ComboSelect(boleto.getIdPromotor().getIdPromotor()));
        }

        bjson.setEstado(boleto.getEstado());
        bjson.setIdNotaDebitoTransaccion(boleto.getIdNotaDebitoTransaccion());
        bjson.setIdBoleto(boleto.getIdBoleto());
        bjson.setIdBoletoPadre(boleto.getIdBoletoPadre());
        //System.out.println(boleto.getIdBoletoPadre());
        bjson.setIdLibro(boleto.getIdLibro());
        bjson.setIdIngresoCaja(boleto.getIdIngresoCaja());
        bjson.setIdNotaDebito(boleto.getIdNotaDebito());

        // rutas
        bjson.setIdRuta1(boleto.getIdRuta1() != null ? new ComboSelect(boleto.getIdRuta1()) : null);
        bjson.setIdRuta2(boleto.getIdRuta2() != null ? new ComboSelect(boleto.getIdRuta2()) : null);
        bjson.setIdRuta3(boleto.getIdRuta3() != null ? new ComboSelect(boleto.getIdRuta3()) : null);
        bjson.setIdRuta4(boleto.getIdRuta4() != null ? new ComboSelect(boleto.getIdRuta4()) : null);
        bjson.setIdRuta5(boleto.getIdRuta5() != null ? new ComboSelect(boleto.getIdRuta5()) : null);

        //IMPUESTOS
        bjson.setImpuesto1nombre(boleto.getImpuesto1nombre() != null ? boleto.getImpuesto1nombre() : null);
        bjson.setImpuesto2nombre(boleto.getImpuesto2nombre() != null ? boleto.getImpuesto2nombre() : null);
        bjson.setImpuesto3nombre(boleto.getImpuesto3nombre() != null ? boleto.getImpuesto3nombre() : null);
        bjson.setImpuesto4nombre(boleto.getImpuesto4nombre() != null ? boleto.getImpuesto4nombre() : null);
        bjson.setImpuesto5nombre(boleto.getImpuesto5nombre() != null ? boleto.getImpuesto5nombre() : null);

        //cliente
        if (boleto.getIdCliente() != null) {
            bjson.setIdCliente(new ComboSelect(boleto.getIdCliente().getIdCliente(), boleto.getIdCliente().getNombre()));
        }

        if (boleto.getNombrePasajero() != null) {
            bjson.setNombrePasajero(boleto.getNombrePasajero().toUpperCase());
        }

        bjson.setFechaEmision(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        bjson.setFechaViaje(DateContable.getDateFormat(boleto.getFechaViaje(), DateContable.LATIN_AMERICA_FORMAT));
        bjson.setFechaInsert(DateContable.getDateFormat(boleto.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));

        bjson.setFactorCambiario(boleto.getFactorCambiario());
        bjson.setIdUsuarioCreador(boleto.getIdUsuarioCreador());

        bjson.setComision(boleto.getComision());
        bjson.setFee(boleto.getFee());
        bjson.setDescuento(boleto.getDescuento());
        bjson.setMoneda(boleto.getMoneda());
        //Dolares
        if (boleto.getTipoCupon() != null) {
            if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
                bjson.setImporteNetoUsd(boleto.getImporteNeto());
                bjson.setImpuestoBobUsd(boleto.getImpuestoBob());
                bjson.setImpuestoQmUsd(boleto.getImpuestoQm());
                bjson.setImpuesto1Usd(boleto.getImpuesto1());
                bjson.setImpuesto2Usd(boleto.getImpuesto2());
                bjson.setImpuesto3Usd(boleto.getImpuesto3());
                bjson.setImpuesto4Usd(boleto.getImpuesto4());
                bjson.setImpuesto5Usd(boleto.getImpuesto5());
                bjson.setTotalBoletoUsd(boleto.getTotalBoleto());
                bjson.setMontoComisionUsd(boleto.getMontoComision());
                bjson.setMontoFeeUsd(boleto.getMontoFee());
                bjson.setMontoDescuentoUsd(boleto.getMontoDescuento());
                bjson.setTotalMontoCobrarUsd(boleto.getTotalMontoCobrar());
                bjson.setMontoPagarLineaAereaUsd(boleto.getMontoPagarLineaAerea());

                if (boleto.getMontoComision() != null) {
                    if (boleto.getTotalBoleto() != null) {
                        bjson.setMontoPagarLineaAereaUsd(boleto.getTotalBoleto().subtract(boleto.getMontoComision()));
                    }
                } else {
                    bjson.setMontoPagarLineaAereaUsd(boleto.getTotalBoleto());
                }

                if (boleto.getImporteNeto() != null) {
                    bjson.setImporteNetoBs(boleto.getImporteNeto().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }
                if (boleto.getImpuestoBob() != null) {
                    bjson.setImpuestoBobBs(boleto.getImpuestoBob().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuestoQm() != null) {
                    bjson.setImpuestoQmBs(boleto.getImpuestoQm().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto1() != null) {
                    bjson.setImpuesto1Bs(boleto.getImpuesto1().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto2() != null) {
                    bjson.setImpuesto2Bs(boleto.getImpuesto2().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto3() != null) {
                    bjson.setImpuesto3Bs(boleto.getImpuesto3().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto4() != null) {
                    bjson.setImpuesto4Bs(boleto.getImpuesto4().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto5() != null) {
                    bjson.setImpuesto5Bs(boleto.getImpuesto5().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getTotalBoleto() != null) {
                    bjson.setTotalBoletoBs(boleto.getTotalBoleto().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getMontoComision() != null) {
                    bjson.setMontoComisionBs(boleto.getMontoComision().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getMontoFee() != null) {
                    bjson.setMontoFeeBs(boleto.getMontoFee().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }
                if (boleto.getMontoDescuento() != null) {
                    bjson.setMontoDescuentoBs(boleto.getMontoDescuento().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getTotalMontoCobrar() != null) {
                    bjson.setTotalMontoCobrarBs(boleto.getTotalMontoCobrar().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }
                if (boleto.getMontoComision() != null) {
                    if (boleto.getTotalBoleto() != null) {
                        bjson.setMontoPagarLineaAereaBs(boleto.getTotalBoleto().subtract(boleto.getMontoComision()).multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                    }
                } else {
                    if (boleto.getTotalBoleto() != null) {
                        bjson.setMontoPagarLineaAereaBs(boleto.getTotalBoleto().multiply(boleto.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                    }
                }

            } else {//Bolivianos
                bjson.setImporteNetoBs(boleto.getImporteNeto());
                bjson.setImpuestoBobBs(boleto.getImpuestoBob());
                bjson.setImpuestoQmBs(boleto.getImpuestoQm());
                bjson.setImpuesto1Bs(boleto.getImpuesto1());
                bjson.setImpuesto2Bs(boleto.getImpuesto2());
                bjson.setImpuesto3Bs(boleto.getImpuesto3());
                bjson.setImpuesto4Bs(boleto.getImpuesto4());
                bjson.setImpuesto5Bs(boleto.getImpuesto5());
                bjson.setTotalBoletoBs(boleto.getTotalBoleto());
                bjson.setMontoComisionBs(boleto.getMontoComision());
                bjson.setMontoFeeBs(boleto.getMontoFee());
                bjson.setMontoDescuentoBs(boleto.getMontoDescuento());
                bjson.setTotalMontoCobrarBs(boleto.getTotalMontoCobrar());
                bjson.setMontoPagarLineaAereaBs(boleto.getMontoPagarLineaAerea());

                if (boleto.getMontoComision() != null) {
                    if (boleto.getTotalBoleto() != null) {
                        bjson.setMontoPagarLineaAereaBs(boleto.getTotalBoleto().subtract(boleto.getMontoComision()));
                    }
                } else {
                    bjson.setMontoPagarLineaAereaBs(boleto.getTotalBoleto());
                }

                System.out.println("Importe Neto :" + boleto.getImporteNeto());
                System.out.println("Factor Cambiario :" + boleto.getFactorCambiario());
                //System.out.println("Divide :" + boleto.getImporteNeto().doubleValue() / boleto.getFactorCambiario().doubleValue());

                if (boleto.getImporteNeto() != null) {
                    bjson.setImporteNetoUsd(boleto.getImporteNeto().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }
                if (boleto.getImpuestoBob() != null) {
                    bjson.setImpuestoBobUsd(boleto.getImpuestoBob().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuestoQm() != null) {
                    bjson.setImpuestoQmUsd(boleto.getImpuestoQm().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto1() != null) {
                    bjson.setImpuesto1Usd(boleto.getImpuesto1().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto2() != null) {
                    bjson.setImpuesto2Usd(boleto.getImpuesto2().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto3() != null) {
                    bjson.setImpuesto3Usd(boleto.getImpuesto3().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto4() != null) {
                    bjson.setImpuesto4Usd(boleto.getImpuesto4().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getImpuesto5() != null) {
                    bjson.setImpuesto5Usd(boleto.getImpuesto5().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getTotalBoleto() != null) {
                    bjson.setTotalBoletoUsd(boleto.getTotalBoleto().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getMontoComision() != null) {
                    bjson.setMontoComisionUsd(boleto.getMontoComision().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getMontoFee() != null) {
                    bjson.setMontoFeeUsd(boleto.getMontoFee().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }
                if (boleto.getMontoDescuento() != null) {
                    bjson.setMontoDescuentoUsd(boleto.getMontoDescuento().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getTotalMontoCobrar() != null) {
                    bjson.setTotalMontoCobrarUsd(boleto.getTotalMontoCobrar().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                }

                if (boleto.getMontoComision() != null) {
                    if (boleto.getTotalBoleto() != null) {
                        bjson.setMontoPagarLineaAereaUsd(boleto.getTotalBoleto().subtract(boleto.getMontoComision()).divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                    }
                } else {
                    if (boleto.getTotalBoleto() != null) {
                        bjson.setMontoPagarLineaAereaUsd(boleto.getTotalBoleto().divide(boleto.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
                    }
                }
            }
        }

        //formas de pago
        bjson.setFormaPago(boleto.getFormaPago());

        if (boleto.getFormaPago() != null) {
            switch (boleto.getFormaPago()) {

                case FormasPago.CREDITO:
                    bjson.setCreditoDias(boleto.getCreditoDias());
                    bjson.setCreditoVencimiento(DateContable.getDateFormat(boleto.getCreditoVencimiento(), DateContable.LATIN_AMERICA_FORMAT));
                    break;

                case FormasPago.TARJETA:
                    bjson.setTarjetaId(boleto.getTarjetaId());
                    bjson.setTarjetaNumero(boleto.getTarjetaNumero());
                    break;

                case FormasPago.CONTADO:
                    bjson.setContadoNroCheque(boleto.getContadoNroCheque());
                    bjson.setContadoIdBanco(boleto.getContadoIdBanco());
                    bjson.setContadoNroDeposito(boleto.getContadoNroDeposito());
                    bjson.setContadoTipo(boleto.getContadoTipo());
                    bjson.setContadoIdCuenta(new ComboSelect(boleto.getContadoIdCuenta()));

                    break;

                case FormasPago.COMBINADO:
                    bjson.setCombinadoTipo(boleto.getCombinadoTipo());

                    if (boleto.getCombinadoContado().equals("1")) {
                        bjson.setCombinadoContadoTipo(boleto.getCombinadoContadoTipo());
                        bjson.setCombinadoContadoMonto(boleto.getCombinadoContadoMonto());

                        switch (boleto.getCombinadoContadoTipo()) {
                            case FormasPago.EFECTIVO:
                                break;
                            case FormasPago.CHEQUE:
                                bjson.setCombinadoContadoIdBanco(boleto.getCombinadoContadoIdBanco());
                                bjson.setCombinadoContadoNroCheque(boleto.getCombinadoContadoNroCheque());
                                break;
                            case FormasPago.DEPOSITO:
                                bjson.setCombinadoContadoIdBanco(boleto.getCombinadoContadoIdBanco());
                                bjson.setCombinadoContadoNroDeposito(boleto.getCombinadoContadoNroDeposito());
                                break;
                        }
                    }

                    if (boleto.getCombinadoCredito().equals("1")) {
                        bjson.setCombinadoCredito(true);
                        bjson.setCombinadoCreditoDias(boleto.getCombinadoCreditoDias());
                        bjson.setCombinadoCreditoVencimiento(DateContable.getDateFormat(boleto.getCombinadoCreditoVencimiento(), DateContable.LATIN_AMERICA_FORMAT));
                        bjson.setCombinadoCreditoMonto(boleto.getCombinadoCreditoMonto());
                    }

                    if (boleto.getCombinadoTarjeta().equals("1")) {
                        bjson.setCombinadoTarjeta(true);
                        bjson.setCombinadoTarjetaId(boleto.getCombinadoTarjetaId());
                        bjson.setCombinadoTarjetaNumero(boleto.getCombinadoTarjetaNumero());
                        bjson.setCombinadoTarjetaMonto(boleto.getCombinadoTarjetaMonto());
                    }
                    break;
            }
        }

        return bjson;
    }

    public Long getNumeroOrigen() {
        return numeroOrigen;
    }

    public void setNumeroOrigen(Long numeroOrigen) {
        this.numeroOrigen = numeroOrigen;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(Integer idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }

    public ComboSelect getContadoIdCuenta() {
        return contadoIdCuenta;
    }

    public void setContadoIdCuenta(ComboSelect contadoIdCuenta) {
        this.contadoIdCuenta = contadoIdCuenta;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    public ComboSelect getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(ComboSelect idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public ComboSelect getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(ComboSelect idPromotor) {
        this.idPromotor = idPromotor;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public String getTipoBoleto() {
        return tipoBoleto;
    }

    public void setTipoBoleto(String tipoBoleto) {
        this.tipoBoleto = tipoBoleto;
    }

    public String getTipoCupon() {
        return tipoCupon;
    }

    public void setTipoCupon(String tipoCupon) {
        this.tipoCupon = tipoCupon;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public ComboSelect getIdRuta1() {
        return idRuta1;
    }

    public void setIdRuta1(ComboSelect idRuta1) {
        this.idRuta1 = idRuta1;
    }

    public ComboSelect getIdRuta2() {
        return idRuta2;
    }

    public void setIdRuta2(ComboSelect idRuta2) {
        this.idRuta2 = idRuta2;
    }

    public ComboSelect getIdRuta3() {
        return idRuta3;
    }

    public void setIdRuta3(ComboSelect idRuta3) {
        this.idRuta3 = idRuta3;
    }

    public ComboSelect getIdRuta4() {
        return idRuta4;
    }

    public void setIdRuta4(ComboSelect idRuta4) {
        this.idRuta4 = idRuta4;
    }

    public ComboSelect getIdRuta5() {
        return idRuta5;
    }

    public void setIdRuta5(ComboSelect idRuta5) {
        this.idRuta5 = idRuta5;
    }

    public ComboSelect getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ComboSelect idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getImporteNetoBs() {
        return importeNetoBs;
    }

    public void setImporteNetoBs(BigDecimal importeNetoBs) {
        this.importeNetoBs = importeNetoBs;
    }

    public BigDecimal getImporteNetoUsd() {
        return importeNetoUsd;
    }

    public void setImporteNetoUsd(BigDecimal importeNetoUsd) {
        this.importeNetoUsd = importeNetoUsd;
    }

    public BigDecimal getImpuestoBobBs() {
        return impuestoBobBs;
    }

    public void setImpuestoBobBs(BigDecimal impuestoBobBs) {
        this.impuestoBobBs = impuestoBobBs;
    }

    public BigDecimal getImpuestoBobUsd() {
        return impuestoBobUsd;
    }

    public void setImpuestoBobUsd(BigDecimal impuestoBobUsd) {
        this.impuestoBobUsd = impuestoBobUsd;
    }

    public BigDecimal getImpuestoQmBs() {
        return impuestoQmBs;
    }

    public void setImpuestoQmBs(BigDecimal impuestoQmBs) {
        this.impuestoQmBs = impuestoQmBs;
    }

    public BigDecimal getImpuestoQmUsd() {
        return impuestoQmUsd;
    }

    public void setImpuestoQmUsd(BigDecimal impuestoQmUsd) {
        this.impuestoQmUsd = impuestoQmUsd;
    }

    public BigDecimal getImpuesto1Bs() {
        return impuesto1Bs;
    }

    public void setImpuesto1Bs(BigDecimal impuesto1Bs) {
        this.impuesto1Bs = impuesto1Bs;
    }

    public BigDecimal getImpuesto1Usd() {
        return impuesto1Usd;
    }

    public void setImpuesto1Usd(BigDecimal impuesto1Usd) {
        this.impuesto1Usd = impuesto1Usd;
    }

    public BigDecimal getImpuesto2Bs() {
        return impuesto2Bs;
    }

    public void setImpuesto2Bs(BigDecimal impuesto2Bs) {
        this.impuesto2Bs = impuesto2Bs;
    }

    public BigDecimal getImpuesto2Usd() {
        return impuesto2Usd;
    }

    public void setImpuesto2Usd(BigDecimal impuesto2Usd) {
        this.impuesto2Usd = impuesto2Usd;
    }

    public BigDecimal getImpuesto3Bs() {
        return impuesto3Bs;
    }

    public void setImpuesto3Bs(BigDecimal impuesto3Bs) {
        this.impuesto3Bs = impuesto3Bs;
    }

    public BigDecimal getImpuesto3Usd() {
        return impuesto3Usd;
    }

    public void setImpuesto3Usd(BigDecimal impuesto3Usd) {
        this.impuesto3Usd = impuesto3Usd;
    }

    public BigDecimal getImpuesto4Bs() {
        return impuesto4Bs;
    }

    public void setImpuesto4Bs(BigDecimal impuesto4Bs) {
        this.impuesto4Bs = impuesto4Bs;
    }

    public BigDecimal getImpuesto4Usd() {
        return impuesto4Usd;
    }

    public void setImpuesto4Usd(BigDecimal impuesto4Usd) {
        this.impuesto4Usd = impuesto4Usd;
    }

    public BigDecimal getImpuesto5Bs() {
        return impuesto5Bs;
    }

    public void setImpuesto5Bs(BigDecimal impuesto5Bs) {
        this.impuesto5Bs = impuesto5Bs;
    }

    public BigDecimal getImpuesto5Usd() {
        return impuesto5Usd;
    }

    public void setImpuesto5Usd(BigDecimal impuesto5Usd) {
        this.impuesto5Usd = impuesto5Usd;
    }

    public BigDecimal getTotalBoletoBs() {
        return totalBoletoBs;
    }

    public void setTotalBoletoBs(BigDecimal totalBoletoBs) {
        this.totalBoletoBs = totalBoletoBs;
    }

    public BigDecimal getTotalBoletoUsd() {
        return totalBoletoUsd;
    }

    public void setTotalBoletoUsd(BigDecimal totalBoletoUsd) {
        this.totalBoletoUsd = totalBoletoUsd;
    }

    public BigDecimal getMontoFeeBs() {
        return montoFeeBs;
    }

    public void setMontoFeeBs(BigDecimal montoFeeBs) {
        this.montoFeeBs = montoFeeBs;
    }

    public BigDecimal getMontoFeeUsd() {
        return montoFeeUsd;
    }

    public void setMontoFeeUsd(BigDecimal montoFeeUsd) {
        this.montoFeeUsd = montoFeeUsd;
    }

    public BigDecimal getMontoComisionBs() {
        return montoComisionBs;
    }

    public void setMontoComisionBs(BigDecimal montoComisionBs) {
        this.montoComisionBs = montoComisionBs;
    }

    public BigDecimal getMontoComisionUsd() {
        return montoComisionUsd;
    }

    public void setMontoComisionUsd(BigDecimal montoComisionUsd) {
        this.montoComisionUsd = montoComisionUsd;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getMontoDescuentoBs() {
        return montoDescuentoBs;
    }

    public void setMontoDescuentoBs(BigDecimal montoDescuentoBs) {
        this.montoDescuentoBs = montoDescuentoBs;
    }

    public BigDecimal getMontoDescuentoUsd() {
        return montoDescuentoUsd;
    }

    public void setMontoDescuentoUsd(BigDecimal montoDescuentoUsd) {
        this.montoDescuentoUsd = montoDescuentoUsd;
    }

    public BigDecimal getTotalMontoCobrarBs() {
        return totalMontoCobrarBs;
    }

    public void setTotalMontoCobrarBs(BigDecimal totalMontoCobrarBs) {
        this.totalMontoCobrarBs = totalMontoCobrarBs;
    }

    public BigDecimal getTotalMontoCobrarUsd() {
        return totalMontoCobrarUsd;
    }

    public void setTotalMontoCobrarUsd(BigDecimal totalMontoCobrarUsd) {
        this.totalMontoCobrarUsd = totalMontoCobrarUsd;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Integer getCreditoDias() {
        return creditoDias;
    }

    public void setCreditoDias(Integer creditoDias) {
        this.creditoDias = creditoDias;
    }

    public String getCreditoVencimiento() {
        return creditoVencimiento;
    }

    public void setCreditoVencimiento(String creditoVencimiento) {
        this.creditoVencimiento = creditoVencimiento;
    }

    public String getContadoTipo() {
        return contadoTipo;
    }

    public void setContadoTipo(String contadoTipo) {
        this.contadoTipo = contadoTipo;
    }

    public String getContadoNroCheque() {
        return contadoNroCheque;
    }

    public void setContadoNroCheque(String contadoNroCheque) {
        this.contadoNroCheque = contadoNroCheque;
    }

    public Integer getContadoIdBanco() {
        return contadoIdBanco;
    }

    public void setContadoIdBanco(Integer contadoIdBanco) {
        this.contadoIdBanco = contadoIdBanco;
    }

    public String getContadoNroDeposito() {
        return contadoNroDeposito;
    }

    public void setContadoNroDeposito(String contadoNroDeposito) {
        this.contadoNroDeposito = contadoNroDeposito;
    }

    public String getTarjetaNumero() {
        return tarjetaNumero;
    }

    public void setTarjetaNumero(String tarjetaNumero) {
        this.tarjetaNumero = tarjetaNumero;
    }

    public Integer getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(Integer tarjetaId) {
        this.tarjetaId = tarjetaId;
    }

    public String getCombinadoTipo() {
        return combinadoTipo;
    }

    public void setCombinadoTipo(String combinadoTipo) {
        this.combinadoTipo = combinadoTipo;
    }

    public Integer getCombinadoCreditoDias() {
        return combinadoCreditoDias;
    }

    public void setCombinadoCreditoDias(Integer combinadoCreditoDias) {
        this.combinadoCreditoDias = combinadoCreditoDias;
    }

    public String getCombinadoCreditoVencimiento() {
        return combinadoCreditoVencimiento;
    }

    public void setCombinadoCreditoVencimiento(String combinadoCreditoVencimiento) {
        this.combinadoCreditoVencimiento = combinadoCreditoVencimiento;
    }

    public BigDecimal getCombinadoCreditoMonto() {
        return combinadoCreditoMonto;
    }

    public void setCombinadoCreditoMonto(BigDecimal combinadoCreditoMonto) {
        this.combinadoCreditoMonto = combinadoCreditoMonto;
    }

    public String getCombinadoContadoTipo() {
        return combinadoContadoTipo;
    }

    public void setCombinadoContadoTipo(String combinadoContadoTipo) {
        this.combinadoContadoTipo = combinadoContadoTipo;
    }

    public BigDecimal getCombinadoContadoMonto() {
        return combinadoContadoMonto;
    }

    public void setCombinadoContadoMonto(BigDecimal combinadoContadoMonto) {
        this.combinadoContadoMonto = combinadoContadoMonto;
    }

    public String getCombinadoContadoNroCheque() {
        return combinadoContadoNroCheque;
    }

    public void setCombinadoContadoNroCheque(String combinadoContadoNroCheque) {
        this.combinadoContadoNroCheque = combinadoContadoNroCheque;
    }

    public Integer getCombinadoContadoIdBanco() {
        return combinadoContadoIdBanco;
    }

    public void setCombinadoContadoIdBanco(Integer combinadoContadoIdBanco) {
        this.combinadoContadoIdBanco = combinadoContadoIdBanco;
    }

    public String getCombinadoContadoNroDeposito() {
        return combinadoContadoNroDeposito;
    }

    public void setCombinadoContadoNroDeposito(String combinadoContadoNroDeposito) {
        this.combinadoContadoNroDeposito = combinadoContadoNroDeposito;
    }

    public Integer getCombinadoTarjetaId() {
        return combinadoTarjetaId;
    }

    public void setCombinadoTarjetaId(Integer combinadoTarjetaId) {
        this.combinadoTarjetaId = combinadoTarjetaId;
    }

    public String getCombinadoTarjetaNumero() {
        return combinadoTarjetaNumero;
    }

    public void setCombinadoTarjetaNumero(String combinadoTarjetaNumero) {
        this.combinadoTarjetaNumero = combinadoTarjetaNumero;
    }

    public BigDecimal getCombinadoTarjetaMonto() {
        return combinadoTarjetaMonto;
    }

    public void setCombinadoTarjetaMonto(BigDecimal combinadoTarjetaMonto) {
        this.combinadoTarjetaMonto = combinadoTarjetaMonto;
    }

    public boolean isCombinadoCredito() {
        return combinadoCredito;
    }

    public void setCombinadoCredito(boolean combinadoCredito) {
        this.combinadoCredito = combinadoCredito;
    }

    public boolean isCombinadoContado() {
        return combinadoContado;
    }

    public void setCombinadoContado(boolean combinadoContado) {
        this.combinadoContado = combinadoContado;
    }

    public boolean isCombinadoTarjeta() {
        return combinadoTarjeta;
    }

    public void setCombinadoTarjeta(boolean combinadoTarjeta) {
        this.combinadoTarjeta = combinadoTarjeta;
    }

    public BigDecimal getMontoPagarLineaAereaBs() {
        return montoPagarLineaAereaBs;
    }

    public void setMontoPagarLineaAereaBs(BigDecimal montoPagarLineaAereaBs) {
        this.montoPagarLineaAereaBs = montoPagarLineaAereaBs;
    }

    public BigDecimal getMontoPagarLineaAereaUsd() {
        return montoPagarLineaAereaUsd;
    }

    public void setMontoPagarLineaAereaUsd(BigDecimal montoPagarLineaAereaUsd) {
        this.montoPagarLineaAereaUsd = montoPagarLineaAereaUsd;
    }

    public Integer getIdBoletoPadre() {
        return idBoletoPadre;
    }

    public void setIdBoletoPadre(Integer idBoletoPadre) {
        this.idBoletoPadre = idBoletoPadre;
    }

    public String getImpuesto1nombre() {
        return impuesto1nombre;
    }

    public void setImpuesto1nombre(String impuesto1nombre) {
        this.impuesto1nombre = impuesto1nombre;
    }

    public String getImpuesto2nombre() {
        return impuesto2nombre;
    }

    public void setImpuesto2nombre(String impuesto2nombre) {
        this.impuesto2nombre = impuesto2nombre;
    }

    public String getImpuesto3nombre() {
        return impuesto3nombre;
    }

    public void setImpuesto3nombre(String impuesto3nombre) {
        this.impuesto3nombre = impuesto3nombre;
    }

    public String getImpuesto4nombre() {
        return impuesto4nombre;
    }

    public void setImpuesto4nombre(String impuesto4nombre) {
        this.impuesto4nombre = impuesto4nombre;
    }

    public String getImpuesto5nombre() {
        return impuesto5nombre;
    }

    public void setImpuesto5nombre(String impuesto5nombre) {
        this.impuesto5nombre = impuesto5nombre;
    }

}

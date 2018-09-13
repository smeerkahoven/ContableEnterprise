/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.seguridad.utils.ComboSelect;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class BoletoJSON implements Serializable {
    
    //id de la tabla
    private int idBoleto ;
    private int gestion ;
    private ComboSelect idAerolinea ;
    private int idEmpresa ;
    private ComboSelect idPromotor ;
    private Integer idLibro ;
    private ComboSelect idCliente;
    private ComboSelect nombrePasajero ;
    
    private String emision ;
    private String tipoBoleto ;
    private String tipoCupon ;
    private long numero ;
    //rutas
    private ComboSelect idRuta1 ;
    private ComboSelect idRuta2 ;
    private ComboSelect idRuta3 ;
    private ComboSelect idRuta4 ;
    private ComboSelect idRuta5 ;
    
    
    
    private String fechaEmision ;
    private String fechaViaje ;
    
    private BigDecimal factorCambiario ;
    private String moneda;
    
    // montos
    private BigDecimal importeNetoBs;
    private BigDecimal importeNetoUsd;
    private BigDecimal impuestoBobBs ;
    private BigDecimal impuestoBobUsd ;
    private BigDecimal impuestoQmBs ;
    private BigDecimal impuestoQmUsd ;
    private BigDecimal impuesto1Bs ;
    private BigDecimal impuesto1Usd ;
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
    private BigDecimal comisionBs;
    private BigDecimal comisionUsd;
    private BigDecimal montoFee;
    private BigDecimal montoFeeBs;
    private BigDecimal montoFeeUsd;
    private BigDecimal montoDescuento;
    private BigDecimal montoDescuentoBs;
    private BigDecimal montoDescuentoUsd;
    private BigDecimal totalMontoCanceladoBs;
    private BigDecimal totalMontoCanceladoUsd;
    
    
    private String estado ;
    private int idNotaDebito ;
    private String fechaInsert ;
    
    private String idUsuarioCreador ;
    
    //formas de pago
    private String formaPago ;
    private Integer creditoDias ;
    private String creditoVencimiento ;
    private String contadoTipo ;
    private String contadoNroCheque ;
    private Integer contadoNroBanco ;
    private String contadoNroDeposito ;
    
    private String tarjetaNumero ;
    private Integer tarjetaId ;
    private String combinadoTipo;
    private Integer combinadoCreditoDias;
    private String combinadoCreditoVencimiento ;
    private BigDecimal combinadoCreditoMonto;
    private String combinadoContadoTipo ;
    private BigDecimal combinadoContadoMonto ;
    private String combinadoContadoNroCheque ;
    private Integer combinadoCOntadoIdBanco ;
    private String combinadoContadoNroDeposito ;

    public int getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(int idBoleto) {
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

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
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

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
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

    public ComboSelect getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(ComboSelect nombrePasajero) {
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

    public BigDecimal getComisionBs() {
        return comisionBs;
    }

    public void setComisionBs(BigDecimal comisionBs) {
        this.comisionBs = comisionBs;
    }

    public BigDecimal getComisionUsd() {
        return comisionUsd;
    }

    public void setComisionUsd(BigDecimal comisionUsd) {
        this.comisionUsd = comisionUsd;
    }

    public BigDecimal getMontoFee() {
        return montoFee;
    }

    public void setMontoFee(BigDecimal montoFee) {
        this.montoFee = montoFee;
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

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
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

    public BigDecimal getTotalMontoCanceladoBs() {
        return totalMontoCanceladoBs;
    }

    public void setTotalMontoCanceladoBs(BigDecimal totalMontoCanceladoBs) {
        this.totalMontoCanceladoBs = totalMontoCanceladoBs;
    }

    public BigDecimal getTotalMontoCanceladoUsd() {
        return totalMontoCanceladoUsd;
    }

    public void setTotalMontoCanceladoUsd(BigDecimal totalMontoCanceladoUsd) {
        this.totalMontoCanceladoUsd = totalMontoCanceladoUsd;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(int idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    public Integer getContadoNroBanco() {
        return contadoNroBanco;
    }

    public void setContadoNroBanco(Integer contadoNroBanco) {
        this.contadoNroBanco = contadoNroBanco;
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

    public Integer getCombinadoCOntadoIdBanco() {
        return combinadoCOntadoIdBanco;
    }

    public void setCombinadoCOntadoIdBanco(Integer combinadoCOntadoIdBanco) {
        this.combinadoCOntadoIdBanco = combinadoCOntadoIdBanco;
    }

    public String getCombinadoContadoNroDeposito() {
        return combinadoContadoNroDeposito;
    }

    public void setCombinadoContadoNroDeposito(String combinadoContadoNroDeposito) {
        this.combinadoContadoNroDeposito = combinadoContadoNroDeposito;
    }
    
    
    
    
    
}

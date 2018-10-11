/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */

public class BoletoSearch extends Entidad {

    private static final long serialVersionUID = 1L;
    private int idBoleto;
    private int gestion;
    private int idAerolinea;
    private String nombreAerolinea;
    private int idBoletoPadre;
    private int idPromotor;
    private Integer idIngresoCaja;
    private Integer idLibro;
    private int idEmpresa;
    private String emision;
    private String tipoBoleto;
    private String tipoCupon;
    private long numero;
    private String idRuta1;
    private String idRuta2;
    private String idRuta3;
    private String idRuta4;
    private String idRuta5;
    private Integer idCliente;
    private String nombreCliente ;
    private String nombrePasajero;
    private Date fechaEmision;
    private Date fechaViaje;
    private BigDecimal factorCambiario;
    private String formaPago;
    private String estado;
    private Integer idNotaDebito;
    private Date fechaInsert;
    private String idUsuarioCreador;
    private Integer creditoDias;
    private Date creditoVencimiento;
    private String moneda;
    private BigDecimal importeNeto;
    private BigDecimal impuestoBob;
    private BigDecimal impuestoQm;
    private BigDecimal impuesto1;
    private BigDecimal impuesto2;
    private BigDecimal impuesto3;
    private BigDecimal impuesto4;
    private BigDecimal impuesto5;
    private BigDecimal totalBoleto;
    private BigDecimal comision;
    private BigDecimal montoComision;
    private BigDecimal fee;
    private BigDecimal montoFee;
    private BigDecimal descuento;
    private BigDecimal montoDescuento;
    private BigDecimal totalMontoCancelado;
    private String contadoTipo;
    private String contadoNroCheque;
    private Integer contadoIdBanco;
    private Integer contadoIdCuenta;
    private String contadoNroDeposito;
    private String tarjetaNumero;
    private Integer tarjetaId;
    private String combinadoTipo;
    private Short combinadoCredito;
    private Integer combinadoCreditoDias;
    private Date combinadoCreditoVencimiento;
    private BigDecimal combinadoCreditoMonto;
    private Short combinadoContado;
    private String combinadoContadoTipo;
    private BigDecimal combinadoContadoMonto;
    private String combinadoContadoNroCheque;
    private Integer combinadoContadoIdBanco;
    private Integer combinadoContadoIdCuenta;
    private String combinadoContadoNroDeposito;
    private Short combinadoTarjeta;
    private Integer combinadoTarjetaId;
    private String combinadoTarjetaNumero;
    private BigDecimal combinadoTarjetaMonto;

    public BoletoSearch() {
    }


    public void setIdIngresoCaja(int idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public Integer getContadoIdCuenta() {
        return contadoIdCuenta;
    }

    public void setContadoIdCuenta(Integer contadoIdCuenta) {
        this.contadoIdCuenta = contadoIdCuenta;
    }

    public Integer getCombinadoContadoIdCuenta() {
        return combinadoContadoIdCuenta;
    }

    public void setCombinadoContadoIdCuenta(Integer combinadoContadoIdCuenta) {
        this.combinadoContadoIdCuenta = combinadoContadoIdCuenta;
    }


    public int getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(int idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public int getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(int idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getIdRuta1() {
        return idRuta1;
    }

    public void setIdRuta1(String idRuta1) {
        this.idRuta1 = idRuta1;
    }

    public String getIdRuta2() {
        return idRuta2;
    }

    public void setIdRuta2(String idRuta2) {
        this.idRuta2 = idRuta2;
    }

    public String getIdRuta3() {
        return idRuta3;
    }

    public void setIdRuta3(String idRuta3) {
        this.idRuta3 = idRuta3;
    }

    public String getIdRuta4() {
        return idRuta4;
    }

    public void setIdRuta4(String idRuta4) {
        this.idRuta4 = idRuta4;
    }

    public String getIdRuta5() {
        return idRuta5;
    }

    public void setIdRuta5(String idRuta5) {
        this.idRuta5 = idRuta5;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
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

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Integer getCreditoDias() {
        return creditoDias;
    }

    public void setCreditoDias(Integer creditoDias) {
        this.creditoDias = creditoDias;
    }

    public Date getCreditoVencimiento() {
        return creditoVencimiento;
    }

    public void setCreditoVencimiento(Date creditoVencimiento) {
        this.creditoVencimiento = creditoVencimiento;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImpuestoBob() {
        return impuestoBob;
    }

    public void setImpuestoBob(BigDecimal impuestoBob) {
        this.impuestoBob = impuestoBob;
    }

    public BigDecimal getImpuestoQm() {
        return impuestoQm;
    }

    public void setImpuestoQm(BigDecimal impuestoQm) {
        this.impuestoQm = impuestoQm;
    }

    public BigDecimal getImpuesto1() {
        return impuesto1;
    }

    public void setImpuesto1(BigDecimal impuesto1) {
        this.impuesto1 = impuesto1;
    }

    public BigDecimal getImpuesto2() {
        return impuesto2;
    }

    public void setImpuesto2(BigDecimal impuesto2) {
        this.impuesto2 = impuesto2;
    }

    public BigDecimal getImpuesto3() {
        return impuesto3;
    }

    public void setImpuesto3(BigDecimal impuesto3) {
        this.impuesto3 = impuesto3;
    }

    public BigDecimal getImpuesto4() {
        return impuesto4;
    }

    public void setImpuesto4(BigDecimal impuesto4) {
        this.impuesto4 = impuesto4;
    }

    public BigDecimal getImpuesto5() {
        return impuesto5;
    }

    public void setImpuesto5(BigDecimal impuesto5) {
        this.impuesto5 = impuesto5;
    }

    public BigDecimal getTotalBoleto() {
        return totalBoleto;
    }

    public void setTotalBoleto(BigDecimal totalBoleto) {
        this.totalBoleto = totalBoleto;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getMontoFee() {
        return montoFee;
    }

    public void setMontoFee(BigDecimal montoFee) {
        this.montoFee = montoFee;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public BigDecimal getTotalMontoCancelado() {
        return totalMontoCancelado;
    }

    public void setTotalMontoCancelado(BigDecimal totalMontoCancelado) {
        this.totalMontoCancelado = totalMontoCancelado;
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

    public Short getCombinadoCredito() {
        return combinadoCredito;
    }

    public void setCombinadoCredito(Short combinadoCredito) {
        this.combinadoCredito = combinadoCredito;
    }

    public Integer getCombinadoCreditoDias() {
        return combinadoCreditoDias;
    }

    public void setCombinadoCreditoDias(Integer combinadoCreditoDias) {
        this.combinadoCreditoDias = combinadoCreditoDias;
    }

    public Date getCombinadoCreditoVencimiento() {
        return combinadoCreditoVencimiento;
    }

    public void setCombinadoCreditoVencimiento(Date combinadoCreditoVencimiento) {
        this.combinadoCreditoVencimiento = combinadoCreditoVencimiento;
    }

    public BigDecimal getCombinadoCreditoMonto() {
        return combinadoCreditoMonto;
    }

    public void setCombinadoCreditoMonto(BigDecimal combinadoCreditoMonto) {
        this.combinadoCreditoMonto = combinadoCreditoMonto;
    }

    public Short getCombinadoContado() {
        return combinadoContado;
    }

    public void setCombinadoContado(Short combinadoContado) {
        this.combinadoContado = combinadoContado;
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

    public Short getCombinadoTarjeta() {
        return combinadoTarjeta;
    }

    public void setCombinadoTarjeta(Short combinadoTarjeta) {
        this.combinadoTarjeta = combinadoTarjeta;
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

    public int getIdBoletoPadre() {
        return idBoletoPadre;
    }

    public void setIdBoletoPadre(int idBoletoPadre) {
        this.idBoletoPadre = idBoletoPadre;
    }

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

    @Override
    public int getId() throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

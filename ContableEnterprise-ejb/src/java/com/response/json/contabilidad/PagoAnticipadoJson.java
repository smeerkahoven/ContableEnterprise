/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.PagoAnticipado;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 *
 * @author xeio
 */
public class PagoAnticipadoJson implements Serializable {

    private Integer idPagoAnticipado;
    private Integer idEmpresa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    private ComboSelect idCliente;

    private String fechaEmision;

    private BigDecimal factorCambiario;
    private String moneda;
    private BigDecimal montoAnticipado;
    private BigDecimal montoTotalAcreditado;
    private BigDecimal montoTotalDisponible ;
    private String concepto;
    private String formaPago;
    private String nroCheque;
    private Integer idBanco;
    
    private Integer idCuentaDeposito;
    private String nroDeposito;
    private Integer idTarjetaCredito;
    private String nroTarjeta;
    private String idUsuarioCreador;
    private String fechaInsert;
    private String estado;
    private LinkedList<PagoAnticipadoTransaccionJson> pagoAnticipadoTransaccionCollection;

    public static PagoAnticipadoJson toPagoAnticipadoJson(PagoAnticipado data) {
        PagoAnticipadoJson json = new PagoAnticipadoJson();

        json.setConcepto(data.getConcepto());
        json.setEstado(data.getEstado());
        json.setFactorCambiario(data.getFactorCambiario());
        json.setFechaEmision(DateContable.getDateFormat(data.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFormaPago(data.getFormaPago());
        json.setIdBanco(data.getIdBanco());

        if (data.getIdCliente() != null) {
            json.setIdCliente(new ComboSelect(data.getIdCliente().getIdCliente(), data.getIdCliente().getNombre()));
        }

        json.setIdEmpresa(data.getIdEmpresa());
        json.setIdPagoAnticipado(data.getIdPagoAnticipado());
        json.setIdUsuarioCreador(data.getIdUsuarioCreador());
        json.setIdCuentaDeposito(data.getIdCuentaDeposito());
        json.setIdTarjetaCredito(data.getIdTarjetaCredito());
        json.setMoneda(data.getMoneda());
        json.setMontoAnticipado(data.getMontoAnticipado());
        json.setMontoTotalAcreditado(data.getMontoTotalAcreditado());
        
        if (data.getMontoTotalAcreditado() != null){
            json.setMontoTotalDisponible(json.getMontoAnticipado().subtract(data.getMontoTotalAcreditado()));;
        }else {
            json.setMontoTotalDisponible(data.getMontoAnticipado());
        }
        
        json.setNroCheque(data.getNroCheque());
        json.setNroDeposito(data.getNroDeposito());
        json.setNroTarjeta(data.getNroTarjeta());
        
        
        return json;
    }
    
    public static PagoAnticipado toPagoAnticipado(PagoAnticipadoJson json) {
        PagoAnticipado data = new PagoAnticipado();
        
        data.setConcepto(json.getConcepto());
        data.setEstado(json.getEstado());
        data.setFactorCambiario(json.getFactorCambiario());
        data.setFechaEmision(DateContable.toLatinAmericaDateFormat(json.getFechaEmision()));

        if (json.getIdCliente() != null) {
            Double idCliente = (Double) json.getIdCliente().getId();
            data.setIdCliente(new Cliente(idCliente.intValue()));
        }
        
        data.setFormaPago(json.getFormaPago());
        data.setIdBanco(json.idBanco);
        data.setIdEmpresa(json.getIdEmpresa());
        data.setIdCuentaDeposito(json.getIdCuentaDeposito());
        data.setIdTarjetaCredito(json.getIdTarjetaCredito());
        data.setIdPagoAnticipado(json.getIdPagoAnticipado());
        data.setIdUsuarioCreador(json.getIdUsuarioCreador());
        data.setMoneda(json.getMoneda());
        data.setMontoAnticipado(json.getMontoAnticipado());
        data.setMontoTotalAcreditado(json.getMontoTotalAcreditado());
        data.setNroCheque(json.getNroCheque());
        data.setNroDeposito(json.getNroDeposito());
        data.setNroTarjeta(json.getNroTarjeta());
        
        return data;
    }

    public BigDecimal getMontoTotalDisponible() {
        return montoTotalDisponible;
    }

    public void setMontoTotalDisponible(BigDecimal montoTotalDisponible) {
        this.montoTotalDisponible = montoTotalDisponible;
    }
    
    

    public Integer getIdCuentaDeposito() {
        return idCuentaDeposito;
    }

    public void setIdCuentaDeposito(Integer idCuentaDeposito) {
        this.idCuentaDeposito = idCuentaDeposito;
    }

    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }
    
    public Integer getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public ComboSelect getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ComboSelect idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public BigDecimal getMontoAnticipado() {
        return montoAnticipado;
    }

    public void setMontoAnticipado(BigDecimal montoAnticipado) {
        this.montoAnticipado = montoAnticipado;
    }

    public BigDecimal getMontoTotalAcreditado() {
        return montoTotalAcreditado;
    }

    public void setMontoTotalAcreditado(BigDecimal montoTotalAcreditado) {
        this.montoTotalAcreditado = montoTotalAcreditado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNroDeposito() {
        return nroDeposito;
    }

    public void setNroDeposito(String nroDeposito) {
        this.nroDeposito = nroDeposito;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LinkedList<PagoAnticipadoTransaccionJson> getPagoAnticipadoTransaccionCollection() {
        return pagoAnticipadoTransaccionCollection;
    }

    public void setPagoAnticipadoTransaccionCollection(LinkedList<PagoAnticipadoTransaccionJson> pagoAnticipadoTransaccionCollection) {
        this.pagoAnticipadoTransaccionCollection = pagoAnticipadoTransaccionCollection;
    }

}

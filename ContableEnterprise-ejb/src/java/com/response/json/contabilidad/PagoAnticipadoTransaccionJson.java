/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class PagoAnticipadoTransaccionJson implements Serializable {
    
    private Integer idPagoAnticipadoTransaccion;
    private String descripcion;
    private BigDecimal monto;
    private String fechaInsert;
    private String idUsuarioCreador;
    private String estado;
    private String moneda;
    private Integer idPagoAnticipado;
    
    private Integer idNotaDebitoTransaccion;
    
    private Integer idNotaDebito;
    private BigDecimal montoAdeudadoBs;
    private BigDecimal montoAdeudadoUsd;
    private BigDecimal montoTransaccionBs;
    private BigDecimal montoTransaccionUsd;
    private String monedaTransaccion;
    private String descripcionTransaccion;
    private String tipo;
    
    private ComboSelect idCliente;
    private BigDecimal factorCambiario;
    private String fechaEmision;
    
    public static PagoAnticipadoTransaccionJson toPagoAnticipadoTransaccionJsOn(PagoAnticipadoTransaccion data) {
        PagoAnticipadoTransaccionJson json = new PagoAnticipadoTransaccionJson();
        
        json.setDescripcion(data.getDescripcion());
        if (data.getIdNotaTransaccion() != null) {
            json.setDescripcionTransaccion(data.getIdNotaTransaccion().getDescripcion());
            json.setIdNotaDebito(data.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito());
            
            json.setMontoTransaccionBs(data.getIdNotaTransaccion().getMontoBs());
            json.setMontoTransaccionUsd(data.getIdNotaTransaccion().getMontoUsd());
            json.setMonedaTransaccion(data.getIdNotaTransaccion().getMoneda());
            json.setTipo(data.getIdNotaTransaccion().getTipo());
            json.setMonedaTransaccion(data.getIdNotaTransaccion().getMoneda());
            json.setMontoAdeudadoBs(data.getIdNotaTransaccion().getMontoAdeudadoBs());
            json.setMontoAdeudadoUsd(data.getIdNotaTransaccion().getMontoAdeudadoUsd());
        }
        
        json.setTipo(data.getTipo());
        json.setEstado(data.getEstado());
        json.setFactorCambiario(data.getIdPagoAnticipado().getFactorCambiario());
        json.setFechaInsert(DateContable.getDateFormat(data.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        
        json.setIdPagoAnticipado(data.getIdPagoAnticipado().getIdPagoAnticipado());
        json.setIdPagoAnticipadoTransaccion(data.getIdPagoAnticipadoTransaccion());
        json.setIdUsuarioCreador(data.getIdUsuarioCreador());
        
        json.setMoneda(data.getMoneda());
        
        json.setMonto(data.getMonto());
        
        return json;
    }
    
    public static PagoAnticipadoTransaccion toPagoAnticipadoTransaccion(PagoAnticipadoTransaccionJson json) {
        PagoAnticipadoTransaccion data = new PagoAnticipadoTransaccion();
        
        data.setIdPagoAnticipadoTransaccion(json.getIdPagoAnticipadoTransaccion());
        data.setDescripcion(json.getDescripcion());
        data.setEstado(json.getEstado());
        data.setIdNotaTransaccion(new NotaDebitoTransaccion(json.getIdNotaDebitoTransaccion()));
        data.setIdPagoAnticipado(new PagoAnticipado(json.getIdPagoAnticipado()));
        data.setIdUsuarioCreador(json.getIdUsuarioCreador());
        data.setMoneda(json.getMoneda());
        data.setMonto(json.getMonto());
        
        return data;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public static LinkedList<PagoAnticipadoTransaccionJson> toPagoAnticipadoTransaccionListJson(
            List<PagoAnticipadoTransaccion> list
    ) {
        LinkedList r = new LinkedList();
        
        for (PagoAnticipadoTransaccion pat : list) {
            PagoAnticipadoTransaccionJson json = toPagoAnticipadoTransaccionJsOn(pat);
            r.add(json);
        }
        
        return r;
        
    }
    
    public String getMoneda() {
        return moneda;
    }
    
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }
    
    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }
    
    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }
    
    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }
    
    public BigDecimal getMontoAdeudadoBs() {
        return montoAdeudadoBs;
    }
    
    public void setMontoAdeudadoBs(BigDecimal montoAdeudadoBs) {
        this.montoAdeudadoBs = montoAdeudadoBs;
    }
    
    public BigDecimal getMontoAdeudadoUsd() {
        return montoAdeudadoUsd;
    }
    
    public void setMontoAdeudadoUsd(BigDecimal montoAdeudadoUsd) {
        this.montoAdeudadoUsd = montoAdeudadoUsd;
    }
    
    public BigDecimal getMontoTransaccionBs() {
        return montoTransaccionBs;
    }
    
    public void setMontoTransaccionBs(BigDecimal montoTransaccionBs) {
        this.montoTransaccionBs = montoTransaccionBs;
    }
    
    public BigDecimal getMontoTransaccionUsd() {
        return montoTransaccionUsd;
    }
    
    public void setMontoTransaccionUsd(BigDecimal montoTransaccionUsd) {
        this.montoTransaccionUsd = montoTransaccionUsd;
    }
    
    public String getMonedaTransaccion() {
        return monedaTransaccion;
    }
    
    public void setMonedaTransaccion(String monedaTransaccion) {
        this.monedaTransaccion = monedaTransaccion;
    }
    
    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }
    
    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }
    
    public ComboSelect getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(ComboSelect idCliente) {
        this.idCliente = idCliente;
    }
    
    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }
    
    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }
    
    public String getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public Integer getIdPagoAnticipadoTransaccion() {
        return idPagoAnticipadoTransaccion;
    }
    
    public void setIdPagoAnticipadoTransaccion(Integer idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Integer getIdPagoAnticipado() {
        return idPagoAnticipado;
    }
    
    public void setIdPagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }
    
}

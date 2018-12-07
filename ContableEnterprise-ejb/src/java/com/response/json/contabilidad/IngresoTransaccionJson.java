/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class IngresoTransaccionJson implements Serializable {
    
    private Integer idTransaccion;
    private Integer idIngresoCaja;
    private String descripcion ;
    private String moneda ;
    private BigDecimal montoBs ;
    private BigDecimal montoUsd ;
    private String fechaInsert ;
    private Integer idNotaTransaccion ;
    private Integer idNotaDebito ;
    private String estado ;
    
    public static IngresoTransaccion toIngresoCaja(IngresoTransaccionJson json){
        IngresoTransaccion data = new IngresoTransaccion();
        data.setDescripcion(json.getDescripcion());
        data.setEstado(json.getEstado());
        data.setIdIngresoCaja(new IngresoCaja(json.getIdIngresoCaja()));
        data.setIdNotaTransaccion(new NotaDebitoTransaccion(json.getIdNotaTransaccion()));
        data.setIdTransaccion(json.getIdTransaccion());
        data.setMoneda(json.getMoneda());
        data.setMontoBs(json.getMontoBs());
        data.setMontoUsd(json.getMontoUsd());
        
        return data ;
    }
    
    public static IngresoTransaccionJson toIngresoTransaccionJson(IngresoTransaccion data){
        IngresoTransaccionJson json = new IngresoTransaccionJson();
        
        json.setDescripcion(data.getDescripcion());
        json.setEstado(data.getEstado());
        json.setIdIngresoCaja(data.getIdIngresoCaja().getIdIngresoCaja());
        json.setFechaInsert(DateContable.getDateFormat(data.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setIdNotaDebito(data.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        json.setIdTransaccion(data.getIdTransaccion());
        json.setMoneda(data.getMoneda());
        json.setMontoBs(data.getMontoBs());
        json.setMontoUsd(data.getMontoUsd());
        
        return json;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMontoBs() {
        return montoBs;
    }

    public void setMontoBs(BigDecimal montoBs) {
        this.montoBs = montoBs;
    }

    public BigDecimal getMontoUsd() {
        return montoUsd;
    }

    public void setMontoUsd(BigDecimal montoUsd) {
        this.montoUsd = montoUsd;
    }

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public Integer getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(Integer idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}

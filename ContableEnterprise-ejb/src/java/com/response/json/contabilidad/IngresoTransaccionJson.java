/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.seguridad.utils.ComboSelect;
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
    private String moneda;
    private BigDecimal monto;
    private BigDecimal montoBs;
    private BigDecimal montoUsd;
    private String fechaInsert;
    private String estado;
    private String descripcion;

    private Integer idNotaDebitoTransaccion;
    private Integer idNotaDebito;
    private BigDecimal montoAdeudadoBs;
    private BigDecimal montoAdeudadoUsd;
    private BigDecimal montoTransaccionBs;
    private BigDecimal montoTransaccionUsd;
    private String monedaTransaccion;
    private String descripcionTransaccion;

    private ComboSelect idCliente;
    private BigDecimal factorCambiario;
    private String fechaEmision;

    public static IngresoTransaccion toIngresoCaja(IngresoTransaccionJson json) {
        IngresoTransaccion data = new IngresoTransaccion();
        data.setDescripcion(json.getDescripcion());
        data.setEstado(json.getEstado());
        data.setIdIngresoCaja(new IngresoCaja(json.getIdIngresoCaja()));
        data.setIdNotaTransaccion(new NotaDebitoTransaccion(json.getIdNotaDebitoTransaccion()));
        data.setIdTransaccion(json.getIdTransaccion());
        data.setMoneda(json.getMoneda());

        if (json.getMoneda().equals(Moneda.NACIONAL)) {
            data.setMontoBs(json.getMonto());
        } else {
            data.setMontoUsd(json.getMonto());
        }

        return data;
    }

    public static IngresoTransaccionJson toIngresoTransaccionJson(IngresoTransaccion data) {
        IngresoTransaccionJson json = new IngresoTransaccionJson();

        json.setDescripcion(data.getDescripcion());
        json.setEstado(data.getEstado());
        json.setIdIngresoCaja(data.getIdIngresoCaja().getIdIngresoCaja());
        json.setFechaInsert(DateContable.getDateFormat(data.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setIdNotaDebito(data.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito());
        json.setIdTransaccion(data.getIdTransaccion());
        json.setMoneda(data.getMoneda());

        json.setDescripcionTransaccion(data.getIdNotaTransaccion().getDescripcion());
        json.setMontoTransaccionBs(data.getIdNotaTransaccion().getMontoBs());
        json.setMontoTransaccionUsd(data.getIdNotaTransaccion().getMontoUsd());
        json.setMonedaTransaccion(data.getIdNotaTransaccion().getMoneda());
        
        json.setMontoAdeudadoBs(data.getIdNotaTransaccion().getMontoAdeudadoBs());
        json.setMontoAdeudadoUsd(data.getIdNotaTransaccion().getMontoAdeudadoUsd());

        if (json.getMoneda().equals(Moneda.NACIONAL)) {
            json.setMonto(data.getMontoBs());
        } else {
            json.setMonto(data.getMontoUsd());
        }

        json.setMontoBs(data.getMontoBs());
        json.setMontoUsd(data.getMontoUsd());

        return json;
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

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
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

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
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

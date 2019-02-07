/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PlanCuentas;
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
public class NotaCreditoTransaccionJson implements Serializable {

    private Integer idNotaCreditoTransaccion;
    private String descripcion;
    private BigDecimal montoBs;
    private BigDecimal montoUsd;
    private BigDecimal monto;
    private String moneda;
    private String fechaInsert;
    private String estado;
    private Integer idNotaCredito;

    private Integer idNotaDebitoTransaccion;

    private Integer idNotaDebito;
    private BigDecimal montoAdeudadoBs;
    private BigDecimal montoAdeudadoUsd;
    private BigDecimal montoTransaccionBs;
    private BigDecimal montoTransaccionUsd;
    private String monedaTransaccion;
    private String descripcionTransaccion;

    private ComboSelect idPlanCuenta;

    private ComboSelect idCliente;
    private BigDecimal factorCambiario;
    private String fechaEmision;

    public static LinkedList<NotaCreditoTransaccionJson> toNotaCreditoTransaccionListJsOn(List<NotaCreditoTransaccion> list) {

        LinkedList r = new LinkedList();
        for (NotaCreditoTransaccion nct : list) {
            NotaCreditoTransaccionJson json = toNotaCreditoTransaccionJsOn(nct);
            r.add(json);
        }

        return r;

    }

    public static NotaCreditoTransaccionJson toNotaCreditoTransaccionJsOn(NotaCreditoTransaccion data) {
        NotaCreditoTransaccionJson json = new NotaCreditoTransaccionJson();

        json.setDescripcion(data.getDescripcion());
        json.setDescripcionTransaccion(data.getIdNotaCredito().getConcepto());
        json.setEstado(data.getEstado());
        json.setFactorCambiario(data.getIdNotaCredito().getFactorCambiario());
        json.setFechaInsert(DateContable.getDateFormat(data.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));

        json.setIdNotaCredito(data.getIdNotaCredito().getIdNotaCredito());
        json.setIdNotaCreditoTransaccion(data.getIdNotaCreditoTransaccion());
        json.setIdNotaDebito(data.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito());
        
        json.setIdNotaDebitoTransaccion(data.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        json.setIdPlanCuenta(new ComboSelect(data.getIdPlanCuenta().getIdPlanCuentas(), data.getIdPlanCuenta().getCuenta()));

        json.setMoneda(data.getMoneda());
        json.setMonedaTransaccion(data.getIdNotaTransaccion().getMoneda());
        json.setMontoAdeudadoBs(data.getIdNotaTransaccion().getMontoAdeudadoBs());
        json.setMontoAdeudadoUsd(data.getIdNotaTransaccion().getMontoAdeudadoUsd());
        json.setMontoBs(data.getMontoBs());
        json.setMontoUsd(data.getMontoUsd());

        json.setMontoTransaccionBs(data.getIdNotaTransaccion().getMontoBs());
        json.setMontoTransaccionUsd(data.getIdNotaTransaccion().getMontoUsd());
        json.setMonedaTransaccion(data.getIdNotaTransaccion().getMoneda());

        if (json.getMoneda().equals(Moneda.NACIONAL)) {
            json.setMonto(data.getMontoBs());
        } else {
            json.setMonto(data.getMontoUsd());
        }

        return json;
    }

    public static NotaCreditoTransaccion toNotaCreditoTransaccion(NotaCreditoTransaccionJson json) {
        NotaCreditoTransaccion data = new NotaCreditoTransaccion();

        data.setDescripcion(json.getDescripcion());
        data.setEstado(json.getEstado());
        data.setIdNotaCredito(new NotaCredito(json.getIdNotaCredito()));
        data.setIdNotaTransaccion(new NotaDebitoTransaccion(json.getIdNotaDebitoTransaccion()));
        data.setIdNotaCreditoTransaccion(json.getIdNotaCreditoTransaccion());
        data.setIdPlanCuenta(new PlanCuentas(((Double) json.getIdPlanCuenta().getId()).intValue()));
        data.setMoneda(json.getMoneda());
        if (json.getMoneda().equals(Moneda.NACIONAL)) {
            data.setMontoBs(json.getMonto());
        } else {
            data.setMontoUsd(json.getMonto());
        }

        return data;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getIdNotaCreditoTransaccion() {
        return idNotaCreditoTransaccion;
    }

    public void setIdNotaCreditoTransaccion(Integer idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(Integer idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
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

    public ComboSelect getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(ComboSelect idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
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

}

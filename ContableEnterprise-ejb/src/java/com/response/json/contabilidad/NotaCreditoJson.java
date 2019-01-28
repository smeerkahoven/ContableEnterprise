/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 *
 * @author xeio
 */
public class NotaCreditoJson {

    private Integer idNotaCredito;
    private String idUsuario;
    private Integer idEmpresa;

    private ComboSelect idCliente;

    private String fechaEmision;
    private String fechaInsert;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal montoAbonadoBs;
    private BigDecimal montoAbonadoUsd;
    private BigDecimal factorCambiario;
    private String concepto;
    private String estado;
    private LinkedList<NotaCreditoTransaccionJson> transacciones;

    public static NotaCredito toNotaCredito(NotaCreditoJson json) {
        NotaCredito data = new NotaCredito();

        data.setConcepto(json.getConcepto());
        data.setEstado(json.getEstado());
        data.setFactorCambiario(json.getFactorCambiario());
        data.setFechaEmision(DateContable.toLatinAmericaDateFormat(json.getFechaEmision()));

        if (json.getIdCliente() != null) {
            Double idCliente = (Double) json.getIdCliente().getId();
            data.setIdCliente(new Cliente(idCliente.intValue()));
        }
        data.setIdEmpresa(json.getIdEmpresa());
        data.setIdNotaCredito(json.getIdNotaCredito());
        data.setIdUsuario(json.getIdUsuario());
        data.setMontoAbonadoBs(json.getMontoAbonadoBs());
        data.setMontoAbonadoUsd(json.getMontoAbonadoUsd());

        return data;
    }

    public static NotaCreditoJson toNotaCreditoJson(NotaCredito data) {
        NotaCreditoJson json = new NotaCreditoJson();

        json.setConcepto(data.getConcepto());
        json.setEstado(data.getEstado());
        json.setFactorCambiario(data.getFactorCambiario());
        json.setFechaEmision(DateContable.getDateFormat(data.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaInsert(DateContable.getDateFormat(data.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        if (data.getIdCliente() != null) {
            json.setIdCliente(new ComboSelect(data.getIdCliente().getIdCliente(), data.getIdCliente().getNombre()));
        }
        
        json.setIdEmpresa(data.getIdEmpresa());
        json.setIdNotaCredito(data.getIdNotaCredito());
        json.setIdUsuario(data.getIdUsuario());
        json.setMontoAbonadoBs(data.getMontoAbonadoBs());
        json.setMontoAbonadoUsd(data.getMontoAbonadoUsd());
        //json.setTransacciones( NotaCreditoTransaccionJson.toNotaCreditoTransaccionListJsOn(data.getNotaCreditoTransaccionList()));

        return json;
    }

    public Integer getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(Integer idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public BigDecimal getMontoAbonadoBs() {
        return montoAbonadoBs;
    }

    public void setMontoAbonadoBs(BigDecimal montoAbonadoBs) {
        this.montoAbonadoBs = montoAbonadoBs;
    }

    public BigDecimal getMontoAbonadoUsd() {
        return montoAbonadoUsd;
    }

    public void setMontoAbonadoUsd(BigDecimal montoAbonadoUsd) {
        this.montoAbonadoUsd = montoAbonadoUsd;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LinkedList<NotaCreditoTransaccionJson> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(LinkedList<NotaCreditoTransaccionJson> transacciones) {
        this.transacciones = transacciones;
    }

}

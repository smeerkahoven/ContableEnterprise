/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.NotaDebitoTransaccion;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class NotaDebitoTransaccionJson implements Serializable {

    private Integer idNotaDebitoTransaccion;
    private Integer gestion;
    private Integer idNotaDebito;
    private String descripcion;
    private BigDecimal montoBs;
    private BigDecimal montoUsd;
    private String fechaInsert;
    private Integer idBoleto;
    private Integer idCargo;
    private String estado;
    private String moneda;
    private String tipo;
    private String idUsuario;

    public static NotaDebitoTransaccion toNotaDebitoTransaccion(NotaDebitoTransaccionJson json) {
        NotaDebitoTransaccion tr = new NotaDebitoTransaccion();

        tr.setDescripcion(json.getDescripcion());
        tr.setEstado(json.getEstado());
        tr.setGestion(json.getGestion());
        tr.setIdBoleto(json.getIdBoleto());
        tr.setIdCargo(json.getIdCargo());
        tr.setIdNotaDebito(json.getIdNotaDebito());
        tr.setIdNotaDebitoTransaccion(json.getIdNotaDebitoTransaccion());
        tr.setIdUsuario(json.getIdUsuario());
        tr.setMoneda(json.getMoneda());
        tr.setMontoBs(json.getMontoBs());
        tr.setMontoUsd(json.getMontoUsd());
        tr.setTipo(json.getTipo());
        
        return tr;
    }

    public NotaDebitoTransaccionJson toNotaDebitoTransaccionJson(NotaDebitoTransaccion tr) {
        NotaDebitoTransaccionJson json = new NotaDebitoTransaccionJson();

        json.setDescripcion(tr.getDescripcion());
        json.setEstado(tr.getEstado());
        json.setFechaInsert(DateContable.getDateFormat(tr.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setGestion(tr.getGestion());
        json.setIdBoleto(tr.getIdBoleto());
        json.setIdCargo(tr.getIdCargo());
        json.setIdNotaDebito(tr.getIdNotaDebito());
        json.setIdNotaDebitoTransaccion(tr.getIdNotaDebitoTransaccion());
        json.setIdUsuario(tr.getIdUsuario());
        json.setMoneda(tr.getMoneda());
        json.setMontoBs(tr.getMontoBs());
        json.setMontoUsd(tr.getMontoUsd());
        json.setTipo(tr.getTipo());

        return json;
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

}

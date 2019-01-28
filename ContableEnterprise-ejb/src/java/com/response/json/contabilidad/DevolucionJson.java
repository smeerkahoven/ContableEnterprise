/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.PagoAnticipado;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class DevolucionJson implements Serializable {

    private Integer idDevolucion;
    private Integer idCliente;
    private String nombreCliente;
    private Integer idNotaDebito;
    private Integer idPagoAnticipado;
    private Integer idEmpresa;
    private String fechaEmision;
    private String moneda;
    private BigDecimal factor;
    private BigDecimal monto;
    private String concepto;
    private String tipoDevolucion;
    private String nroCheque;
    private String nroDeposito;
    private Integer idCuentaDeposito;
    private String fechaInsert;
    private String idUsuarioCreador;
    private String estado;
    private BigDecimal montoMaximoDevolucion;

    public static DevolucionJson toDevolucionJson(final Devolucion data) {
        DevolucionJson json = new DevolucionJson();

        json.setConcepto(data.getConcepto());
        json.setEstado(data.getEstado());
        json.setFactor(data.getFactorCambiario());
        json.setFechaEmision(DateContable.getDateFormat(data.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaInsert(DateContable.getDateFormat(data.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setIdCliente(data.getIdCliente() != null ? data.getIdCliente().getIdCliente() : null);
        json.setIdDevolucion(data.getIdDevolucion());
        json.setIdEmpresa(data.getIdEmpresa());
        json.setIdNotaDebito(data.getIdNotaDebito() != null ? data.getIdNotaDebito().getIdNotaDebito() : null);
        json.setIdPagoAnticipado(data.getIdPagoAnticipado() != null ? data.getIdPagoAnticipado().getIdPagoAnticipado() : null);
        json.setIdUsuarioCreador(data.getIdUsuarioCreador());
        json.setMoneda(data.getMoneda());
        json.setMonto(data.getMonto());
        json.setNombreCliente(data.getIdCliente() != null ? data.getIdCliente().getNombre() : null);
        json.setNroCheque(data.getNroCheque());
        json.setTipoDevolucion(data.getTipoDevolucion());
        json.setIdCuentaDeposito(data.getIdCuentaDeposito());
        json.setNroDeposito(data.getNroDeposito());

        return json;
    }

    public static Devolucion toDevolucion(final DevolucionJson json) {
        Devolucion data = new Devolucion();

        data.setConcepto(json.getConcepto());
        data.setEstado(json.getEstado());
        data.setFactorCambiario(json.getFactor());
        data.setFechaEmision(DateContable.toLatinAmericaDateFormat(json.getFechaEmision()));
        data.setIdCliente(new Cliente(json.getIdCliente()));
        data.setIdDevolucion(json.getIdDevolucion());
        data.setIdEmpresa(json.getIdEmpresa());
        data.setIdNotaDebito(json.getIdNotaDebito() != null ? new NotaDebito(json.getIdNotaDebito()) : null);
        data.setIdPagoAnticipado(json.getIdPagoAnticipado() != null ? new PagoAnticipado(json.getIdPagoAnticipado()) : null);
        data.setIdUsuarioCreador(json.getIdUsuarioCreador());
        data.setMoneda(json.getMoneda());
        data.setMonto(json.getMonto());
        data.setNroCheque(json.getNroCheque());
        data.setTipoDevolucion(json.getTipoDevolucion());
        data.setNroDeposito(json.getNroDeposito());
        data.setIdCuentaDeposito(json.getIdCuentaDeposito());

        return data;
    }

    public String getNroDeposito() {
        return nroDeposito;
    }

    public void setNroDeposito(String nroDeposito) {
        this.nroDeposito = nroDeposito;
    }

    public Integer getIdCuentaDeposito() {
        return idCuentaDeposito;
    }

    public void setIdCuentaDeposito(Integer idCuentaDeposito) {
        this.idCuentaDeposito = idCuentaDeposito;
    }

    public BigDecimal getMontoMaximoDevolucion() {
        return montoMaximoDevolucion;
    }

    public void setMontoMaximoDevolucion(BigDecimal montoMaximoDevolucion) {
        this.montoMaximoDevolucion = montoMaximoDevolucion;
    }

    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipoDevolucion() {
        return tipoDevolucion;
    }

    public void setTipoDevolucion(String tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
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

}

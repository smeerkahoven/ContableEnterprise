/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cobranzas.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class ReporteEstadoClienteDto implements Serializable {

    private Integer idDocumento;
    private String glosa;
    private String fecha;
    private String vencimiento;
    private String formaPago;
    private BigDecimal montoDebeNac;
    private BigDecimal montoHaberNac;
    private BigDecimal saldoNac;
    private BigDecimal montoDebeExt;
    private BigDecimal montoHaberExt;
    private BigDecimal saldoExt;
    private String tipoDocumento;
    private String estado;
    private String row;
    private Integer idCliente;
    private String nombreCliente;
    private String telefonoFijo;
    private String telefonoCelular;
    private String email ;

    public ReporteEstadoClienteDto(Integer idDocumento, String glosa, String fecha, String vencimiento, String formaPago, BigDecimal montoDebeNac, BigDecimal montoHaberNac, BigDecimal saldoNac, BigDecimal montoDebeExt, BigDecimal montoHaberExt, BigDecimal saldoExt, String tipoDocumento, String estado, String row, Integer idCliente, String nombreCliente, String telefonoFijo, String telefonoCelular, String email) {
        this.idDocumento = idDocumento;
        this.glosa = glosa;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.formaPago = formaPago;
        this.montoDebeNac = montoDebeNac;
        this.montoHaberNac = montoHaberNac;
        this.saldoNac = saldoNac;
        this.montoDebeExt = montoDebeExt;
        this.montoHaberExt = montoHaberExt;
        this.saldoExt = saldoExt;
        this.tipoDocumento = tipoDocumento;
        this.estado = estado;
        this.row = row;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.email = email;
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

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public BigDecimal getMontoDebeNac() {
        return montoDebeNac;
    }

    public void setMontoDebeNac(BigDecimal montoDebeNac) {
        this.montoDebeNac = montoDebeNac;
    }

    public BigDecimal getMontoHaberNac() {
        return montoHaberNac;
    }

    public void setMontoHaberNac(BigDecimal montoHaberNac) {
        this.montoHaberNac = montoHaberNac;
    }

    public BigDecimal getSaldoNac() {
        return saldoNac;
    }

    public void setSaldoNac(BigDecimal saldoNac) {
        this.saldoNac = saldoNac;
    }

    public BigDecimal getMontoDebeExt() {
        return montoDebeExt;
    }

    public void setMontoDebeExt(BigDecimal montoDebeExt) {
        this.montoDebeExt = montoDebeExt;
    }

    public BigDecimal getMontoHaberExt() {
        return montoHaberExt;
    }

    public void setMontoHaberExt(BigDecimal montoHaberExt) {
        this.montoHaberExt = montoHaberExt;
    }

    public BigDecimal getSaldoExt() {
        return saldoExt;
    }

    public void setSaldoExt(BigDecimal saldoExt) {
        this.saldoExt = saldoExt;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}

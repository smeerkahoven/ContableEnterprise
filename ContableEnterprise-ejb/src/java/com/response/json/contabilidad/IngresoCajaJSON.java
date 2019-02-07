/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.IngresoCaja;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class IngresoCajaJSON implements Serializable {
    
    private Integer idIngresoCaja;
    private Integer idNotaDebito;
    private String idUsuario;
    private Integer idEmpresa;
    private ComboSelect idCliente;
    private ComboSelect idCounter;
    private String fechaEmision;
    private String fechaInsert;
    private BigDecimal montoAbonadoBs;
    private BigDecimal montoAbonadoUsd;
    private BigDecimal factorCambiario;
    private String formaPago;
    private Integer idBanco;
    private String nroCheque;
    private Integer idTarjetaCredito;
    private String nroTarjeta;
    private String nroDeposito;
    private Integer idCuentaDeposito;
    private String estado;
    
    public static IngresoCaja toIngresoCaja(IngresoCajaJSON json) {
        IngresoCaja caja = new IngresoCaja();
        
        caja.setEstado(json.getEstado());
        caja.setFactorCambiario(json.getFactorCambiario());
        caja.setFechaEmision(DateContable.toLatinAmericaDateFormat(json.getFechaEmision()));
        caja.setFormaPago(json.getFormaPago());
        caja.setIdBanco(json.getIdBanco());
        caja.setNroCheque(json.getNroCheque());
        caja.setNroDeposito(json.getNroDeposito());
        caja.setNroTarjeta(json.getNroTarjeta());
        caja.setIdTarjetaCredito(json.getIdTarjetaCredito());
        caja.setIdCuentaDeposito(json.getIdCuentaDeposito());
        
        if (json.getIdCliente() != null) {
            Double idCliente = (Double) json.getIdCliente().getId();
            caja.setIdCliente(new Cliente(idCliente.intValue()));
        }
        
        caja.setIdCuentaDeposito(json.getIdCuentaDeposito());
        caja.setIdEmpresa(json.getIdEmpresa());
        caja.setIdIngresoCaja(json.getIdIngresoCaja());
        
        caja.setIdUsuario(json.getIdUsuario());
        caja.setMontoAbonadoBs(json.getMontoAbonadoBs());
        caja.setMontoAbonadoUsd(json.getMontoAbonadoUsd());
        
        return caja;
    }
    
    public static IngresoCajaJSON toJSON(IngresoCaja ingreso) {
        IngresoCajaJSON json = new IngresoCajaJSON();
        
        json.setEstado(ingreso.getEstado());
        json.setFactorCambiario(ingreso.getFactorCambiario());
        json.setFechaEmision(DateContable.getDateFormat(ingreso.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaInsert(DateContable.getDateFormat(ingreso.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setFormaPago(ingreso.getFormaPago());
        json.setIdBanco(ingreso.getIdBanco());
        if (ingreso.getIdCliente() != null) {
            json.setIdCliente(new ComboSelect(ingreso.getIdCliente().getIdCliente(), ingreso.getIdCliente().getNombre()));
        }
        /*if (ingreso.getIdCounter() != null) {
            json.setIdCounter(new ComboSelect(ingreso.getIdCounter().getIdPromotor(), ingreso.getIdCounter().getNombre()));
        }*/
        
        json.setIdCuentaDeposito(ingreso.getIdCuentaDeposito());
        json.setIdEmpresa(ingreso.getIdEmpresa());
        json.setIdIngresoCaja(ingreso.getIdIngresoCaja());
        json.setIdTarjetaCredito(ingreso.getIdTarjetaCredito());
        json.setIdUsuario(ingreso.getIdUsuario());
        json.setMontoAbonadoBs(ingreso.getMontoAbonadoBs());
        json.setMontoAbonadoUsd(ingreso.getMontoAbonadoUsd());
        json.setNroCheque(ingreso.getNroCheque());
        json.setNroDeposito(ingreso.getNroDeposito());
        json.setNroTarjeta(ingreso.getNroTarjeta());
        
        return json;
    }
    
    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }
    
    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }
    
    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }
    
    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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
    
    public ComboSelect getIdCounter() {
        return idCounter;
    }
    
    public void setIdCounter(ComboSelect idCounter) {
        this.idCounter = idCounter;
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
    
    public String getFormaPago() {
        return formaPago;
    }
    
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
    public Integer getIdBanco() {
        return idBanco;
    }
    
    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }
    
    public String getNroCheque() {
        return nroCheque;
    }
    
    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }
    
    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }
    
    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }
    
    public String getNroTarjeta() {
        return nroTarjeta;
    }
    
    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Cliente;
import com.agencia.entities.Promotor;
import com.contabilidad.entities.NotaDebito;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class NotaDebitoJSON {

    private Integer idNotaDebito;
    private Integer gestion;
    private Integer idEmpresa;
    private ComboSelect idCliente;
    private ComboSelect idPromotor;
    private String fechaEmision;
    private String fechaInsert;
    private BigDecimal montoTotalBs;
    private BigDecimal montoTotalUsd;
    private BigDecimal montoAdeudadoBs;
    private BigDecimal montoAdeudadoUsd;
    private BigDecimal factorCambiario;
    private String formaPago;
    private Integer idBanco;
    private String nroCheque;
    private String nroDeposito;
    private Integer idCuentaDeposito;
    private Integer idTarjetaCredito;
    private String nroTarjeta;
    private String idUsuarioCreador;
    private Integer creditoDias;
    private String creditoVencimiento;
    private Short combinadoContado;
    private Short combinadoTarjeta;
    private Short combinadoCredito;
    private String combinadoContadoTipo;
    private String estado ;

    public static NotaDebitoJSON toNotaDebitoJSON(NotaDebito nota) {
        NotaDebitoJSON ndj = new NotaDebitoJSON();
        ndj.setCombinadoContado(nota.getCombinadoContado());
        ndj.setCombinadoContadoTipo(nota.getCombinadoContadoTipo());
        ndj.setCombinadoCredito(nota.getCombinadoCredito());
        ndj.setCombinadoTarjeta(nota.getCombinadoTarjeta());
        ndj.setCreditoDias(nota.getCreditoDias());
        ndj.setCreditoVencimiento(DateContable.getDateFormat(nota.getCreditoVencimiento(), DateContable.LATIN_AMERICA_FORMAT));
        ndj.setFactorCambiario(nota.getFactorCambiario());
        ndj.setFechaEmision(DateContable.getDateFormat(nota.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        ndj.setFechaInsert(DateContable.getDateFormat(nota.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        ndj.setFormaPago(nota.getFormaPago());
        ndj.setGestion(nota.getGestion());
        ndj.setIdBanco(nota.getIdBanco());
        if (nota.getIdCliente() != null) {
            ndj.setIdCliente(new ComboSelect(nota.getIdCliente().getIdCliente(), nota.getIdCliente().getNombre()));
        }

        if (nota.getIdCounter() != null) {
            ndj.setIdPromotor(new ComboSelect(nota.getIdCounter().getIdPromotor(), nota.getIdCounter().getNombre() + " " + nota.getIdCounter().getApellido()));
        }
        
        ndj.setIdCuentaDeposito(nota.getIdCuentaDeposito());
        ndj.setIdEmpresa(nota.getIdEmpresa());
        ndj.setIdNotaDebito(nota.getIdNotaDebito());
        ndj.setIdTarjetaCredito(nota.getIdTarjetaCredito());
        ndj.setIdUsuarioCreador(nota.getIdUsuarioCreador());
        ndj.setMontoAdeudadoBs(nota.getMontoAdeudadoBs());
        ndj.setMontoAdeudadoUsd(nota.getMontoAdeudadoUsd());
        ndj.setMontoTotalBs(nota.getMontoTotalBs());
        ndj.setMontoTotalUsd(nota.getMontoTotalUsd());
        ndj.setNroCheque(nota.getNroCheque());
        ndj.setNroDeposito(nota.getNroDeposito());
        ndj.setNroTarjeta(nota.getNroTarjeta());
        ndj.setEstado(nota.getEstado());
        
        return ndj;
    }
    
    public static NotaDebito toNotaDebito(NotaDebitoJSON nota) {
        NotaDebito ndj = new NotaDebito();
        ndj.setCombinadoContado(nota.getCombinadoContado());
        ndj.setCombinadoContadoTipo(nota.getCombinadoContadoTipo());
        ndj.setCombinadoCredito(nota.getCombinadoCredito());
        ndj.setCombinadoTarjeta(nota.getCombinadoTarjeta());
        ndj.setCreditoDias(nota.getCreditoDias());
        ndj.setCreditoVencimiento(DateContable.toLatinAmericaDateFormat(nota.getCreditoVencimiento()));
        ndj.setFactorCambiario(nota.getFactorCambiario());
        ndj.setFechaEmision(DateContable.toLatinAmericaDateFormat(nota.getFechaEmision()));
        //ndj.setFechaInsert(DateContable.getDateFormat(nota.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        ndj.setFormaPago(nota.getFormaPago());
        ndj.setGestion(nota.getGestion());
        ndj.setIdBanco(nota.getIdBanco());
        if (nota.getIdCliente() != null) {
            if (nota.getIdCliente().getId() instanceof Double){
                Double d = (Double)nota.getIdCliente().getId() ;
                ndj.setIdCliente(new Cliente(d.intValue()));
            }else if (nota.getIdCliente().getId() instanceof BigDecimal){
                BigDecimal d = (BigDecimal)nota.getIdCliente().getId() ;
                ndj.setIdCliente(new Cliente(d.intValue()));
            }
        }

        if (nota.getIdPromotor()!= null) {
            if (nota.getIdPromotor().getId() instanceof Double){
                Double d = (Double)nota.getIdPromotor().getId() ;
                ndj.setIdCounter(new Promotor(d.intValue()));
            }else if (nota.getIdPromotor().getId() instanceof BigDecimal){
                BigDecimal d = (BigDecimal)nota.getIdPromotor().getId() ;
                ndj.setIdCounter(new Promotor(d.intValue()));
            }
        }

        
        ndj.setIdCuentaDeposito(nota.getIdCuentaDeposito());
        ndj.setIdEmpresa(nota.getIdEmpresa());
        ndj.setIdNotaDebito(nota.getIdNotaDebito());
        ndj.setIdTarjetaCredito(nota.getIdTarjetaCredito());
        ndj.setIdUsuarioCreador(nota.getIdUsuarioCreador());
        ndj.setMontoAdeudadoBs(nota.getMontoAdeudadoBs());
        ndj.setMontoAdeudadoUsd(nota.getMontoAdeudadoUsd());
        ndj.setMontoTotalBs(nota.getMontoTotalBs());
        ndj.setMontoTotalUsd(nota.getMontoTotalUsd());
        ndj.setNroCheque(nota.getNroCheque());
        ndj.setNroDeposito(nota.getNroDeposito());
        ndj.setNroTarjeta(nota.getNroTarjeta());
        ndj.setEstado(nota.getEstado());
        
        return ndj;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
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

    public ComboSelect getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(ComboSelect idPromotor) {
        this.idPromotor = idPromotor;
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

    public BigDecimal getMontoTotalBs() {
        return montoTotalBs;
    }

    public void setMontoTotalBs(BigDecimal montoTotalBs) {
        this.montoTotalBs = montoTotalBs;
    }

    public BigDecimal getMontoTotalUsd() {
        return montoTotalUsd;
    }

    public void setMontoTotalUsd(BigDecimal montototalUsd) {
        this.montoTotalUsd = montototalUsd;
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

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Integer getCreditoDias() {
        return creditoDias;
    }

    public void setCreditoDias(Integer creditoDias) {
        this.creditoDias = creditoDias;
    }

    public String getCreditoVencimiento() {
        return creditoVencimiento;
    }

    public void setCreditoVencimiento(String creditoVencimiento) {
        this.creditoVencimiento = creditoVencimiento;
    }

    public Short getCombinadoContado() {
        return combinadoContado;
    }

    public void setCombinadoContado(Short combinadoContado) {
        this.combinadoContado = combinadoContado;
    }

    public Short getCombinadoTarjeta() {
        return combinadoTarjeta;
    }

    public void setCombinadoTarjeta(Short combinadoTarjeta) {
        this.combinadoTarjeta = combinadoTarjeta;
    }

    public Short getCombinadoCredito() {
        return combinadoCredito;
    }

    public void setCombinadoCredito(Short combinadoCredito) {
        this.combinadoCredito = combinadoCredito;
    }

    public String getCombinadoContadoTipo() {
        return combinadoContadoTipo;
    }

    public void setCombinadoContadoTipo(String combinadoContadoTipo) {
        this.combinadoContadoTipo = combinadoContadoTipo;
    }

}

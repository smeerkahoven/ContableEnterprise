/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.PlanCuentas;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author xeio
 */
public class CargoBoletoJSON implements Serializable {

    private Integer idCargo;
    private Integer idEmpresa;
    private String moneda;
    private ComboSelect idCuentaMayorista;
    private BigDecimal comisionMayorista;
    private ComboSelect idCuentaAgencia;
    private BigDecimal comisionAgencia;
    private ComboSelect idCuentaPromotor;
    private BigDecimal comisionPromotor;
    private String concepto;
    private String tipo;
    private String estado;
    private String fechaInsert;
    private String usuarioCreador;
    private Integer idNotaDebito;
    private Integer idNotaDebitoTransaccion;

    public static CargoBoleto toCargoBoleto(CargoBoletoJSON json) throws CRUDException {
        CargoBoleto cargo = new CargoBoleto();

        cargo.setComisionAgencia(json.getComisionAgencia() == null ? BigDecimal.ZERO : json.getComisionAgencia());
        cargo.setComisionMayorista(json.getComisionMayorista() == null ? BigDecimal.ZERO : json.getComisionMayorista());
        cargo.setComisionPromotor(json.getComisionPromotor() == null ? BigDecimal.ZERO : json.getComisionPromotor());

        cargo.setConcepto(json.getConcepto());
        cargo.setEstado(json.getEstado());
        if (json.getIdCuentaAgencia() != null) {
            cargo.setIdCuentaAgencia(new PlanCuentas(((Double) json.getIdCuentaAgencia().getId()).intValue()));
        } else {
            throw new CRUDException("No se ha enviado la cuenta de la comision de la Agencia");
        }

        if (json.getIdCuentaMayorista() != null) {
            cargo.setIdCuentaMayorista(new PlanCuentas(((Double) json.getIdCuentaMayorista().getId()).intValue()));
        } else {
            throw new CRUDException("No se ha enviado la cuenta de la comision de Mayorista");
        }

        if (json.getIdCuentaPromotor() != null) {
            cargo.setIdCuentaPromotor(new PlanCuentas(((Double) json.getIdCuentaPromotor().getId()).intValue()));
        } else {
            throw new CRUDException("No se ha enviado la cuenta de la comision de Promotor");
        }

        cargo.setIdEmpresa(json.getIdEmpresa());
        cargo.setMoneda(json.getMoneda());
        cargo.setTipo(json.getTipo());
        cargo.setUsuarioCreador(json.getUsuarioCreador());
        cargo.setIdNotaDebito(json.getIdNotaDebito());
        cargo.setIdNotaDebitoTransaccion(json.getIdNotaDebitoTransaccion());

        return cargo;
    }

    public static CargoBoletoJSON toCargoBoletoJSON(CargoBoleto cargo) {
        CargoBoletoJSON json = new CargoBoletoJSON();

        json.setComisionAgencia(cargo.getComisionAgencia());
        json.setComisionMayorista(cargo.getComisionMayorista());
        json.setComisionPromotor(cargo.getComisionPromotor());
        json.setConcepto(cargo.getConcepto());
        json.setEstado(cargo.getEstado());
        json.setFechaInsert(DateContable.getDateFormat(cargo.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setIdCuentaAgencia(new ComboSelect(cargo.getIdCuentaAgencia().getIdPlanCuentas(), cargo.getIdCuentaAgencia().getCuenta()));
        json.setIdCuentaMayorista(new ComboSelect(cargo.getIdCuentaMayorista().getIdPlanCuentas(), cargo.getIdCuentaAgencia().getCuenta()));
        json.setIdCuentaPromotor(new ComboSelect(cargo.getIdCuentaPromotor().getIdPlanCuentas(), cargo.getIdCuentaAgencia().getCuenta()));
        json.setIdEmpresa(cargo.getIdEmpresa());
        json.setIdCargo(cargo.getIdCargo());
        json.setMoneda(cargo.getMoneda());
        json.setTipo(cargo.getTipo());
        json.setUsuarioCreador(cargo.getUsuarioCreador());
        json.setIdNotaDebito(cargo.getIdNotaDebito());
        json.setIdNotaDebitoTransaccion(cargo.getIdNotaDebitoTransaccion());

        return json;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public ComboSelect getIdCuentaMayorista() {
        return idCuentaMayorista;
    }

    public void setIdCuentaMayorista(ComboSelect idCuentaMayorista) {
        this.idCuentaMayorista = idCuentaMayorista;
    }

    public BigDecimal getComisionMayorista() {
        return comisionMayorista;
    }

    public void setComisionMayorista(BigDecimal comisionMayorista) {
        this.comisionMayorista = comisionMayorista;
    }

    public ComboSelect getIdCuentaAgencia() {
        return idCuentaAgencia;
    }

    public void setIdCuentaAgencia(ComboSelect idCuentaAgencia) {
        this.idCuentaAgencia = idCuentaAgencia;
    }

    public BigDecimal getComisionAgencia() {
        return comisionAgencia;
    }

    public void setComisionAgencia(BigDecimal comisionAgencia) {
        this.comisionAgencia = comisionAgencia;
    }

    public ComboSelect getIdCuentaPromotor() {
        return idCuentaPromotor;
    }

    public void setIdCuentaPromotor(ComboSelect idCuentaPromotor) {
        this.idCuentaPromotor = idCuentaPromotor;
    }

    public BigDecimal getComisionPromotor() {
        return comisionPromotor;
    }

    public void setComisionPromotor(BigDecimal comisionPromotor) {
        this.comisionPromotor = comisionPromotor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

}

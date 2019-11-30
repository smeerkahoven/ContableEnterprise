/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class BalanceGeneralDto implements Serializable {

    private Integer idPlanCuenta;
    private Long nroPlanCuenta;
    private Long nroPlanCuentaPadre;
    private Integer nivel;
    private Integer cuentaRegularizacion;
    private String cuenta;
    private BigDecimal saldoDebe;
    private BigDecimal saldoHaber;

    public BalanceGeneralDto(Integer idPlanCuenta, Long nroPlanCuenta, Long nroPlanCuentaPadre, Integer nivel, Integer cuentaRegularizacion, String cuenta, BigDecimal saldoDebe, BigDecimal saldoHaber) {
        this.idPlanCuenta = idPlanCuenta;
        this.nroPlanCuenta = nroPlanCuenta;
        this.nroPlanCuentaPadre = nroPlanCuentaPadre;
        this.nivel = nivel;
        this.cuentaRegularizacion = cuentaRegularizacion;
        this.cuenta = cuenta;
        this.saldoDebe = saldoDebe;
        this.saldoHaber = saldoHaber;
    }

    
    
    public Integer getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(Integer idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public Long getNroPlanCuenta() {
        return nroPlanCuenta;
    }

    public void setNroPlanCuenta(Long nroPlanCuenta) {
        this.nroPlanCuenta = nroPlanCuenta;
    }

    public Long getNroPlanCuentaPadre() {
        return nroPlanCuentaPadre;
    }

    public void setNroPlanCuentaPadre(Long nroPlanCuentaPadre) {
        this.nroPlanCuentaPadre = nroPlanCuentaPadre;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getCuentaRegularizacion() {
        return cuentaRegularizacion;
    }

    public void setCuentaRegularizacion(Integer cuentaRegularizacion) {
        this.cuentaRegularizacion = cuentaRegularizacion;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getSaldoDebe() {
        return saldoDebe;
    }

    public void setSaldoDebe(BigDecimal saldoDebe) {
        this.saldoDebe = saldoDebe;
    }

    public BigDecimal getSaldoHaber() {
        return saldoHaber;
    }

    public void setSaldoHaber(BigDecimal saldoHaber) {
        this.saldoHaber = saldoHaber;
    }

    
    
}

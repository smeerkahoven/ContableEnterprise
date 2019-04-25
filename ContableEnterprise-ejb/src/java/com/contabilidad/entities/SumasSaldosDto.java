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
public class SumasSaldosDto implements Serializable {

    private Integer idPlanCuenta;
    private String cuenta;
    private Long nroPlanCuenta;
    private Long nroPlanCuentaPadre;
    private BigDecimal sumaDebe;
    private BigDecimal sumaHaber;
    private BigDecimal saldoDebe;
    private BigDecimal saldoHaber;
    private Integer nivel;

    public SumasSaldosDto() {
    }
    

    public SumasSaldosDto(Integer idPlanCuenta, String cuenta, Long nroPlanCuenta, Long nroPlanCuentaPadre, BigDecimal sumaDebe, BigDecimal sumaHaber, BigDecimal saldoDebe, BigDecimal saldoHaber, Integer nivel) {
        this.idPlanCuenta = idPlanCuenta;
        this.cuenta = cuenta;
        this.nroPlanCuenta = nroPlanCuenta;
        this.nroPlanCuentaPadre = nroPlanCuentaPadre;
        this.sumaDebe = sumaDebe;
        this.sumaHaber = sumaHaber;
        this.saldoDebe = saldoDebe;
        this.saldoHaber = saldoHaber;
        this.nivel = nivel;
    }

    
    public Integer getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(Integer idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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

    public BigDecimal getSumaDebe() {
        return sumaDebe;
    }

    public void setSumaDebe(BigDecimal sumaDebe) {
        this.sumaDebe = sumaDebe;
    }

    public BigDecimal getSumaHaber() {
        return sumaHaber;
    }

    public void setSumaHaber(BigDecimal sumaHaber) {
        this.sumaHaber = sumaHaber;
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

}

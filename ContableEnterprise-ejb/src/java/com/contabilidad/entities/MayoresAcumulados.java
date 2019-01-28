/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class MayoresAcumulados {

    private BigDecimal montoAcumuladoDebe;
    private BigDecimal montoAcumuladoHaber;
    private BigDecimal saldoDebe;
    private BigDecimal saldoHaber;

    public MayoresAcumulados() {
        this.montoAcumuladoDebe = null;
        this.montoAcumuladoHaber = null;
    }

    public MayoresAcumulados(BigDecimal montoAcumuladoDebe, BigDecimal montoAcumuladoHaber) {
        this.montoAcumuladoDebe = montoAcumuladoDebe;
        this.montoAcumuladoHaber = montoAcumuladoHaber;
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

    public BigDecimal getMontoAcumuladoDebe() {
        return montoAcumuladoDebe;
    }

    public void setMontoAcumuladoDebe(BigDecimal montoAcumuladoDebe) {
        this.montoAcumuladoDebe = montoAcumuladoDebe;
    }

    public BigDecimal getMontoAcumuladoHaber() {
        return montoAcumuladoHaber;
    }

    public void setMontoAcumuladoHaber(BigDecimal montoAcumuladoHaber) {
        this.montoAcumuladoHaber = montoAcumuladoHaber;
    }

}

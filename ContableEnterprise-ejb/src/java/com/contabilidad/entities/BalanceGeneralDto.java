/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.seguridad.utils.Contabilidad;
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
    private Integer cuentaRegularizacion;
    private BigDecimal saldoDebe;
    private BigDecimal saldoHaber;
    private Integer nivel;
private String cuenta;

    public BalanceGeneralDto(Integer idPlanCuenta, Long nroPlanCuenta, Long nroPlanCuentaPadre, Integer cuentaRegularizacion, BigDecimal saldoDebe, BigDecimal saldoHaber, Integer nivel, String cuenta) {
        this.idPlanCuenta = idPlanCuenta;
        this.nroPlanCuenta = nroPlanCuenta;
        this.nroPlanCuentaPadre = nroPlanCuentaPadre;
        this.cuentaRegularizacion = cuentaRegularizacion;
        this.saldoDebe = saldoDebe;
        this.saldoHaber = saldoHaber;
        this.nivel = nivel;
        this.cuenta = cuenta;
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
        
        if (saldoDebe!= null)
            saldoDebe.setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_UP);
        
        return  saldoDebe;
    }

    public void setSaldoDebe(BigDecimal saldoDebe) {
        this.saldoDebe = saldoDebe;
    }

    public BigDecimal getSaldoHaber() {
               if (saldoHaber!= null)
            saldoHaber.setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_UP);
  
               
        return saldoHaber;
    }

    public void setSaldoHaber(BigDecimal saldoHaber) {
        this.saldoHaber = saldoHaber;
    }

    @Override
    public String toString() {
        return "BalanceGeneralDto{" + "idPlanCuenta=" + idPlanCuenta + ", cuenta=" + cuenta + ", nroPlanCuenta=" + nroPlanCuenta + ", nroPlanCuentaPadre=" + nroPlanCuentaPadre + ", cuentaRegularizacion=" + cuentaRegularizacion + ", saldoDebe=" + saldoDebe + ", saldoHaber=" + saldoHaber + ", nivel=" + nivel + '}';
    }

    
    
    
    
}

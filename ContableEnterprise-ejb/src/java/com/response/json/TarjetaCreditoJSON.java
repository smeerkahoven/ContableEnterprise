/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.agencia.entities.TarjetaCredito;
import com.contabilidad.entities.PlanCuentas;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author xeio
 */
public class TarjetaCreditoJSON implements Serializable {

    private Integer idTarjetaCredito;
    private String sigla;
    private String denominacion;
    private BigInteger planCuentaMonExt;
    private String planCuentaMonExtNombre;
    private BigInteger planCuentaMonNac;
    private String planCuentaMonNacNombre;

    public static TarjetaCreditoJSON toJSON(TarjetaCredito tc) {
        TarjetaCreditoJSON json = new TarjetaCreditoJSON();
        json.setDenominacion(tc.getDenominacion());
        json.setIdTarjetaCredito(tc.getIdTarjetaCredito());
        json.setSigla(tc.getSigla());
        //json.setPlanCuentaMonExt(tc.getPlanCuentaMonExt().getIdPlanCuentas());
        //json.setPlanCuentaMonExtNombre(tc.getPlanCuentaMonExt().getCuenta());
        //json.setPlanCuentaMonNac(tc.getPlanCuentaMonNac().getIdPlanCuentas());
        //json.setPlanCuentaMonNacNombre(tc.getPlanCuentaMonNac().getCuenta());
        return json ;
    }
    
    public static TarjetaCredito toTarjetaCredito(TarjetaCreditoJSON json){
        TarjetaCredito t = new TarjetaCredito();
        t.setDenominacion(json.getDenominacion());
        t.setIdTarjetaCredito(json.getIdTarjetaCredito());
        t.setSigla(json.getSigla());
        //t.setPlanCuentaMonExt(new PlanCuentas(json.getPlanCuentaMonExt()));
        //t.setPlanCuentaMonNac(new PlanCuentas(json.getPlanCuentaMonNac()));
        
        return t ;
    }

    public BigInteger getPlanCuentaMonExt() {
        return planCuentaMonExt;
    }

    public void setPlanCuentaMonExt(BigInteger planCuentaMonExt) {
        this.planCuentaMonExt = planCuentaMonExt;
    }

    public BigInteger getPlanCuentaMonNac() {
        return planCuentaMonNac;
    }

    public void setPlanCuentaMonNac(BigInteger planCuentaMonNac) {
        this.planCuentaMonNac = planCuentaMonNac;
    }

    public String getPlanCuentaMonNacNombre() {
        return planCuentaMonNacNombre;
    }

    public void setPlanCuentaMonNacNombre(String planCuentaMonNacNombre) {
        this.planCuentaMonNacNombre = planCuentaMonNacNombre;
    }

    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getPlanCuentaMonExtNombre() {
        return planCuentaMonExtNombre;
    }

    public void setPlanCuentaMonExtNombre(String planCuentaMonExtNombre) {
        this.planCuentaMonExtNombre = planCuentaMonExtNombre;
    }

}

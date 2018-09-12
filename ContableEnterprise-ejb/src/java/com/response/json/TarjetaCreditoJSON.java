/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.agencia.entities.TarjetaCredito;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class TarjetaCreditoJSON implements Serializable {

    private Integer idTarjetaCredito;
    private String sigla;
    private String denominacion;
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

}

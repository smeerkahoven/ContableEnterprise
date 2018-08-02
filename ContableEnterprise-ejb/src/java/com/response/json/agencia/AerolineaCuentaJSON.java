/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.AerolineaCuenta;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class AerolineaCuentaJSON implements Serializable {
    private Integer idAerolineaCuenta;
    private Integer idAerolinea;
    private Integer idPlanCuentas;
    private String tipo;
    private String moneda;
//
    private Long nroPlanCuenta ;
    private String nombreCuenta;
    
    public static List<AerolineaCuentaJSON> toAerolinaJSON(List<Object[]>l){
        LinkedList<AerolineaCuentaJSON> newL = new LinkedList<>();
        Iterator i = l.iterator();
        while (i.hasNext()){
            Object[] o = (Object[])i.next();
            newL.add(toAerolineaJSON(o));
        }
        
        return newL ;
    }

    public static AerolineaCuentaJSON  toAerolineaJSON(Object[] o) {
        AerolineaCuentaJSON newc = new AerolineaCuentaJSON();
        newc.setIdAerolinea((Integer)o[0]);
        newc.setIdAerolineaCuenta((Integer)o[1]);
        newc.setIdPlanCuentas((Integer)o[2]);
        newc.setTipo((String)o[3]);
        newc.setMoneda((String)o[4]);
        newc.setNroPlanCuenta((Long)o[5]);
        newc.setNombreCuenta((String)o[6]);
        return newc ;
    }
    
    
    public static AerolineaCuenta toAerolinea(AerolineaCuentaJSON json){
        AerolineaCuenta a = new AerolineaCuenta();
        a.setIdAerolinea(json.getIdAerolinea());
        a.setIdAerolineaCuenta(json.getIdAerolineaCuenta());
        a.setIdPlanCuentas(json.getIdPlanCuentas());
        a.setMoneda(json.getMoneda());
        a.setTipo(json.getTipo());
        
        return a ;
                
    }

    public Long getNroPlanCuenta() {
        return nroPlanCuenta;
    }

    public void setNroPlanCuenta(Long nroPlanCuenta) {
        this.nroPlanCuenta = nroPlanCuenta;
    }
    
    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    
    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }
    
    public Integer getIdAerolineaCuenta() {
        return idAerolineaCuenta;
    }

    public void setIdAerolineaCuenta(Integer idAerolineaCuenta) {
        this.idAerolineaCuenta = idAerolineaCuenta;
    }

    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    
    
}

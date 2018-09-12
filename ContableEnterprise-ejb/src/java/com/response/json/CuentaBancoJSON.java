/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.agencia.entities.CuentaBanco;
import java.math.BigInteger;

/**
 *
 * @author xeio
 */
public class CuentaBancoJSON {

    private Integer idPlanCuentas;
    private String cuenta;
    private int idBanco;
    private int idCuentaBanco;
    private String descripcion;
    
    public static CuentaBanco toCuentaBanco(CuentaBancoJSON json){
        CuentaBanco c = new CuentaBanco();
        c.setDescripcion(json.getDescripcion());
        c.setIdBanco(json.getIdBanco());
        c.setIdCuentaBanco(json.getIdCuentaBanco());
        c.setIdPlanCuentas(json.getIdPlanCuentas());
        
        return c ;
    }
    
    public static CuentaBancoJSON toCuentaBancoJSON(CuentaBanco c){
        CuentaBancoJSON json = new CuentaBancoJSON();
        json.setCuenta(c.getDescripcion());
        json.setIdBanco(c.getIdBanco());
        json.setIdCuentaBanco(c.getIdCuentaBanco());
        json.setIdPlanCuentas(c.getIdPlanCuentas());
        return json ;
    }

    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdCuentaBanco() {
        return idCuentaBanco;
    }

    public void setIdCuentaBanco(int idCuentaBanco) {
        this.idCuentaBanco = idCuentaBanco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author xeio
 */
public class Pasajero {
    
    private String id ;
    private String nombrePasajero ;
    private Long numero ;
    private HashMap<String, BigDecimal> importes = new HashMap<>();

    private String tipoCupon ;

    public String getTipoCupon() {
        return tipoCupon;
    }

    public void setTipoCupon(String tipoCupon) {
        this.tipoCupon = tipoCupon;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public HashMap<String, BigDecimal> getImportes() {
        return importes;
    }

    public void setImportes(HashMap<String, BigDecimal> importes) {
        this.importes = importes;
    }

    
    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
    
}

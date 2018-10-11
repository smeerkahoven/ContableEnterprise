/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

/**
 *
 * @author xeio
 */
public enum Estado {
    ERROR("E"),
    ANULADO("A"),
    PENDIENTE("P"),
    COMPLETADO("C");
    
    private final String value ;
    
    private Estado(String value) {
        this.value = value ;
    }
    
    public String getValue () {
        return this.value ;
    }
    
}

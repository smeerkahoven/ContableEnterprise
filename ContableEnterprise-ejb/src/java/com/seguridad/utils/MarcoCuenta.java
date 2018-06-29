/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.utils;

import java.io.Serializable;

/**
 *
 * @author xeio
 */
public enum MarcoCuenta implements Serializable {
    
    ACTIVOS (1),
    PASIVOS (2),
    INGRESOS (3),
    EGRESOS(4),
    COSTOS (5)
    ;
    
    private final int code;
    private MarcoCuenta (int code) {
        this.code = code ;
    }
    
    public int getCode(){
        return code ;
    }
}

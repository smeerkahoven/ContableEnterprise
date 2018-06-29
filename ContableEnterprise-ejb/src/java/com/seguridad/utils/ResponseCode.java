/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.utils;

import java.io.Serializable;

/**
 *
 * @author Cheyo
 */
public enum  ResponseCode implements Serializable {
    
    USUARIO_AUTORIZADO (100 ),
    CREDENCIALES_INCORRECTA ( 101) ,
    USUARIO_NO_AUTORIZADO ( 102 ),
    USUARIO_INEXISTENTE (103),
    USUARIO_INACTIVO (104),
    
    RESTFUL_ERROR (200),
    RESTFUL_SUCCESS (201),
    RESTFUL_WARNING(202)
    ;

    private final int code ;
    private ResponseCode(int code) {
        this.code = code ;
    }

    public int getCode() {
        return code;
    }   
    
}

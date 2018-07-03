/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class EntidadJSON implements Serializable {

    private Integer idEntidad;

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public EntidadJSON () {
        
    }
    
    public EntidadJSON(Integer idEntidad){
        this.idEntidad = idEntidad;
    }
    
}

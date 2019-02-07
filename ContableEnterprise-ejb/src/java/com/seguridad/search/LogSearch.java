/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.search;

import com.seguridad.utils.ComboSelect;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class LogSearch implements Serializable {
    
    private ComboSelect username ;
    private String fechaInicio ;
    private String fechaFin ;

    public ComboSelect getUsername() {
        return username;
    }

    public void setUsername(ComboSelect username) {
        this.username = username;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
    
}

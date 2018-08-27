/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad.util;

import com.seguridad.control.entities.Entidad;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class RestRequest implements Serializable {

    private String token;

    private Object content;

    private String formName;

    private String ip;
    
    private Entidad entidad ;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String form) {
        this.formName = form;
    }

    public String getToken() {
        return token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    
    

}

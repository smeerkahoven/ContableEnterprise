/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.seguridad;

import com.seguridad.control.entities.Log;
import com.seguridad.utils.DateContable;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class LogJson implements Serializable {
    
    private Integer idLog;
    private String fechaLog;
    private String accion;
    private String usuario;
    private String formulario;
    private String ip;
    private String comentario;

    public static LogJson getLogJson (Log data){
        LogJson json = new LogJson();
        
        json.setAccion(data.getAccion());
        json.setComentario(data.getComentario());
        json.setFechaLog(DateContable.getDateFormat(data.getFechaLog(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setFormulario(data.getFormulario());
        json.setIp(data.getIp());
        json.setUsuario(data.getUsuario());
        
        return json ;
    }
    
    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getFechaLog() {
        return fechaLog;
    }

    public void setFechaLog(String fechaLog) {
        this.fechaLog = fechaLog;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.seguridad.control.entities.Rol;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author xeio
 */
public class RolJSON implements Serializable {

    private int idRol;
    private String nombre;
    private String status;
    private String fechaAlta;
    private String fechaBaja;

    private List<ModuloJSON> permisos;

    public RolJSON(){
        
    }
    
    public static Rol convertToRol(RolJSON r ){
        Rol rol = new Rol();
        r.setIdRol(r.getIdRol());
        r.setNombre(r.getNombre());
        r.setFechaAlta(r.getFechaAlta());
        r.setFechaBaja(r.getFechaBaja());
        
        return rol ;
    }
    
    public RolJSON(Rol r) {
        this.idRol = r.getIdRol() ;
        this.nombre = r.getNombre() ;
        this.fechaAlta = DateContable.getDateFormat(r.getFechaBaja(), "dd/MM/yyyy HH:mm:ss"); 
        this.fechaBaja = DateContable.getDateFormat(r.getFechaBaja(), "dd/MM/yyyy HH:mm:ss");
        this.status = r.getStatus() ;
    }
    
    
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public List<ModuloJSON> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<ModuloJSON> permisos) {
        this.permisos = permisos;
    }

}

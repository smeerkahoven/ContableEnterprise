/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.AerolineaImpuesto;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class AerolineaImpuestoJSON {

    private Integer idAerolineaImpuesto;
    private String nombre;
    private Integer idAerolinea;

    public AerolineaImpuestoJSON() {

    }

    public static AerolineaImpuesto toAerolineaImpuesto(AerolineaImpuestoJSON json) {
        AerolineaImpuesto aim = new AerolineaImpuesto();
        aim.setIdAerolinea(json.getIdAerolinea());
        aim.setIdAerolineaImpuesto(json.getIdAerolineaImpuesto());
        aim.setNombre(json.getNombre());
        
        return aim ;
    }
    
    
    public static List<AerolineaImpuesto> toAerolineaImpuesto(List<AerolineaImpuestoJSON> l){
        LinkedList r = new LinkedList();
        l.forEach((x)->{
            AerolineaImpuestoJSON tmp = (AerolineaImpuestoJSON)x ;
            AerolineaImpuesto ai = toAerolineaImpuesto(tmp);
            
            r.add(ai);
        });
        
        return r ;
    }
    
    public Integer getIdAerolineaImpuesto() {
        return idAerolineaImpuesto;
    }

    public void setIdAerolineaImpuesto(Integer idAerolineaImpuesto) {
        this.idAerolineaImpuesto = idAerolineaImpuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

}

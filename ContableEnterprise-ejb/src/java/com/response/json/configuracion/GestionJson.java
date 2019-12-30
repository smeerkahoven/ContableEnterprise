/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.configuracion;

import com.configuracion.entities.Gestion;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class GestionJson implements Serializable {

    private Integer idGestion;
    private String nombreGestion;
    private String fechaInicio;
    private String fechaFin;
    private String fechaFinReal;
    private String estado;

    public static Gestion toGestion(GestionJson json) {

        Gestion gestion = new Gestion();
        gestion.setEstado(json.estado);
        gestion.setFechaFin(DateContable.toLatinAmericaDateFormat(json.getFechaFin()));
        gestion.setFechaFinReal(DateContable.toLatinAmericaDateFormat(json.getFechaFinReal()));
        gestion.setFechaInicio(DateContable.toLatinAmericaDateFormat(json.getFechaInicio()));
        gestion.setNombreGestion(json.getNombreGestion());

        return gestion;
    }

    public static List<GestionJson> toGestionJsonList(List<Gestion> o) {

        List<GestionJson> l = new LinkedList<>();
        Iterator i = o.iterator();
        while (i.hasNext()) {
            Gestion index = (Gestion) i.next();
            l.add(toGestionJson(index));
        }
        return l;

    }

    public static GestionJson toGestionJson(Gestion gestion) {
        GestionJson json = new GestionJson();

        json.setEstado(gestion.getEstado());
        json.setFechaFin(DateContable.getDateFormat(gestion.getFechaFin(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaFinReal(DateContable.getDateFormat(gestion.getFechaFinReal(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaInicio(DateContable.getDateFormat(gestion.getFechaInicio(), DateContable.LATIN_AMERICA_FORMAT));
        json.setIdGestion(gestion.getIdGestion());
        json.setNombreGestion(gestion.getNombreGestion());

        return json;
    }

    public Integer getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Integer idGestion) {
        this.idGestion = idGestion;
    }

    public String getNombreGestion() {
        return nombreGestion;
    }

    public void setNombreGestion(String nombreGestion) {
        this.nombreGestion = nombreGestion;
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

    public String getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(String fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}

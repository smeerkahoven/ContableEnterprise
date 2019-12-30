/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "tb_gestion")
@NamedQueries({
    @NamedQuery(name = "Gestion.findAll", query = "SELECT g FROM Gestion g")
    ,
    @NamedQuery(name = "Gestion.findById", query = "SELECT g FROM Gestion g WHERE g.idGestion=:idGestion")
    ,
    @NamedQuery(name = "Gestion.updateByEstado", query = "UPDATE Gestion g SET g.estado=:estado WHERE g.idGestion=:idGestion")
    ,
    @NamedQuery(name = "Gestion.findActivo", query = "SELECT g FROM Gestion g WHERE g.estado='A' "),})

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "Gestion.initiateGestion",
            procedureName = "initiateGestion"
    )

})

public class Gestion extends Entidad {

    public static final String ACTIVO = "A";
    public static final String FINALIZADO = "F";

    @Id
    @Column(name = "id_gestion")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idGestion;

    @Column(name = "nombre_gestion")
    private String nombreGestion;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "fecha_real_fin")
    private Date fechaFinReal;

    @Column(name = "estado")
    private String estado;

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(Date fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int getId() throws CRUDException {
        return idGestion;
    }

}

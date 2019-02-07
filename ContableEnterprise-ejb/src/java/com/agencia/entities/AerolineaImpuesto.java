/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_aerolinea_impuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AerolineaImpuesto.findAll", query = "SELECT a FROM AerolineaImpuesto a WHERE a.idAerolinea=:idAerolinea")})
public class AerolineaImpuesto extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_aerolinea_impuesto")
    private Integer idAerolineaImpuesto;
    
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;

    /*Column(name = "id_aerolinea")
    private int idAerolinea;*/
 /*@JoinColumn(name = "id_aerolinea", referencedColumnName = "idAerolinea")
    @ManyToOne (optional = false, fetch = FetchType.LAZY)
    private Aerolinea idAerolinea;*/
    @Column (name = "id_aerolinea")
    private Integer idAerolinea;

    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public AerolineaImpuesto() {
    }

    public AerolineaImpuesto(Integer idAerolineaImpuesto) {
        this.idAerolineaImpuesto = idAerolineaImpuesto;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAerolineaImpuesto != null ? idAerolineaImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AerolineaImpuesto)) {
            return false;
        }
        AerolineaImpuesto other = (AerolineaImpuesto) object;
        if ((this.idAerolineaImpuesto == null && other.idAerolineaImpuesto != null) || (this.idAerolineaImpuesto != null && !this.idAerolineaImpuesto.equals(other.idAerolineaImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.AerolineaImpuesto[ idAerolineaImpuesto=" + idAerolineaImpuesto + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdAerolineaImpuesto();
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author xeio
 */
@Embeddable
public class ComprobanteContablePK implements Serializable {


    @Basic(optional = false)
    @Column(name = "id_libro")
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int idLibro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion")
    private int gestion;

    public ComprobanteContablePK() {
    }

    public ComprobanteContablePK(int idLibro, int gestion) {
        this.idLibro = idLibro;
        this.gestion = gestion;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLibro;
        hash += (int) gestion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprobanteContablePK)) {
            return false;
        }
        ComprobanteContablePK other = (ComprobanteContablePK) object;
        if (this.idLibro != other.idLibro) {
            return false;
        }
        if (this.gestion != other.gestion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.ComprobanteContablePK[ idLibro=" + idLibro + ", gestion=" + gestion + " ]";
    }
    
}

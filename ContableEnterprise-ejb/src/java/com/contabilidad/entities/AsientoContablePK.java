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
public class AsientoContablePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_asiento", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idAsiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion",  updatable = false)
    private int gestion;

    public AsientoContablePK() {
    }

    public AsientoContablePK(int idAsiento, int gestion) {
        this.idAsiento = idAsiento;
        this.gestion = gestion;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
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
        hash += (int) idAsiento;
        hash += (int) gestion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsientoContablePK)) {
            return false;
        }
        AsientoContablePK other = (AsientoContablePK) object;
        if (this.idAsiento != other.idAsiento) {
            return false;
        }
        if (this.gestion != other.gestion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.AsientoContablePK[ idAsiento=" + idAsiento + ", gestion=" + gestion + " ]";
    }
    
}

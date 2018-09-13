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
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author xeio
 */
@Embeddable
public class BoletoPK extends Entidad {

    @Basic(optional = false)
    @Column(name = "id_boleto")
    private int idBoleto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion")
    private int gestion;

    public BoletoPK() {
    }

    public BoletoPK(int idBoleto, int gestion) {
        this.idBoleto = idBoleto;
        this.gestion = gestion;
    }

    public int getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(int idBoleto) {
        this.idBoleto = idBoleto;
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
        hash += (int) idBoleto;
        hash += (int) gestion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoletoPK)) {
            return false;
        }
        BoletoPK other = (BoletoPK) object;
        if (this.idBoleto != other.idBoleto) {
            return false;
        }
        if (this.gestion != other.gestion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.BoletoPK[ idBoleto=" + idBoleto + ", gestion=" + gestion + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idBoleto ;
    }
    
}

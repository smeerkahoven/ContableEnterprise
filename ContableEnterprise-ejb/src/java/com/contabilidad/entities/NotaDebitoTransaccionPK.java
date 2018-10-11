/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author xeio
 */
@Embeddable
public class NotaDebitoTransaccionPK extends Entidad {

    @Basic(optional = false)
    @Column(name = "id_nota_debito_transaccion")
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int idNotaDebitoTransaccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion")
    private int gestion;

    public NotaDebitoTransaccionPK() {
    }

    public NotaDebitoTransaccionPK(int idNotaDebitoTransaccion, int gestion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
        this.gestion = gestion;
    }

    public int getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(int idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
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
        hash += (int) idNotaDebitoTransaccion;
        hash += (int) gestion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaDebitoTransaccionPK)) {
            return false;
        }
        NotaDebitoTransaccionPK other = (NotaDebitoTransaccionPK) object;
        if (this.idNotaDebitoTransaccion != other.idNotaDebitoTransaccion) {
            return false;
        }
        if (this.gestion != other.gestion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.NotaDebitoTransaccionPK[ idNotaDebitoTransaccion=" + idNotaDebitoTransaccion + ", gestion=" + gestion + " ]";
    }

    @Override
    public int getId() throws CRUDException {
            return this.getIdNotaDebitoTransaccion();
    }
    
}

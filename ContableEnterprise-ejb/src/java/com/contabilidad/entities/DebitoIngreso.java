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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_debito_ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DebitoIngreso.findAll", query = "SELECT d FROM DebitoIngreso d")
    ,@NamedQuery(name = "DebitoIngreso.findByIdDebitoIngreso", query = "SELECT d FROM DebitoIngreso d WHERE d.idDebitoIngreso = :idDebitoIngreso")
    ,@NamedQuery(name = "DebitoIngreso.findAllIngresoCajaByNotaDebito", query = "SELECT d FROM DebitoIngreso d WHERE d.idNotaDebito=:idNotaDebito")
    ,@NamedQuery(name = "DebitoIngreso.findByIngresoCajaNotaDebito", query = "SELECT d FROM DebitoIngreso d WHERE d.idNotaDebito=:idNotaDebito and d.idIngresoCaja=:idIngresoCaja")
    ,@NamedQuery(name = "DebitoIngreso.findAllByIngresoCaja", query = "SELECT d FROM DebitoIngreso d WHERE d.idIngresoCaja=:idIngresoCaja ORDER BY d.idNotaDebito")
})
public class DebitoIngreso extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_debito_ingreso")
    private Integer idDebitoIngreso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota_debito")
    private NotaDebito idNotaDebito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ingreso_caja")
    private IngresoCaja idIngresoCaja;

    public DebitoIngreso() {
    }

    public DebitoIngreso(Integer idDebitoIngreso) {
        this.idDebitoIngreso = idDebitoIngreso;
    }

    public NotaDebito getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(NotaDebito idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public IngresoCaja getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(IngresoCaja idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public Integer getIdDebitoIngreso() {
        return idDebitoIngreso;
    }

    public void setIdDebitoIngreso(Integer idDebitoIngreso) {
        this.idDebitoIngreso = idDebitoIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDebitoIngreso != null ? idDebitoIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DebitoIngreso)) {
            return false;
        }
        DebitoIngreso other = (DebitoIngreso) object;
        if ((this.idDebitoIngreso == null && other.idDebitoIngreso != null) || (this.idDebitoIngreso != null && !this.idDebitoIngreso.equals(other.idDebitoIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.DebitoIngreso[ idDebitoIngreso=" + idDebitoIngreso + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idDebitoIngreso;
    }

}

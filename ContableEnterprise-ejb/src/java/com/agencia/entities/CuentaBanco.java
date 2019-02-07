/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_cuenta_banco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBanco.findAll", query = "SELECT c FROM CuentaBanco c")
    ,
    @NamedQuery(name = "CuentaBanco.countCuentasByBanco", query = "SELECT count(c.idCuentaBanco) FROM CuentaBanco c WHERE c.idBanco=:idBanco")
})
public class CuentaBanco extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_cuenta_banco")
    private Integer idCuentaBanco;

    @Column(name = "id_plan_cuentas")
    private Integer idPlanCuentas;

    @Column(name = "id_banco")
    private Integer idBanco;

    @Column(name = "descripcion")
    private String descripcion;

    public CuentaBanco() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public CuentaBanco(Integer idCuentaBanco) {
        this.idCuentaBanco = idCuentaBanco;
    }

    public Integer getIdCuentaBanco() {
        return idCuentaBanco;
    }

    public void setIdCuentaBanco(Integer idCuentaBanco) {
        this.idCuentaBanco = idCuentaBanco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuentaBanco != null ? idCuentaBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaBanco)) {
            return false;
        }
        CuentaBanco other = (CuentaBanco) object;
        if ((this.idCuentaBanco == null && other.idCuentaBanco != null) || (this.idCuentaBanco != null && !this.idCuentaBanco.equals(other.idCuentaBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.CuentaBanco[ idCuentaBanco=" + idCuentaBanco + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdCuentaBanco();
    }

}

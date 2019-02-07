/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.contabilidad.entities.PlanCuentas;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "cnt_tarjetas_credito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarjetaCredito.findAll", query = "SELECT t FROM TarjetaCredito t")
    , @NamedQuery(name = "TarjetaCredito.findByIdTarjetaCredito", query = "SELECT t FROM TarjetaCredito t WHERE t.idTarjetaCredito = :idTarjetaCredito")
    , @NamedQuery(name = "TarjetaCredito.findBySigla", query = "SELECT t FROM TarjetaCredito t WHERE t.sigla = :sigla")
    , @NamedQuery(name = "TarjetaCredito.findByDenominacion", query = "SELECT t FROM TarjetaCredito t WHERE t.denominacion = :denominacion")})
public class TarjetaCredito extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_tarjeta_credito")
    private Integer idTarjetaCredito;

    @Column(name = "sigla", length = 4)
    private String sigla;
    
    @Column(name = "denominacion", length = 45)
    private String denominacion;
    
    @JoinColumn(name = "plan_cuenta_mon_ext", referencedColumnName = "id_plan_cuentas")
    @ManyToOne(optional = false)
    private PlanCuentas planCuentaMonExt;
    
    @JoinColumn(name = "plan_cuenta_mon_nac", referencedColumnName = "id_plan_cuentas")
    @ManyToOne(optional = false)
    private PlanCuentas planCuentaMonNac;

    public TarjetaCredito() {
    }

    public TarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public TarjetaCredito(Integer idTarjetaCredito, String sigla, String denominacion) {
        this.idTarjetaCredito = idTarjetaCredito;
        this.sigla = sigla;
        this.denominacion = denominacion;
    }

    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public PlanCuentas getPlanCuentaMonExt() {
        return planCuentaMonExt;
    }

    public void setPlanCuentaMonExt(PlanCuentas planCuentaMonExt) {
        this.planCuentaMonExt = planCuentaMonExt;
    }

    public PlanCuentas getPlanCuentaMonNac() {
        return planCuentaMonNac;
    }

    public void setPlanCuentaMonNac(PlanCuentas planCuentaMonNac) {
        this.planCuentaMonNac = planCuentaMonNac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarjetaCredito != null ? idTarjetaCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaCredito)) {
            return false;
        }
        TarjetaCredito other = (TarjetaCredito) object;
        if ((this.idTarjetaCredito == null && other.idTarjetaCredito != null) || (this.idTarjetaCredito != null && !this.idTarjetaCredito.equals(other.idTarjetaCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.TarjetaCredito[ idTarjetaCredito=" + idTarjetaCredito + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdTarjetaCredito();
    }
    
    
    
}

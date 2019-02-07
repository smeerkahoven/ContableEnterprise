/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;
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
@Table(name = "cnt_aerolinea_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AerolineaCuenta.findAll", query = "SELECT a FROM AerolineaCuenta a"),
    @NamedQuery(name = "AerolineaCuenta.exists", query = "SELECT a FROM AerolineaCuenta a WHERE a.idAerolinea=:idAerolinea and a.idPlanCuentas=:idPlanCuentas and a.tipo=:tipo and a.moneda=:moneda"),
    @NamedQuery(name = "AerolineaCuenta.find", query = "SELECT a FROM AerolineaCuenta a WHERE a.idAerolinea=:idAerolinea and a.tipo=:tipo and a.moneda=:moneda")
})
public class AerolineaCuenta extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_aerolinea_cuenta")
    private Integer idAerolineaCuenta;
    
    @Column(name = "id_aerolinea")
    private Integer idAerolinea ;
    
    @Column(name="id_plan_cuentas")
    private Integer idPlanCuentas;
    
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;

    public AerolineaCuenta() {
    }

    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    
    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    
    public AerolineaCuenta(Integer idAerolineaCuenta) {
        this.idAerolineaCuenta = idAerolineaCuenta;
    }

    public Integer getIdAerolineaCuenta() {
        return idAerolineaCuenta;
    }

    public void setIdAerolineaCuenta(Integer idAerolineaCuenta) {
        this.idAerolineaCuenta = idAerolineaCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAerolineaCuenta != null ? idAerolineaCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AerolineaCuenta)) {
            return false;
        }
        AerolineaCuenta other = (AerolineaCuenta) object;
        if ((this.idAerolineaCuenta == null && other.idAerolineaCuenta != null) || (this.idAerolineaCuenta != null && !this.idAerolineaCuenta.equals(other.idAerolineaCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.AerolineaCuenta[ idAerolineaCuenta=" + idAerolineaCuenta + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdAerolineaCuenta();
    }
    
}

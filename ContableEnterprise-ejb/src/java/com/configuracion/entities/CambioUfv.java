/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_cambio_ufv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CambioUfv.findAll", query = "SELECT c FROM CambioUfv c ORDER BY c.fecha DESC")})
public class CambioUfv extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;

    public CambioUfv() {
    }

    public CambioUfv(Date fecha) {
        this.fecha = fecha;
    }

    public CambioUfv(Date fecha, BigDecimal valor) {
        this.fecha = fecha;
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CambioUfv)) {
            return false;
        }
        CambioUfv other = (CambioUfv) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.configuracion.entities.CambioUfv[ fecha=" + fecha + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return 0 ;
    }
    
    
}

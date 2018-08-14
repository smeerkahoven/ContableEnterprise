/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.entities;

import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "tb_password_recover")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PasswordRecover.findAll", query = "SELECT p FROM PasswordRecover p")
    ,
    @NamedQuery(name = "PasswordRecover.findAt", query = "SELECT p FROM PasswordRecover p WHERE p.idPasswordRecover=:idPasswordRecover")})
public class PasswordRecover extends Entidad {

    public static final String ACTIVO = "A";
    public static final String INACTIVO = "I";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_password_recover", length = 256, updatable = false)
    private String idPasswordRecover;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "fecha_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcceso;

    public PasswordRecover() {
    }

    public Date getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public PasswordRecover(String idPasswordRecover) {
        this.idPasswordRecover = idPasswordRecover;
    }

    public String getIdPasswordRecover() {
        return idPasswordRecover;
    }

    public void setIdPasswordRecover(String idPasswordRecover) {
        this.idPasswordRecover = idPasswordRecover;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPasswordRecover != null ? idPasswordRecover.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PasswordRecover)) {
            return false;
        }
        PasswordRecover other = (PasswordRecover) object;
        if ((this.idPasswordRecover == null && other.idPasswordRecover != null) || (this.idPasswordRecover != null && !this.idPasswordRecover.equals(other.idPasswordRecover))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguridad.control.entities.PasswordRecover[ idPasswordRecover=" + idPasswordRecover + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return 1;
    }

}

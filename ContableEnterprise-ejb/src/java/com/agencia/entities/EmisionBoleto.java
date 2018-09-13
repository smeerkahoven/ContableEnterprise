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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_emision_boleto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmisionBoleto.findAll", query = "SELECT e FROM EmisionBoleto e ORDER BY e.orden ASC")})
public class EmisionBoleto extends Entidad {
    
    public static final String NORMAL = "NORM";
    public static final String CONEXION = "CNX";
    public static final String CANJE = "CHG";
    public static final String VIRTUAL = "MDP";
    public static final String DEBITO = "ADM";
    public static final String CREDITO = "ACM";
    public static final String EMISION_TICKET = "TKT";
    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_emision")
    private String idEmision;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "orden")
    private Integer orden ;

    public EmisionBoleto() {
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    
    public EmisionBoleto(String idEmision) {
        this.idEmision = idEmision;
    }

    public String getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(String idEmision) {
        this.idEmision = idEmision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmision != null ? idEmision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmisionBoleto)) {
            return false;
        }
        EmisionBoleto other = (EmisionBoleto) object;
        if ((this.idEmision == null && other.idEmision != null) || (this.idEmision != null && !this.idEmision.equals(other.idEmision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.EmisionBoleto[ idEmision=" + idEmision + " ]";
    }

    @Override
    public int getId() throws CRUDException {
            return 0 ;
    }
    
}

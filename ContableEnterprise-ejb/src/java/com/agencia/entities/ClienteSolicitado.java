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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_cliente_solicitado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteSolicitado.findAll", query = "SELECT c FROM ClienteSolicitado c"),
    @NamedQuery(name = "ClienteSolicitado.getAllFromClient", query = "SELECT c FROM ClienteSolicitado c WHERE c.idCliente=:idCliente")
})
public class ClienteSolicitado extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_cliente_solicitado")
    private Integer idClienteSolicitado;
    @Size(max = 64)
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_cliente")
    private Integer idCliente;

    public ClienteSolicitado() {
    }

    public ClienteSolicitado(Integer idClienteSolicitado) {
        this.idClienteSolicitado = idClienteSolicitado;
    }

    public Integer getIdClienteSolicitado() {
        return idClienteSolicitado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdClienteSolicitado(Integer idClienteSolicitado) {
        this.idClienteSolicitado = idClienteSolicitado;
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
        hash += (idClienteSolicitado != null ? idClienteSolicitado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteSolicitado)) {
            return false;
        }
        ClienteSolicitado other = (ClienteSolicitado) object;
        if ((this.idClienteSolicitado == null && other.idClienteSolicitado != null) || (this.idClienteSolicitado != null && !this.idClienteSolicitado.equals(other.idClienteSolicitado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.ClienteSolicitado[ idClienteSolicitado=" + idClienteSolicitado + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idClienteSolicitado;
    }

}

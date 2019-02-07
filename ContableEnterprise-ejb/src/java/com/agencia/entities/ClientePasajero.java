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
@Table(name = "cnt_cliente_pasajero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientePasajero.findAll", query = "SELECT c FROM ClientePasajero c"),
    @NamedQuery(name = "ClientePasajero.findPasajero", query = "SELECT c FROM ClientePasajero c WHERE UPPER(c.nombrePasajero)=UPPER(:nombrePasajero) and c.idCliente=:idCliente"),
    @NamedQuery (name ="ClientePasajero.find", query ="SELECT c.nombrePasajero FROM ClientePasajero c WHERE c.idCliente =:idCliente" )
})
public class ClientePasajero extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_cliente_pasajero")
    private Integer idClientePasajero;
    @Size(max = 64)
    @Column(name = "nombre_pasajero")
    private String nombrePasajero;
    @Column (name="id_cliente")
    private Integer idCliente ;

    public ClientePasajero() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    
    
    public ClientePasajero(Integer idClientePasajero) {
        this.idClientePasajero = idClientePasajero;
    }

    public Integer getIdClientePasajero() {
        return idClientePasajero;
    }

    public void setIdClientePasajero(Integer idClientePasajero) {
        this.idClientePasajero = idClientePasajero;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClientePasajero != null ? idClientePasajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientePasajero)) {
            return false;
        }
        ClientePasajero other = (ClientePasajero) object;
        if ((this.idClientePasajero == null && other.idClientePasajero != null) || (this.idClientePasajero != null && !this.idClientePasajero.equals(other.idClientePasajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.ClientePasajero[ idClientePasajero=" + idClientePasajero + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return idClientePasajero ;
    }
    
}

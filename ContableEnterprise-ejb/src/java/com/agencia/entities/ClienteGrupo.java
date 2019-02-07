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
@Table(name = "cnt_cliente_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteGrupo.findAll", query = "SELECT c FROM ClienteGrupo c ORDER by c.nombre")})
public class ClienteGrupo extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_cliente_grupo")
    private Integer idClienteGrupo;
    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;


    public ClienteGrupo() {
    }

    public ClienteGrupo(Integer idClienteGrupo) {
        this.idClienteGrupo = idClienteGrupo;
    }

    public ClienteGrupo(Integer idClienteGrupo, String nombre) {
        this.idClienteGrupo = idClienteGrupo;
        this.nombre = nombre;
    }

    public Integer getIdClienteGrupo() {
        return idClienteGrupo;
    }

    public void setIdClienteGrupo(Integer idClienteGrupo) {
        this.idClienteGrupo = idClienteGrupo;
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
        hash += (idClienteGrupo != null ? idClienteGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteGrupo)) {
            return false;
        }
        ClienteGrupo other = (ClienteGrupo) object;
        if ((this.idClienteGrupo == null && other.idClienteGrupo != null) || (this.idClienteGrupo != null && !this.idClienteGrupo.equals(other.idClienteGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.ClienteGrupo[ idClienteGrupo=" + idClienteGrupo + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdClienteGrupo() ;
    }
    
    
    
}

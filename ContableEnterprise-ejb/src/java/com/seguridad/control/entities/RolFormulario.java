/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.entities;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cheyo
 */
@Entity
@Table(name = "tb_rol_formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolFormulario.findAll", query = "SELECT r FROM RolFormulario r"),
    @NamedQuery(name = "RolFormulario.remove", query = "DELETE FROM RolFormulario r WHERE r.idRol=:idRol")
})
public class RolFormulario extends Entidad {

    private static final long serialVersionUID = 1L;
    @Column(name = "crear")
    private Short crear;
    @Column(name = "actualizar")
    private Short actualizar;
    @Column(name = "eliminar")
    private Short eliminar;
    @Column(name = "acceder")
    private Short acceder;
    @Column(name = "buscar")
    private Short buscar;
    @Column(name = "ver")
    private Short ver;
    @Column(name = "editar")
    private Short editar;
    @Column(name = "anular")
    private Short anular;
    @Column(name = "ejecutar")
    private Short ejecutar;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_formulario")
    private Integer idRolFormulario;
    
    @JoinColumn(name = "id_formularios", referencedColumnName = "id_formulario", updatable = false)
    @ManyToOne
    private Formulario idFormularios;
    
    @JoinColumn(  name = "id_rol", referencedColumnName = "id_rol", updatable = false)
    @ManyToOne
    private Rol idRol;

    public RolFormulario() {
    }

    public RolFormulario(Integer idRolFormulario) {
        this.idRolFormulario = idRolFormulario;
    }

    public Short getCrear() {
        return crear;
    }

    public void setCrear(Short crear) {
        this.crear = crear;
    }

    public Short getActualizar() {
        return actualizar;
    }

    public void setActualizar(Short actualizar) {
        this.actualizar = actualizar;
    }

    public Short getEliminar() {
        return eliminar;
    }

    public void setEliminar(Short eliminar) {
        this.eliminar = eliminar;
    }

    public Short getAcceder() {
        return acceder;
    }

    public void setAcceder(Short acceder) {
        this.acceder = acceder;
    }

    public Short getBuscar() {
        return buscar;
    }

    public void setBuscar(Short buscar) {
        this.buscar = buscar;
    }

    public Integer getIdRolFormulario() {
        return idRolFormulario;
    }

    public void setIdRolFormulario(Integer idRolFormulario) {
        this.idRolFormulario = idRolFormulario;
    }

    public Formulario getIdFormularios() {
        return idFormularios;
    }

    public void setIdFormularios(Formulario idFormularios) {
        this.idFormularios = idFormularios;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Short getVer() {
        return ver;
    }

    public void setVer(Short ver) {
        this.ver = ver;
    }

    public Short getEditar() {
        return editar;
    }

    public void setEditar(Short editar) {
        this.editar = editar;
    }

    public Short getAnular() {
        return anular;
    }

    public void setAnular(Short anular) {
        this.anular = anular;
    }

    public Short getEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(Short ejecutar) {
        this.ejecutar = ejecutar;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolFormulario != null ? idRolFormulario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolFormulario)) {
            return false;
        }
        RolFormulario other = (RolFormulario) object;
        if ((this.idRolFormulario == null && other.idRolFormulario != null) || (this.idRolFormulario != null && !this.idRolFormulario.equals(other.idRolFormulario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguridad.control.entities.RolFormulario[ idRolFormulario=" + idRolFormulario + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdRolFormulario();
    }
    
    
    
}

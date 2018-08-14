/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cheyo
 */
@Entity
@Table(name = "tb_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findUserName", query = "SELECT u FROM User u WHERE u.userName=:user_name"),
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.activate" , query = "UPDATE User u SET u.status='ACTIVO'  WHERE u.userName=:p"),
    @NamedQuery(name = "User.deactivate" , query = "UPDATE User u SET u.status='INACTIVO'  WHERE u.userName=:p"),
        @NamedQuery(name = "User.updatePassword" , query = "UPDATE User u SET u.password=:password  WHERE u.userName=:userName")
})
public class User implements Serializable {

    @Id
    @Column(name = "user_name", length = 16)
    private String userName;

    @Column(name = "password", length = 45)
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "fecha_alta", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne
    private Rol idRol;

    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @OneToOne(optional = false)
    private Empleado idEmpleado;

    @Transient
    private String ip;
    
    @Transient
    private String token_url ;

    public User() {
    }

    public User (String username){
        this.userName = username ;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getToken_url() {
        return token_url;
    }

    public void setToken_url(String token_url) {
        this.token_url = token_url;
    }

    
    @Override
    public String toString() {
        return "com.seguridad.control.entities.User[ userName=" + userName + " ]";
    }
}

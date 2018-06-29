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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cheyo
 */
@Entity
@Table(name = "tb_user_login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserLogin.findAll", query = "SELECT u FROM UserLogin u")
    , @NamedQuery(name = "UserLogin.findByIdUserLogin", query = "SELECT u FROM UserLogin u WHERE u.idUserLogin = :idUserLogin")
    , @NamedQuery(name = "UserLogin.findByFechaLogin", query = "SELECT u FROM UserLogin u WHERE u.fechaLogin = :fechaLogin")
    , @NamedQuery(name = "UserLogin.findByFechaLogout", query = "SELECT u FROM UserLogin u WHERE u.fechaLogout = :fechaLogout")
    , @NamedQuery(name = "UserLogin.findByIp", query = "SELECT u FROM UserLogin u WHERE u.ip = :ip")
    , @NamedQuery(name = "UserLogin.findByNroIntento", query = "SELECT u FROM UserLogin u WHERE u.nroIntento = :nroIntento")
    , @NamedQuery(name = "UserLogin.findByUserName", query = "SELECT u FROM UserLogin u WHERE u.userName = :userName")})
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_login")
    private Integer idUserLogin;
    
    @Column(name = "fecha_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLogin;
    
    @Column(name = "fecha_logout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLogout;
    
    @Column(name = "ip", length = 16)
    private String ip;
    
    @Column(name = "nro_intento")
    private Integer nroIntento;
    
    @Column(name = "user_name", length = 16)
    private String userName;

    public UserLogin() {
    }

    public UserLogin(Integer idUserLogin) {
        this.idUserLogin = idUserLogin;
    }

    public UserLogin(Integer idUserLogin, Date fechaLogin, String userName) {
        this.idUserLogin = idUserLogin;
        this.fechaLogin = fechaLogin;
        this.userName = userName;
    }

    public Integer getIdUserLogin() {
        return idUserLogin;
    }

    public void setIdUserLogin(Integer idUserLogin) {
        this.idUserLogin = idUserLogin;
    }

    public Date getFechaLogin() {
        return fechaLogin;
    }

    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }

    public Date getFechaLogout() {
        return fechaLogout;
    }

    public void setFechaLogout(Date fechaLogout) {
        this.fechaLogout = fechaLogout;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getNroIntento() {
        return nroIntento;
    }

    public void setNroIntento(Integer nroIntento) {
        this.nroIntento = nroIntento;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserLogin != null ? idUserLogin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLogin)) {
            return false;
        }
        UserLogin other = (UserLogin) object;
        if ((this.idUserLogin == null && other.idUserLogin != null) || (this.idUserLogin != null && !this.idUserLogin.equals(other.idUserLogin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.seguridad.control.entities.UserLogin[ idUserLogin=" + idUserLogin + " ]";
    }
    
}

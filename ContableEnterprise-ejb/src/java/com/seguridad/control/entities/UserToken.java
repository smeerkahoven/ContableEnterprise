/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cheyo
 */
@Entity
@Table(name = "tb_user_token")
@XmlRootElement
public class UserToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_token")
    private String idToken;

    @Basic(optional = false)
    @Column(name = "fecha_token", length = 45)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaToken;

    @Column(name = "status")
    private String status;

    @Column(name = "user_name")
    private String userName;

    public UserToken() {
    }

    public UserToken(String idToken) {
        this.idToken = idToken;
    }

    public UserToken(String idToken, Date fechaToken, String status) {
        this.idToken = idToken;
        this.fechaToken = fechaToken;
        this.status = status;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public Date getFechaToken() {
        return fechaToken;
    }

    public void setFechaToken(Date fechaToken) {
        this.fechaToken = fechaToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "com.seguridad.control.entities.UserToken[ idToken=" + idToken + " ]";
    }

}

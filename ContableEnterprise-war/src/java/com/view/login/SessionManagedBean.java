/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.login;

import com.security.SessionUtils;
import com.seguridad.control.entities.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "sessionView")
@RequestScoped
public class SessionManagedBean implements Serializable {

    private String token;

    /**
     * Creates a new instance of SessionManagedBean
     */
    public SessionManagedBean() {
    }

    public String getToken() {
        User u = (User) SessionUtils.getSession(SessionUtils.SESION_USUARIO);
        if (u != null) {
            this.token = u.getToken_url();
            return token;
        }

        return "";
    }

    public void setToken(String token) {
        this.token = token;
    }

}

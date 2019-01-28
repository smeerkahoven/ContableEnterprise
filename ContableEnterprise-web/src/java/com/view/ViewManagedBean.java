/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.configuracion.remote.ParametrosRemote;
import com.security.SessionUtils;
import com.seguridad.control.entities.User;
import com.seguridad.control.remote.LoggerRemote;
import com.view.menu.Formulario;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author xeio
 */
public abstract class ViewManagedBean {

    protected String formName;
    @EJB
    protected ParametrosRemote ejbParametros;

    @EJB
    protected LoggerRemote ejbLogger;

    protected Formulario formulario;

    protected User user;

    public ViewManagedBean() {
        user = (User) SessionUtils.getSession(SessionUtils.SESION_USUARIO);
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    @PostConstruct
    public void init() {
        this.formulario = SessionUtils.getFormulario(this.formName);

    }

    public void checkIfCanAccess() {
        //System.out.println("ACCEDER View Managed Bean : " + this.formName + " Valor : " + this.formulario.getAcceder());
        /*if (this.formulario.getAcceder() == formulario.DENEGAR) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(origRequest.getContextPath() + "/pages/index/index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(BoletosManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }

}

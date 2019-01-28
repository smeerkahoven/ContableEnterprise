/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.administracion;

import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "rol")
@RequestScoped
public class RolManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of RolManagedBean
     */
    public RolManagedBean() {
        this.formName = "roles";
    }

    @PostConstruct
    public void init() {
        //try {
            this.formulario = SessionUtils.getFormulario(Formulario.ROLES);
            checkIfCanAccess();

            //ejbLogger.add(Accion.ACCESS, user.getUserName(), this.formName, user.getIp());
        //} catch (CRUDException ex) {
        //    Logger.getLogger(PersonalManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

}

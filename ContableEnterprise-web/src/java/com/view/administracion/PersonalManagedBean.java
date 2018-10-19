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
import javax.enterprise.context.Dependent;

/**
 *
 * @author xeio
 */
@Named(value = "personal")
@Dependent
public class PersonalManagedBean extends ViewManagedBean {
    
    static final long serialVersionUID = 42L;

    private String errorMessage;

    private String successMessage;

    private String warningMessage;

    /**
     * Creates a new instance of Personal
     */
    public PersonalManagedBean() {
        this.formName = "personal";
    }
    
     @PostConstruct
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(Formulario.PERSONAL);
            checkIfCanAccess();
            
            ejbLogger.add(Accion.ACCESS, user.getUserName(), this.formName, user.getIp());
        } catch (CRUDException ex) {
            Logger.getLogger(PersonalManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

}

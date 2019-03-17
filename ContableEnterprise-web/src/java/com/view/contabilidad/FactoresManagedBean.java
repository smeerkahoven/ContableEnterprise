/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.contabilidad;

import com.security.SessionUtils;
import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "factores")
@RequestScoped
public class FactoresManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of FactoresManagedBean
     */
    public FactoresManagedBean() {
        this.formName = "factores" ;
    }
    
    
        @PostConstruct
    public void init() {
//        try {
            this.formulario = SessionUtils.getFormulario(Formulario.FACTORES);
            checkIfCanAccess();

            //ejbLogger.add(Accion.ACCESS, user.getUserName(), this.formName, user.getIp());
//        } catch (CRUDException ex) {
//            Logger.getLogger(PersonalManagedBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}

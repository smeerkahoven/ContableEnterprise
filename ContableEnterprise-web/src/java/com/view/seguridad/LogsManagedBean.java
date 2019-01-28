/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.seguridad;

import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author xeio
 */
@Named(value = "logs")
@Dependent
public class LogsManagedBean extends ViewManagedBean{

    /**
     * Creates a new instance of LogsManagedBean
     */
    public LogsManagedBean() {
        this.formName = Formulario.LOGS ;
    }
    
}

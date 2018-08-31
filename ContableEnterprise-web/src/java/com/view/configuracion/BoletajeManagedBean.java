/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.configuracion;

import com.view.ViewManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "confBoletaje")
@RequestScoped
public class BoletajeManagedBean extends   ViewManagedBean{

    /**
     * Creates a new instance of BoletajeManagedBean
     */
    public BoletajeManagedBean() {
        this.formName = "configuracion-boletaje";
    }
    
}

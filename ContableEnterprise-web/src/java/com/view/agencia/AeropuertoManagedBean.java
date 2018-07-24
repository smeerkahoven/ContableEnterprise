/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.agencia;

import com.view.ViewManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "aeropuerto")
@RequestScoped
public class AeropuertoManagedBean extends ViewManagedBean{

    static final long serialVersionUID = 42L;

    /**
     * Creates a new instance of AeropuertoManagedBean
     */
    public AeropuertoManagedBean() {
        this.formName = "aeropuerto" ;
    }

}

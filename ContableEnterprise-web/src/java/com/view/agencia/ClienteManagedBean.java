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
@Named(value = "cliente")
@RequestScoped
public class ClienteManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of ClienteManagedBean
     */
    public ClienteManagedBean() {
        this.formName = "clientes";
    }
    
}

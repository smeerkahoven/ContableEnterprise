/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.agencia;

import com.view.ViewManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author xeio
 */
@Named(value = "boletos")
@RequestScoped
public class BoletosManagedBean extends ViewManagedBean {

    public BoletosManagedBean() {
        this.formName = "boletos" ;
    }
 
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.agencia;

import com.security.SessionUtils;
import com.view.ViewManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "promotores")
@RequestScoped
public class PromotoresManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of PromotoresManagedBean
     */
    public PromotoresManagedBean() {
        this.formName = "promotores" ;
    }
    
}

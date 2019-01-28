/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.contabilidad;

import com.view.ViewManagedBean;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "notasCredito")
@RequestScoped
public class NotasCreditoManagedBean extends ViewManagedBean{

    /**
     * Creates a new instance of NotasCreditoManagedBean
     */
    public NotasCreditoManagedBean() {
        this.formName = formulario.NOTA_CREDITO;
    }
    
}

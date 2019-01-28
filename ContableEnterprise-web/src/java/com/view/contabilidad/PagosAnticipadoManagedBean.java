/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.contabilidad;

import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "pagoAnticipado")
@RequestScoped
public class PagosAnticipadoManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of PagosAnticipadosManagedBean
     */
    public PagosAnticipadoManagedBean() {
        this.formName = Formulario.PAGOS_ANTICIPADOS;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.reports;

import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author xeio
 */
@Named(value = "reporteComisionCliente")
@Dependent
public class ComisionClienteManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of VentasBoletosManagedBean
     */
    public ComisionClienteManagedBean() {
        this.formName = Formulario.REPORTES_COMISION_CLIENTE;
    }
    
}

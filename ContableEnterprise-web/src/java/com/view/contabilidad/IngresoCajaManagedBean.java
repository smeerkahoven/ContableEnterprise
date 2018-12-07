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
import javax.enterprise.context.Dependent;

/**
 *
 * @author xeio
 */
@Named(value = "ingresoCaja")
@Dependent
public class IngresoCajaManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of IngresoCajaManagedBean
     */
    public IngresoCajaManagedBean() {
        this.formName = "ingreso-caja";
    }

    @PostConstruct
    public void init() {
        this.formulario = SessionUtils.getFormulario(Formulario.INGRESO_CAJA);
    }
}

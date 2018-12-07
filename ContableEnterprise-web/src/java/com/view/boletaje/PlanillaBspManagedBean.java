/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.boletaje;

import com.security.SessionUtils;
import com.view.ViewManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "planillaBsp")
@RequestScoped
public class PlanillaBspManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of BoletoOtrosCargosManagedBean
     */
    public PlanillaBspManagedBean() {
        this.formName = "planilla-bsp";
    }

    @PostConstruct
    public void init() {
        this.formulario = SessionUtils.getFormulario(this.formName);

    }

}

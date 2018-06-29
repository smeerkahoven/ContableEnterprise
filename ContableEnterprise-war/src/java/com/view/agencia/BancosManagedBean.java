/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.agencia;

import com.security.SessionUtils;
import com.view.menu.Formulario;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "bancos")
@RequestScoped
public class BancosManagedBean {

    private Formulario formulario;

    /**
     * Creates a new instance of BancosManagedBean
     */
    public BancosManagedBean() {
    }

    @PostConstruct
    public void init() {
        this.formulario = SessionUtils.getFormulario(Formulario.BANCOS);
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

}

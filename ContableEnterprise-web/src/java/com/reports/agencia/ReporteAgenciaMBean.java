/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports.agencia;

import com.security.SessionUtils;
import com.view.menu.Formulario;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "reporteAgencia")
@RequestScoped
public class ReporteAgenciaMBean {
    
    private Formulario formulario;

    /**
     * Creates a new instance of ReporteAgenciaMBean
     */
    public ReporteAgenciaMBean() {
    }
    
     @PostConstruct
    public void init() {
            this.formulario = SessionUtils.getFormulario(Formulario.REPORTES_AGENCIA);
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
    
    
    
}

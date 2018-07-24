/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.configuracion.remote.ParametrosRemote;
import com.security.SessionUtils;
import com.view.menu.Formulario;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author xeio
 */
public abstract class ViewManagedBean  {

    protected String formName  ;
    @EJB
    protected ParametrosRemote ejbParametros;
    
    protected Formulario formulario;

    public ViewManagedBean(){
    }
    
    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
    
    @PostConstruct
    public void init(){
        this.formulario = SessionUtils.getFormulario(this.formName);
    }

}

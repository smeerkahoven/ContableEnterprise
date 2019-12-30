/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.configuracion;

import com.security.SessionUtils;
import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author xeio
 */
@Named(value = "gestiones")
@Dependent
public class GestionesManagedBean extends ViewManagedBean {

    private Integer idEmpresa;

    /**
     * Creates a new instance of GestionesManagedBean
     */
    public GestionesManagedBean() {
        this.formName = Formulario.GESTIONES;
        this.idEmpresa = user.getIdEmpleado().getIdEmpresa().getIdEmpresa() ;
    }

    @PostConstruct
    public void init() {
        this.formulario = SessionUtils.getFormulario(Formulario.GESTIONES);
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }
    
    
}

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
@Named(value = "mayores")
@RequestScoped
public class MayoresManagedBean extends ViewManagedBean {

    private Integer idEmpresa ;
    /**
     * Creates a new instance of PagosAnticipadosManagedBean
     */
    public MayoresManagedBean() {
        this.formName = Formulario.MAYORES;
        this.idEmpresa = user.getIdEmpleado().getIdEmpresa().getIdEmpresa() ;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    
}

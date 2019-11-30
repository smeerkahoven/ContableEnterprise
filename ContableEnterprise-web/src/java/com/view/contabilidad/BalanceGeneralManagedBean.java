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
@Named(value = "balanceGeneral")
@RequestScoped
public class BalanceGeneralManagedBean extends ViewManagedBean{

    private Integer idEmpresa ;
    /**
     * Creates a new instance of BalanceGeneral
     */
   public BalanceGeneralManagedBean() {
        this.formName = Formulario.BALANCE_GENERAL;
        this.idEmpresa = user.getIdEmpleado().getIdEmpresa().getIdEmpresa() ;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }
    
   
}

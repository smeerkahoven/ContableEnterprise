/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.cobranzas;

import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "historicoCliente")
@RequestScoped
public class HistoricoClienteManagedBean extends ViewManagedBean {

    /**
     * Creates a new instance of KardexClienteManagedBean
     */
    public HistoricoClienteManagedBean() {
        this.formName = Formulario.HISTORICO_CLIENTE;
    }
    
}

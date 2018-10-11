/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.agencia;

import com.configuracion.entities.Parametros;
import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.view.ViewManagedBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author xeio
 */
@Named(value = "boletos")
@RequestScoped
public class BoletosManagedBean extends ViewManagedBean {

    private Parametros porcentaje ;
    public BoletosManagedBean() {
        this.formName = "boletos";
    }

    @PostConstruct
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(this.formName);
            this.porcentaje = (Parametros) ejbParametros.get(new Parametros(Parametros.PORCENTAJE_COMISION));
            
            ejbLogger.add(Accion.ACCESS, user.getUserName(), this.formName, user.getIp());
        } catch (CRUDException ex) {
            Logger.getLogger(BoletosManagedBean.class.getName()).log(Level.SEVERE.SEVERE, null, ex);
        }
    }

    public Parametros getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Parametros porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    

}

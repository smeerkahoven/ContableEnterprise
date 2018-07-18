/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.agencia;

import com.configuracion.entities.Parametros;
import com.configuracion.remote.ParametrosRemote;
import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
import com.view.menu.Formulario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "aerolinea")
@RequestScoped
public class AerolineaManagedBean {
    @EJB
    private ParametrosRemote ejbParametros;

    private Parametros comboVentas , comboComisiones, comboDevoluciones ; 
    
    private Formulario formulario;

    /**
     * Creates a new instance of AerolineaManagedBean
     */
    public AerolineaManagedBean() {
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
    
     @PostConstruct
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(Formulario.AEROLINEA);
            this.comboVentas = (Parametros)ejbParametros.get(new Parametros(Parametros.COMBO_CUENTA_VENTAS_AEROLINEA));
            this.comboComisiones = (Parametros)ejbParametros.get(new Parametros(Parametros.COMBO_CUENTA_COMISIONES_AEROLINEA));
            this.comboDevoluciones = (Parametros)ejbParametros.get(new Parametros(Parametros.COMBO_CUENTA_DEVOLUCIONES_AEROLINEA));
            
        } catch (CRUDException ex) {
            Logger.getLogger(AerolineaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Parametros getComboVentas() {
        return comboVentas;
    }

    public void setComboVentas(Parametros comboVentas) {
        this.comboVentas = comboVentas;
    }

    public Parametros getComboComisiones() {
        return comboComisiones;
    }

    public void setComboComisiones(Parametros comboComisiones) {
        this.comboComisiones = comboComisiones;
    }

    public Parametros getComboDevoluciones() {
        return comboDevoluciones;
    }

    public void setComboDevoluciones(Parametros comboDevoluciones) {
        this.comboDevoluciones = comboDevoluciones;
    }

    
    

}

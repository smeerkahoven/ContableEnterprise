/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.contabilidad;

import com.configuracion.entities.Parametros;
import com.contabilidad.entities.Bolivianos;
import com.contabilidad.entities.Dolar;
import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
import com.view.ViewManagedBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author xeio
 */
@Named(value = "comprobantes")
@RequestScoped
public class ComprobantesManagedBean extends ViewManagedBean {

    private Parametros factorCambiarioMaxMin;

    public Parametros getFactorCambiarioMaxMin() {
        return factorCambiarioMaxMin;
    }

    public void setFactorCambiarioMaxMin(Parametros factorCambiarioMaxMin) {
        this.factorCambiarioMaxMin = factorCambiarioMaxMin;
    }

    private Bolivianos bolivianos = new Bolivianos();
    private Dolar dolar = new Dolar();

    public Bolivianos getBolivianos() {
        return bolivianos;
    }

    public void setBolivianos(Bolivianos bolivianos) {
        this.bolivianos = bolivianos;
    }

    public Dolar getDolar() {
        return dolar;
    }

    public void setDolar(Dolar dolar) {
        this.dolar = dolar;
    }

    /**
     * Creates a new instance of ComprobantesManagedBean
     */
    public ComprobantesManagedBean() {
        this.formName = "comprobantes";
    }

    @PostConstruct
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(this.formName);
            this.factorCambiarioMaxMin = (Parametros) ejbParametros.get(new Parametros(Parametros.FACTOR_CAMBIARO_MAX_MIN));
        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

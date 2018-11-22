/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.boletaje;

import com.configuracion.entities.Parametros;
import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
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
@Named(value = "boletajeOtros")
@RequestScoped
public class BoletoOtrosCargosManagedBean extends ViewManagedBean {

    private Parametros comisionPromotor, comisionAgencia, pasivoMayorista;

    /**
     * Creates a new instance of BoletoOtrosCargosManagedBean
     */
    public BoletoOtrosCargosManagedBean() {
        this.formName = "boletaje-otros";
    }

    @PostConstruct
    public void init() {
        this.formulario = SessionUtils.getFormulario(this.formName);

        try {
            this.comisionPromotor = (Parametros) ejbParametros.get(new Parametros(Parametros.COMBO_CUENTA_COMISION_PROMOTOR));
            this.comisionAgencia = (Parametros) ejbParametros.get(new Parametros(Parametros.COMBO_CUENTA_COMISION_AGENCIA));
            this.pasivoMayorista = (Parametros) ejbParametros.get(new Parametros(Parametros.COMBO_CUENTA_PASIVO_MAYORISTAS));
            
            ejbLogger.add(Accion.ACCESS, user.getUserName(), this.formName, user.getIp());
        } catch (CRUDException ex) {
            Logger.getLogger(BoletoOtrosCargosManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Parametros getComisionPromotor() {
        return comisionPromotor;
    }

    public void setComisionPromotor(Parametros comisionPromotor) {
        this.comisionPromotor = comisionPromotor;
    }


    public Parametros getComisionAgencia() {
        return comisionAgencia;
    }

    public void setComisionAgencia(Parametros comisionAgencia) {
        this.comisionAgencia = comisionAgencia;
    }

    public Parametros getPasivoMayorista() {
        return pasivoMayorista;
    }

    public void setPasivoMayorista(Parametros pasivoMayorista) {
        this.pasivoMayorista = pasivoMayorista;
    }
    
    

}

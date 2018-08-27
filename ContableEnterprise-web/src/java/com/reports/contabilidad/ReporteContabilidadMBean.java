/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports.contabilidad;

import com.reports.ReporteHandler;
import com.reports.agencia.ReporteAgenciaMBean;
import com.security.SessionUtils;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.exception.CRUDException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author xeio
 */
@Named(value = "reporteContabilidad")
@RequestScoped
public class ReporteContabilidadMBean extends ReporteHandler {

    /**
     * Creates a new instance of ReporteContabilidadMBean
     */
    public ReporteContabilidadMBean() {
        this.formName = "reportes-contabilidad";
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(com.view.menu.Formulario.REPORTES_CONTABILIDAD);
            this.listaReportes = ejbReporte.get(new Formulario(this.formulario.getIdFormulario()));
        } catch (CRUDException ex) {
            Logger.getLogger(ReporteAgenciaMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verComprobante() {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
            String trxNo = facesContext.getExternalContext(). getRequestParameterMap().get("idLibro");
        System.err.println("idLibro" + trxNo);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("ID_LIBRO", trxNo);
        verReporte("contabilidad/comprobante.jasper", parameters);
    }

}

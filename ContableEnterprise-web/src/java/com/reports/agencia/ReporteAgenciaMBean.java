/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports.agencia;

import com.reports.ReportViewer;
import com.reportes.entities.Reportes;
import com.reportes.remote.ReportAgenciaRemote;
import com.reports.ReporteHandler;
import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

/**
 *
 * @author xeio
 */
@Named(value = "reporteAgencia")
@RequestScoped
public class ReporteAgenciaMBean extends ReporteHandler {

    /**
     * Creates a new instance of ReporteAgenciaMBean
     */
    public ReporteAgenciaMBean() {
        this.formName = "reportes-agencia";
    }

    @PostConstruct
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(this.formName);
            this.listaReportes = ejbReporte.get(new com.seguridad.control.entities.Formulario(this.formulario.getIdFormulario()));
        } catch (CRUDException ex) {
            Logger.getLogger(ReporteAgenciaMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**/
}

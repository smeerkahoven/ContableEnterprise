/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports;

import com.reportes.entities.Reportes;
import com.reportes.remote.ReportAgenciaRemote;
import com.reports.agencia.ReporteAgenciaMBean;
import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

/**
 *
 * @author xeio
 */
public abstract class ReporteHandler extends ViewManagedBean {

    @EJB
    protected ReportAgenciaRemote ejbReporte;

    @Resource(mappedName = "jdbc/db_travel")
    protected DataSource datasource;

    protected List<Reportes> listaReportes = new LinkedList<>();

    protected String idFormulario;

    public ReporteHandler() {

    }

    public ReporteHandler(String idFormulario) {
        this.idFormulario = idFormulario;
    }

    /**
     *
     * @param reportPath
     */
    public void verReporte(String reportPath) {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String path = facesContext.getExternalContext().getRealPath("/resources/cabecera.jasper");

            Connection conn = datasource.getConnection();

            ReportViewer r = new ReportViewer();
            r.mostrarReport(reportPath, path, conn, facesContext);

            facesContext.getResponseComplete();

        } catch (SQLException ex) {
            Logger.getLogger(ReporteAgenciaMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     *
     * @param reportPath
     * @param parameters
     */
    public void verReporte(String reportPath, HashMap parameters) {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String path = facesContext.getExternalContext().getRealPath("/resources/cabecera.jasper");

            Connection conn = datasource.getConnection();

            ReportViewer r = new ReportViewer();
            r.mostrarReport(reportPath, path, conn, facesContext, parameters);

            facesContext.getResponseComplete();

        } catch (SQLException ex) {
            Logger.getLogger(ReporteAgenciaMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   

    public String getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(String idFormulario) {
        this.idFormulario = idFormulario;
    }

    public List<Reportes> getListaReportes() {
        return listaReportes;
    }

    public void setListaReportes(List<Reportes> listaReportes) {
        this.listaReportes = listaReportes;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

}

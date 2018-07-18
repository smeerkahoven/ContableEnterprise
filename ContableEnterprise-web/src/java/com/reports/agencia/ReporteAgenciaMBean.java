/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports.agencia;

import com.reportes.entities.Reportes;
import com.reportes.remote.ReportAgenciaRemote;
import com.security.SessionUtils;
import com.seguridad.control.exception.CRUDException;
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
public class ReporteAgenciaMBean {

    @EJB
    private ReportAgenciaRemote ejbReporte;

    @Resource(mappedName = "jdbc/db_travel")
    private DataSource datasource;

    private List<Reportes> listaReportes = new LinkedList<>();

    private Formulario formulario;

    /**
     * Creates a new instance of ReporteAgenciaMBean
     */
    public ReporteAgenciaMBean() {
    }

    @PostConstruct
    public void init() {
        try {
            this.formulario = SessionUtils.getFormulario(Formulario.REPORTES_AGENCIA);
            this.listaReportes = ejbReporte.get(new com.seguridad.control.entities.Formulario(this.formulario.getIdFormulario()));
        } catch (CRUDException ex) {
            Logger.getLogger(ReporteAgenciaMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verReporteAerolinea(String reportPath) {

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

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public List<Reportes> getListaReportes() {
        return listaReportes;
    }

    public void setListaReportes(LinkedList<Reportes> listaReportes) {
        this.listaReportes = listaReportes;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports.servlet;

import com.configuracion.entities.Parametros;
import com.configuracion.remote.ParametrosRemote;
import com.reportes.entities.Reportes;
import com.reportes.remote.ReportAgenciaRemote;
import com.reports.ReportViewer;
import com.reports.agencia.ReporteAgenciaMBean;
import com.seguridad.control.exception.CRUDException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author xeio
 */
@WebServlet(name = "NotaDebitoReportServlet", urlPatterns = {"/NotaDebitoReportServlet"})
public class NotaDebitoReportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    protected ReportAgenciaRemote ejbReporte;

    @Resource(mappedName = "jdbc/db_travel")
    protected DataSource datasource;

    protected List<Reportes> listaReportes = new LinkedList<>();

    protected String idFormulario;

    @EJB
    protected ParametrosRemote ejbParametros;
    private Parametros printPaper;

    /**
     *
     * @param reportPath
     */
    public void verReporte(String reportPath) {

        try {

            try {
                this.printPaper = (Parametros) ejbParametros.get(new Parametros(Parametros.PRINT_PAPER));
            } catch (CRUDException ex) {
                Logger.getLogger(NotaDebitoReportServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String cabecera = "";
            String contenido = "";
            if (this.printPaper.equals(Parametros.CARTA)) {
                contenido = "_lt";
                cabecera = "_lt";
            } else if (this.printPaper.equals(Parametros.CONTINUO)) {
                cabecera = "_240";
            }
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
            Connection conn = datasource.getConnection();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            String path = facesContext.getExternalContext().getRealPath("/resources/cabecera.jasper");

            ReportViewer r = new ReportViewer();
            r.mostrarReport(reportPath, path, conn, facesContext, parameters);

            facesContext.getResponseComplete();

        } catch (SQLException ex) {
            Logger.getLogger(ReporteAgenciaMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            this.printPaper = (Parametros) ejbParametros.get(new Parametros(Parametros.PRINT_PAPER));
        } catch (CRUDException ex) {
            Logger.getLogger(NotaDebitoReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String cabecera = "";
        String contenido = "";
        if (this.printPaper.getValor().equals(Parametros.CARTA)) {
            contenido = "_lt";
            cabecera = "_lt";
        } else if (this.printPaper.getValor(). equals(Parametros.CONTINUO)) {
            cabecera = "_240";
        }

        try {
            String jrxmlFileName = "/resources/cabecera" + cabecera + ".jasper";
            String subreporte = "contabilidad/nota_debito" + contenido + ".jasper" ;
            
            File archivoReporte = new File(request.getSession().getServletContext().getRealPath(jrxmlFileName));
            Integer idNotaDebito = Integer.parseInt(request.getParameter("idNota"));
            HashMap hm = null;
            hm = new HashMap();

            ServletOutputStream servletOutputStream = response.getOutputStream();

            byte[] bytes = null;

            System.out.println(jrxmlFileName);
            System.out.println(subreporte);
            
            try {

                hm.put("ID_NOTA_DEBITO", idNotaDebito);
                hm.put("PATH_SUBREPORTE", subreporte);

                bytes = JasperRunManager.runReportToPdf(archivoReporte.getPath(), hm, datasource.getConnection());

                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (JRException e) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            } finally {
                if (datasource.getConnection() != null) {
                    datasource.getConnection().close();
                }
            }
        } catch (Exception e) {
            String salida = "Error generando Reporte Jasper, el error del Sistema es " + e;
            System.out.println(salida);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

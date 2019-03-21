/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports.servlet;

import com.reportes.entities.Reportes;
import com.reportes.remote.ReportAgenciaRemote;
import com.reports.ReportViewer;
import com.reports.agencia.ReporteAgenciaMBean;
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
@WebServlet(name = "CartaCobranzaServlet", urlPatterns = {"/CartaCobranzaServlet"})
public class CartaCobranzaReportServlet extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String jrxmlFileName = "/resources/cabecera.jasper";
            File archivoReporte = new File(request.getSession().getServletContext().getRealPath(jrxmlFileName));
            String fechaInicio = request.getParameter("pi");
            String fechaFin = request.getParameter("pf");
            Integer idCliente = Integer.parseInt( request.getParameter("pc"));
            String idUser = (request.getParameter("pu"));
            HashMap hm = null;
            hm = new HashMap();

            ServletOutputStream servletOutputStream = response.getOutputStream();

            byte[] bytes = null;

            try {

                hm.put("FECHA_INICIO", fechaInicio);
                hm.put("FECHA_FIN", fechaFin);
                hm.put("ID_CLIENTE", idCliente);
                hm.put("ID_USER", idUser);
                hm.put("PATH_SUBREPORTE", "cobranza/carta_cobranza.jasper");
                hm.put("PATH_FIRMA", "cobranza/firma_empleado.jasper");

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
            }catch (NumberFormatException e) {
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

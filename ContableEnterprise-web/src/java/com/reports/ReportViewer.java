/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author xeio
 */
public class ReportViewer {

    public void mostrarReport(String subreporte, String path, Connection conn, FacesContext facesContext) {
        try {
            ///---
            HashMap hash = new HashMap<Object, Object>();
            hash.put("EMPRESA_ID", "1");
            hash.put("PATH_SUBREPORTE", subreporte);
            File file = new File(path);

            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.addHeader("Content-type", "application/pdf");

            byte[] bytes = JasperRunManager.runReportToPdf(file.getPath(), hash, conn);

            response.setContentLength(bytes.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
            //conn.close();
            System.out.println(facesContext.getExternalContext().getResponse().getClass());

            //--
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarReport(String subreporte, String path, Connection conn, FacesContext facesContext, HashMap<String, Object> parameters) {
        try {
            ///---

            HashMap hash = new HashMap<Object, Object>();
            hash.put("EMPRESA_ID", "1");
            hash.put("PATH_SUBREPORTE", subreporte);

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                hash.put(key, value);
                // do what you have to do here
                // In your case, another loop.
            }

            File file = new File(path);

            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.addHeader("Content-type", "application/pdf");

            byte[] bytes = JasperRunManager.runReportToPdf(file.getPath(), hash, conn);

            response.setContentLength(bytes.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
            //conn.close();
            System.out.println(facesContext.getExternalContext().getResponse().getClass());

            //--
        } catch (JRException | IOException ex) {
            Logger.getLogger(ReportViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

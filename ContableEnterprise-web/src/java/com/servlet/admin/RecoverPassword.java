/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seguridad.control.entities.PasswordRecover;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpleadoRemote;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author xeio
 */
public class RecoverPassword extends HttpServlet {

    @EJB
    private EmpleadoRemote ejbEmpleado;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idPasswordRecover = request.getParameter("p_id");
        if (idPasswordRecover.isEmpty()) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.xhtml");
            dispatcher.forward(request, response);
        } else {
            try {
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("idPasswordRecover", idPasswordRecover);

                List l = (List) ejbEmpleado.get("PasswordRecover.findAt", PasswordRecover.class, parameters);
                if (!l.isEmpty()) {
                    PasswordRecover password = (PasswordRecover) l.get(0);

                    if (password != null) {

                        //if (password.getEstado() == PasswordRecover.ACTIVO) {
                            String[] split = password.getIdPasswordRecover().split("\\.");
                            String base64EncodedHeader = split[0];
                            String base64EncodedBody = split[1];
                            String base64EncodedSignature = split[2];

                            Base64 base64Url = new Base64(true);
                            String body = new String(base64Url.decode(base64EncodedBody));

                            Gson gson = new GsonBuilder().create();
                            JsonParser parser = new JsonParser();
                            JsonObject object = parser.parse(body).getAsJsonObject();
                            object.get("value");

                            request.setAttribute("username", object.get("value"));
                            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/recover.xhtml");
                            dispatcher.forward(request, response);
                       // }
                    } else {
                        //reenvia pagina de error
                        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.xhtml");
                        dispatcher.forward(request, response);
                    }
                } else {
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.xhtml");
                    dispatcher.forward(request, response);
                }
            } catch (CRUDException ex) {
                Logger.getLogger(RecoverPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
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

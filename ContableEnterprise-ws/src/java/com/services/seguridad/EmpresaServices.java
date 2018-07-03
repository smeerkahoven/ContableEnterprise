/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad;

import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpresaRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.Mensajes;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("empresa")
@Stateless
public class EmpresaServices {

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private EmpresaRemote ejbEmpresa;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmpresaServices
     */
    public EmpresaServices() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.seguridad.EmpresaServices
     *
     * @param request
     * @return an instance of java.lang.String
     */
    @POST
    @Path("prueba")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getJson(final RestRequest request) {
        //TODO return proper representation object
        RestResponse r = new RestResponse();
        r.setCode(100);
        r.setContent("hola " + request.getToken() + " y yo un mensaje : " + request.getContent());
        return r;
    }

    /**
     * No esta realizada la validacion del token
     *
     * @param request
     * @return
     */
    @POST
    @Path("sucursal/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllSucursal(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {
                        Empresa e = new Empresa();
                        e.setTipo(Empresa.SUCURSAL);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(ejbEmpresa.getSucursales(e));

                        return r;
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    @POST
    @Path("sucursal/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getSucursal(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes();
        RestResponse r = new RestResponse();

        try {
            HashMap<String, Object> h = (HashMap<String, Object>) request.getContent();
            Empresa e = new Empresa();
            e.setIdEmpresa(((BigDecimal) h.get("idEmpresa")).intValue());

            r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            r.setContent(ejbEmpresa.get(e));

            return r;

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     * PUT method for updating or creating an instance of EmpresaServices
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

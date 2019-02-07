/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.LoggerRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.agencia.AerolineaResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.Mensajes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author xeio
 */
public class TemplateResource {

    @EJB
    protected UsuarioRemote ejbUsuario;

    @EJB
    protected LoggerRemote ejbLogger;

    @Context
    protected UriInfo context;
    
    protected Mensajes mensajes = Mensajes.getMensajes();
    
    protected User user ;

    /**
     * Realiza las validaciones previas
     * @param request
     * @return 
     */
    protected RestResponse doValidations(final RestRequest request) {
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID Token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                user = ejbUsuario.get(new User(t.getUserName()));

                //deberia validar si el token tiene la fecha actual con la de la BD
                
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {
                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(mensajes.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(mensajes.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(mensajes.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }
        } catch (CRUDException ex) {
            Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

}

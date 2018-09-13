/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("boletos")
public class BoletoResource extends TemplateResource {

    @EJB
    private BoletoRemote ejbBoleto;

    /**
     * Creates a new instance of BoletoResource
     */
    public BoletoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.BoletoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of BoletoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("existe-boleto/{numero}")
    public RestResponse getExisteBoleto(@PathParam("nroBoleto") long numero) {
        RestResponse response = new RestResponse();
        try {
            Optional op = Optional.ofNullable(numero);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            boolean existe = ejbBoleto.isBoletoRegistrado(new Boleto(numero));

            if (existe) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_NUMERO_BOLETO_INSERTADO));
            } else {
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_NUMERO_BOLETO_INSERTADO));

            }

        } catch (CRUDException ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tipo-emision")
    public RestResponse getTipoEmision() {
        RestResponse response = new RestResponse();

        try {
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(ejbBoleto.getTipoEmision());
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }
}

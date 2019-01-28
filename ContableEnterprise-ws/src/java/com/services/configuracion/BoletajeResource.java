/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.configuracion;

import com.configuracion.entities.ContabilidadBoletaje;
import com.configuracion.remote.ConfiguracionBoletajeRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.ContabilidadBoletajeJSon;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("configuracion/boletaje")
public class BoletajeResource extends TemplateResource {

    @EJB
    private ConfiguracionBoletajeRemote ejbBoletaje;

    /**
     * Creates a new instance of Boletaje
     */
    public BoletajeResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.configuracion.BoletajeResource
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
     * PUT method for updating or creating an instance of BoletajeResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{idEmpresa}")
    public RestResponse getConfiguracionBoletaje(@PathParam("idEmpresa") Integer idEmpresa,final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                Optional op = Optional.of(idEmpresa);
                if (!op.isPresent()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("idEmpresa", idEmpresa);
                
                List<ContabilidadBoletaje> list = ejbBoletaje.get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);
                if (list.isEmpty()) {
                    ContabilidadBoletaje newBoletaje = new ContabilidadBoletaje(idEmpresa);
                    
                    ContabilidadBoletajeJSon json = ContabilidadBoletajeJSon.toJSon(newBoletaje);
                    
                    response.setContent(json);
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                } else {
                    ContabilidadBoletaje boletaje = (ContabilidadBoletaje) list.get(0);
                    
                    ContabilidadBoletajeJSon json = ContabilidadBoletajeJSon.toJSon(boletaje);
                    response.setContent(json);
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                }
            } catch (CRUDException ex) {
                Logger.getLogger(BoletajeResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setContent(ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            }
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public RestResponse saveBoletaje(final RestRequest request){
        RestResponse response = doValidations(request);
        
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()){
            try {
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String)request.getContent()).getAsJsonObject();
                System.out.println(object.toString());
                ContabilidadBoletajeJSon json = gson.fromJson(object.toString(), ContabilidadBoletajeJSon.class);
                
                ContabilidadBoletaje data = ContabilidadBoletajeJSon.toContabilidadBoletaje(json);
                
                ejbBoletaje.insert(data);
            } catch (CRUDException ex) {
                Logger.getLogger(BoletajeResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return response ;
    }
}

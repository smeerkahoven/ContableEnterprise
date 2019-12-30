/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.configuracion;

import com.configuracion.entities.Gestion;
import com.configuracion.remote.GestionRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.agencia.ClienteJSON;
import com.response.json.configuracion.GestionJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import com.view.menu.Formulario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("gestiones")
public class GestionResource extends TemplateResource {

    @EJB
    private GestionRemote ejbGestion;

    @Context
    private UriInfo context;

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse save(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            GestionJson json = BeanUtils.converToGestion(request);
            Gestion newGestion = GestionJson.toGestion(json);
            try {
                newGestion.setEstado(Gestion.ACTIVO);

                ejbGestion.iniciarGestion(newGestion);
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.GESTIONES, user.getIp());
            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
                Logger.getLogger(GestionResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("cerrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse cerrar(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {

                GestionJson json = BeanUtils.converToGestion(request);

                ejbGestion.cerrarGestion(json.getIdGestion());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent("Gestion Finalizada Correctamente");

                ejbLogger.add(Accion.UPDATE, user.getUserName(),
                        Formulario.GESTIONES, user.getIp(),
                        null);

            } catch (CRUDException ex) {
                Logger.getLogger(GestionResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response;
    }

    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {

                List<Gestion> l = (List<Gestion>) ejbGestion.get("Gestion.findAll", Gestion.class);
                List<GestionJson> list = GestionJson.toGestionJsonList(l);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(list);

                ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.GESTIONES, user.getIp());
            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
                Logger.getLogger(GestionResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("nuevo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse nuevo(final RestRequest request) {

        doValidations(request);
        
        RestResponse response = new RestResponse();
        try {

            Gestion g = ejbGestion.getCurrent();
            if (g == null) {
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(true);
            } else {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent("Existe una gestion Activa. Cierre la Gestion para crear una nueva");
            }

            ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.GESTIONES, user.getIp());
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(GestionResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    /**
     * Creates a new instance of GestionResource
     */
    public GestionResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.configuracion.GestionResource
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
     * PUT method for updating or creating an instance of GestionResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

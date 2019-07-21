/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.configuracion;

import com.configuracion.entities.Parametros;
import com.configuracion.remote.ParametrosRemote;
import com.response.json.configuracion.ParametrosJson;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
@Path("parametros")
public class ParametrosResource extends TemplateResource {

    @EJB
    private ParametrosRemote ejbParametros ;

    /**
     * Creates a new instance of ParametrosResource
     */
    public ParametrosResource() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public RestResponse getAllParametros(final RestRequest request){
        RestResponse response = doValidations(request);
        try {
            List<Parametros> fromDb = ejbParametros.get();
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(fromDb);
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(ParametrosResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response ;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public RestResponse saveParametro(final RestRequest request){
       RestResponse response = doValidations(request);
        try {
            ParametrosJson json = BeanUtils.convertToParametroJson(request);
            Parametros data = ParametrosJson.toParametro(json);
            ejbParametros.update(data);
            
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty( RestResponse.RESTFUL_SUCCESS));
        } catch (CRUDException ex) {
            Logger.getLogger(ParametrosResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }
        
        return response ;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad;

import com.response.json.seguridad.LogJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.search.LogSearch;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import java.util.LinkedList;
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
@Path("seguridad/logs")
public class LogsResource extends TemplateResource {
    


    /**
     * Creates a new instance of LogsResource
     */
    public LogsResource() {
    }

    /**
     * Retrieves representation of an instance of com.services.seguridad.LogsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @POST
    @Path("find")
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse find( final RestRequest request) {
        RestResponse response = doValidations(request);
        
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()){
            try {
                LogSearch json = BeanUtils.convertToLogSearchJson(request);
                
                List<Log> returnList = ejbLogger.find(json);
                
                if (returnList.isEmpty()) {
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_LISTA_VACIA));
                }else {
                    List<LogJson> lcontent = new LinkedList<>();
                    for (Log l : returnList) {
                        LogJson rjson = LogJson.getLogJson(l);
                        lcontent.add(rjson);
                    }
                    
                    response.setContent(lcontent);
                }
                
            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
                Logger.getLogger(LogsResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return response ;
    }

    /**
     * PUT method for updating or creating an instance of LogsResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

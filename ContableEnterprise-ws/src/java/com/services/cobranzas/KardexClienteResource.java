/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.cobranzas;

import com.cobranzas.dto.KardexClienteDto;
import com.cobranzas.json.KardexClienteSearchJson;
import com.cobranzas.remote.KardexClienteRemote;
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
@Path("cobranzas/kardex-cliente")
public class KardexClienteResource extends TemplateResource {

    @EJB
    private KardexClienteRemote ejbKardex ;

    /**
     * Creates a new instance of KardexClienteResource
     */
    public KardexClienteResource() {
    }

    /**
     * Retrieves representation of an instance of com.services.cobranzas.KardexClienteResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @POST
    @Path ("generar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse generarKardex (final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()){
            
            try {
                KardexClienteSearchJson json = BeanUtils.convertToKardexClienteSearchJson(request);
                
                List <KardexClienteDto> l = ejbKardex.generarKardexCliente(json);
                if (l.isEmpty()) {
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_LISTA_VACIA));
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    return response ;
                }
                
                response.setContent(l);
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                
            } catch (CRUDException ex) {
                response.setContent(ex.getMessage());
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                Logger.getLogger(KardexClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        return response ;
    }

    /**
     * PUT method for updating or creating an instance of KardexClienteResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

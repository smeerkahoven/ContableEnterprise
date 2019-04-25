/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.SumasSaldosDto;
import com.contabilidad.remote.PlanCuentasRemote;
import com.response.json.contabilidad.SumasSaldosSearchJson;
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
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("sumas-saldos")
public class SumasSaldosResource extends TemplateResource {

    @Context
    private UriInfo context;
    
    @EJB
    private PlanCuentasRemote ejbPlanCuentas;

    /**
     * Creates a new instance of SumasSaldosResource
     */
    public SumasSaldosResource() {
    }

    /**
     * Retrieves representation of an instance of com.services.contabilidad.SumasSaldosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SumasSaldosResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse generarSumasYSaldos(final RestRequest request) {
        RestResponse response = doValidations(request);
        
        SumasSaldosSearchJson search = BeanUtils.convertToSumasySaldos(request);
        search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
        
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()){
            try {
                List<SumasSaldosDto> list = ejbPlanCuentas.generarSumasYSaldos(search);
                
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(list);
            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }
        
        return response ;
    }
}

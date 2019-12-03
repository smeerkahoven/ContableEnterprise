/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.BalanceDto;
import com.contabilidad.remote.BalanceGeneralRemote;
import com.response.json.contabilidad.BalanceGeneralSearchJson;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("balance-general")
public class BalanceGeneralResource extends TemplateResource {
    
    @EJB
    private BalanceGeneralRemote ejbBalanceGeneral ;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BalanceGeneral
     */
    public BalanceGeneralResource() {
    }

@POST
    @Path("generar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse generar(final RestRequest request) {
        RestResponse response = doValidations(request);
        
        BalanceGeneralSearchJson search = BeanUtils.convertToBalanceGeneralSearchJson(request);
        search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
        
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()){
            try {
                BalanceDto balance = ejbBalanceGeneral.generarBalance(search);
                
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(balance);
            } catch (CRUDException ex) {
                Logger.getLogger(BalanceGeneralResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }
        
        return response ;
    }
}

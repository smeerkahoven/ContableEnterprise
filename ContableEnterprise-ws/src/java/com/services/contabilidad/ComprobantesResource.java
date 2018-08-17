/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.ComprobanteContablePK;
import com.contabilidad.entities.TipoComprobante;
import com.contabilidad.remote.ComprobanteRemote;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.ComboSelect;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("comprobantes")
public class ComprobantesResource extends TemplateResource {

    @EJB
    private ComprobanteRemote ejbComprobante;

    /**
     * Creates a new instance of ComprobantesResource
     */
    public ComprobantesResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.ComprobantesResource
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
     * PUT method for updating or creating an instance of ComprobantesResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("all-tipo-comprobantes")
    public RestResponse getAllTipoComprobantes(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                List<TipoComprobante> l = (List<TipoComprobante>) ejbComprobante.get("TipoComprobante.findAll", ComprobanteRemote.class);
                List<ComboSelect> lreturn = new LinkedList<>();
                if (!l.isEmpty()) {
                    for (Iterator<TipoComprobante> iterator = l.iterator(); iterator.hasNext();) {
                        TipoComprobante next = iterator.next();
                        ComboSelect select = new ComboSelect();
                        select.setId(next.getIdTipoComprobante());
                        select.setName(next.getValor());

                        lreturn.add(select);

                    }
                }
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(lreturn);
            } catch (CRUDException ex) {
                Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("numero-comprobante/{tipo}")
    public RestResponse getNumeroComprobante(final RestRequest request, @PathParam("tipo") String tipo) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                if (tipo == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }
                if (tipo.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }
                
                String fecha = DateContable.getCurrentDateStr(DateContable.PARTITION_FORMAT);
                
                ComprobanteContablePK numero = ejbComprobante.getNextComprobantePK(fecha, tipo);
                
                if (numero.getIdLibro() == ComprobanteContablePK.INVALID_PK){
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_NUMERO_COMPROBANTE));
                    return response;
                }
                
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(numero);
            } catch (CRUDException ex) {
                Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

}
